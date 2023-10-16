package com.shoppingcartapplication.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Shader
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.shoppingcartapplication.R

class GradientTextView : AppCompatTextView {
    private var gradientShader: LinearGradient? = null
    private var paint: Paint? = null
    private val gradientColors = intArrayOf(
    context.getColor(R.color.start_color),
        context.getColor(R.color.center_color),
        context.getColor(R.color.end_color)
    )
    constructor (context: Context?) : super(context!!) {
        init()
    }

        constructor (context: Context?, attrs: AttributeSet?) : super(
        context!!, attrs){
            init()
        }

    constructor (context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context!!, attrs, defStyleAttr
    ) {
    init()
    }


    private fun init() {
// Set up the paint object
        paint = getPaint()
        setTextAppearance (R.style.WorkSansRegular)
    }

    override fun onDraw(canvas: Canvas) {
        val paint = paint
        val width = measuredWidth.toFloat()
        val height = (measuredHeight.toFloat() / 1.4). toFloat()
        if (gradientShader == null) {
            gradientShader = LinearGradient(
                0f, 0f, width,0f, gradientColors, null,
            Shader.TileMode.CLAMP)
        }
        paint?.shader = gradientShader
        canvas.drawText(text.toString().trim(),0f, height, paint!!)

    }
}