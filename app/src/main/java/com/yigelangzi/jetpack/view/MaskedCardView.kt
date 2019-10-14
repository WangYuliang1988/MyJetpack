package com.yigelangzi.jetpack.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet
import com.google.android.material.R
import com.google.android.material.card.MaterialCardView
import com.google.android.material.shape.ShapeAppearanceModel
import com.google.android.material.shape.ShapeAppearancePathProvider

/**
 * A Card view that clips the content of any shape.
 */
// @JvmOverloads注解的作用：在有默认参数值的方法中使用，Kotlin会暴露多个重载方法
class MaskedCardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.materialCardViewStyle
) : MaterialCardView(context, attrs, defStyleAttr) {
    private val pathProvider = ShapeAppearancePathProvider()
    private val path = Path()
    private val shapeAppearance = ShapeAppearanceModel.builder(context, attrs, defStyleAttr, R.style.Widget_MaterialComponents_CardView).build()
    private val rectF = RectF(0f, 0f, 0f, 0f)

    override fun onDraw(canvas: Canvas) {
        canvas.clipPath(path)
        super.onDraw(canvas)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        rectF.right = w.toFloat()
        rectF.bottom = h.toFloat()
        pathProvider.calculatePath(shapeAppearance, 1f, rectF, path)
        super.onSizeChanged(w, h, oldw, oldh)
    }
}
