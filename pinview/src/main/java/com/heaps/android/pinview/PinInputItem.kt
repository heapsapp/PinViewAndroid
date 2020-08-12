package com.heaps.android.pinview

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout


class PinInputItem : FrameLayout {

    companion object {
        const val ANIMATION_DURATION = 200L
    }

    private lateinit var container: ViewGroup
    private lateinit var smallCircle: CircleView
    private lateinit var bigCircle: CircleView

    private var pendingInputColor: Int = Color.WHITE
    private var idleInputColor: Int = Color.BLUE
    private var completedInputColor: Int = Color.MAGENTA

    private var completedInputSize: Int = 48
    private var pendingInputSize: Int = 60

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        init()
    }

    fun setPendingInputColor(pendingInputColor: Int) {
        this.pendingInputColor = pendingInputColor
        invalidate()
    }

    fun setIdleInputColor(idleInputColor: Int) {
        this.idleInputColor = idleInputColor
        invalidate()
    }

    fun setCompletedInputColor(completedInputColor: Int) {
        this.completedInputColor = completedInputColor
        invalidate()
    }

    fun setCompletedInputSize(completedInputSize: Int) {
        this.completedInputSize = completedInputSize
        smallCircle.layoutParams.width = completedInputSize
        smallCircle.layoutParams.height = completedInputSize
        invalidate()
    }

    fun setPendingInputSize(pendingInputSize: Int) {
        this.pendingInputSize = pendingInputSize
        bigCircle.layoutParams.height = pendingInputSize
        bigCircle.layoutParams.width = pendingInputSize
        container.layoutParams.height = pendingInputSize
        container.layoutParams.width = pendingInputSize
        invalidate()
    }

    private fun init() {
        LayoutInflater.from(context).inflate(R.layout.view_pin_input, this)
        smallCircle = findViewById(R.id.small_circle)
        smallCircle.setBackgroundPaintColor(completedInputColor)
        bigCircle = findViewById(R.id.big_circle)
        bigCircle.setBackgroundPaintColor(pendingInputColor)
        container = findViewById(R.id.container)
    }

    fun setAwaitingInput() {
        bigCircle.setBackgroundPaintColor(pendingInputColor)
        animateUnconfirmed()
    }

    fun setIdle() {
        bigCircle.setBackgroundPaintColor(idleInputColor)
        animateUnconfirmed()
    }

    fun setInputDone() {
        animateConfirmed()
    }

    private fun animateConfirmed() {
        val bigCircleAnimator = ValueAnimator.ofInt(
            bigCircle.measuredHeight,
            completedInputSize
        )
        bigCircleAnimator.addUpdateListener { valueAnimator ->
            val size = valueAnimator.animatedValue as Int
            val layoutParams = bigCircle.layoutParams
            layoutParams.width = size
            layoutParams.height = size
            bigCircle.layoutParams = layoutParams
        }
        bigCircleAnimator.duration = ANIMATION_DURATION
        bigCircleAnimator.start()

        val smallCircleAnimator = ValueAnimator.ofInt(
            smallCircle.measuredHeight,
            completedInputSize - 4
        )
        smallCircleAnimator.addUpdateListener { valueAnimator ->
            val size = valueAnimator.animatedValue as Int
            val layoutParams = smallCircle.layoutParams
            layoutParams.width = size
            layoutParams.height = size
            smallCircle.layoutParams = layoutParams
        }

        smallCircleAnimator.duration = ANIMATION_DURATION
        smallCircleAnimator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {
            }

            override fun onAnimationEnd(animation: Animator?) {
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationStart(animation: Animator?) {
                smallCircle.visibility = View.VISIBLE
            }

        })
        smallCircleAnimator.start()

    }

    private fun animateUnconfirmed() {
        val bigCircleAnimator = ValueAnimator.ofInt(
            bigCircle.measuredHeight,
            pendingInputSize
        )
        bigCircleAnimator.addUpdateListener { valueAnimator ->
            val size = valueAnimator.animatedValue as Int
            val layoutParams = bigCircle.layoutParams
            layoutParams.width = size
            layoutParams.height = size
            bigCircle.layoutParams = layoutParams
        }
        bigCircleAnimator.duration = ANIMATION_DURATION
        bigCircleAnimator.start()

        val smallCircleAnimator = ValueAnimator.ofInt(smallCircle.measuredHeight, 1)
        smallCircleAnimator.addUpdateListener { valueAnimator ->
            val size = valueAnimator.animatedValue as Int
            val layoutParams = smallCircle.layoutParams
            layoutParams.width = size
            layoutParams.height = size
            smallCircle.layoutParams = layoutParams
        }
        smallCircleAnimator.duration = ANIMATION_DURATION
        smallCircleAnimator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {

            }

            override fun onAnimationEnd(animation: Animator?) {
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationStart(animation: Animator?) {
            }

        })
        smallCircleAnimator.start()
    }
}