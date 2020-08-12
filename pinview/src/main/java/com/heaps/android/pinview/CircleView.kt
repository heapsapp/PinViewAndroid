package com.heaps.android.pinview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class CircleView : View {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    )

    private val paint = Paint().apply {
        isAntiAlias = true
    }

    fun setBackgroundPaintColor(colorRes: Int) {
        paint.color = colorRes
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        val width = width.toFloat()
        val height = height.toFloat()

        canvas.drawCircle(width / 2, height / 2, width / 2, paint)
    }
}