package ru.zeroninedev.financesaccounting.ui

import android.content.Context
import androidx.viewpager.widget.ViewPager
import android.util.AttributeSet
import android.view.View

class ScrollableViewPager : ViewPager {

    //Override parent constructor
    constructor(context: Context)
            : super(context)
    constructor(context: Context,attributeSet: AttributeSet)
            : super(context,attributeSet)


    // Override onMeasure function
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        var height = 0
        for (i in 0 until childCount){
            val childView: View = getChildAt(i)
            childView.measure(
                widthMeasureSpec,
                MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)
            )
            val childHeight: Int = childView.measuredHeight
            if (childHeight > height) height = childHeight
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

}