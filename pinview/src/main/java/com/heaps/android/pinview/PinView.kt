package com.heaps.android.pinview

import android.content.Context
import android.graphics.Color
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.LinearLayout

typealias OnCodeTypedListener = (String) -> Unit

class PinView : FrameLayout {

    private lateinit var pinContainer: LinearLayout
    private lateinit var pinLabel: EditText

    companion object {
        private const val DEFAULT_NUMBER_OF_FIELDS = 4
    }

    constructor(context: Context) : super(context) {
        init(null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        init(attrs, defStyle)
    }

    var listener: OnCodeTypedListener? = null
    private var numberOfFields: Int = -1
    private var currentCode = ""
    private var pendingInputColor: Int = Color.WHITE
    private var idleInputColor: Int = Color.BLUE
    private var completedInputColor: Int = Color.MAGENTA
    private var completedInputSize: Int = 48
    private var pendingInputSize: Int = 60

    private fun init(attrs: AttributeSet?, defStyle: Int) {
        LayoutInflater.from(context).inflate(R.layout.view_pin_view, this)
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.PinView, defStyle, 0)
        numberOfFields =
            attributes.getInteger(
                R.styleable.PinView_numberOfFields,
                DEFAULT_NUMBER_OF_FIELDS
            )
        pendingInputColor = attributes.getColor(R.styleable.PinView_pendingInputColor, Color.WHITE)
        idleInputColor = attributes.getColor(R.styleable.PinView_idleInputColor, Color.WHITE)
        completedInputColor =
            attributes.getColor(R.styleable.PinView_completedInputColor, Color.WHITE)
        completedInputSize = attributes.getDimensionPixelSize(R.styleable.PinView_completedInputSize, 48)
        pendingInputSize = attributes.getDimensionPixelSize(R.styleable.PinView_pendingInputSize, 60)
        setupFields()
        setupLabel()
        attributes.recycle()

        setOnClickListener {
            focus()
        }
    }

    private fun setupFields() {
        pinContainer = findViewById(R.id.pin_container)
        pinLabel = findViewById(R.id.pin_label)
        pinLabel.filters = arrayOf(InputFilter.LengthFilter(numberOfFields))
        for (i in 0 until numberOfFields) {
            val childView = PinInputItem(context)
            childView.setPendingInputColor(pendingInputColor)
            childView.setIdleInputColor(idleInputColor)
            childView.setCompletedInputColor(completedInputColor)
            childView.setCompletedInputSize(completedInputSize)
            childView.setPendingInputSize(pendingInputSize)
            pinContainer.addView(childView)
            childView.setIdle()
            childView.invalidate()
        }
        focusChildAt(0)
    }

    private fun setupLabel() {
        pinLabel.addTextChangedListener(object : SimpleTextWatcher() {
            override fun afterTextChanged(s: Editable) {
                val pin = s.toString()
                if (pin.length < currentCode.length) {
                    currentCode = pin
                    handleDelete()
                } else if (pin.length > currentCode.length) {
                    currentCode = pin
                    handleInput()
                }
            }
        })

    }

    fun focus() {
        pinLabel.requestFocus()
        KeyboardUtil.showKeyboard(pinLabel)
    }

    private fun handleDelete() {
        focusChildAt(currentCode.length)
        idleChildAt(currentCode.length + 1)
    }

    private fun handleInput() {
        setInputDoneAt(currentCode.length - 1)
        if (currentCode.length == numberOfFields) {
            codeCompleted()
        } else {
            focusChildAt(currentCode.length)
        }
    }

    private fun codeCompleted() {
        val code = collectCode()
        listener?.invoke(code)
    }

    private fun collectCode(): String {
        return pinLabel.text.toString()
    }

    private fun focusChildAt(index: Int) {
        if (index in 0 until numberOfFields) {
            val child = pinContainer.getChildAt(index) as PinInputItem
            child.setAwaitingInput()
        }
    }

    private fun idleChildAt(index: Int) {
        if (index in 0 until numberOfFields) {
            val child = pinContainer.getChildAt(index) as PinInputItem
            child.setIdle()
        }
    }

    private fun setInputDoneAt(index: Int) {
        if (index in 0 until numberOfFields) {
            val child = pinContainer.getChildAt(index) as PinInputItem
            child.setInputDone()
        }
    }

    fun resetFields() {
        pinLabel.text.clear()
        currentCode = ""
        for (index in 0 until numberOfFields) {
            val child = pinContainer.getChildAt(index) as PinInputItem
            child.setIdle()
        }
        focusChildAt(0)
    }
}

abstract class SimpleTextWatcher : TextWatcher {
    override fun beforeTextChanged(
        s: CharSequence,
        start: Int,
        count: Int,
        after: Int
    ) {
    }

    override fun onTextChanged(
        s: CharSequence,
        start: Int,
        before: Int,
        count: Int
    ) {
    }

    override fun afterTextChanged(s: Editable) {}
}