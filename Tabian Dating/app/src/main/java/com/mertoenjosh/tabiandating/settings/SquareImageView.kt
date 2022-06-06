package com.mertoenjosh.tabiandating.settings


import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView


/**
 * Custom ImageView class that forces side to be the same length
 */

class SquareImageView : AppCompatImageView {

    constructor(context: Context?) : super(context!!)
    constructor(context: Context?, attrs: AttributeSet?) : super(context!!, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context!!, attrs, defStyleAttr)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec)
    }
}
