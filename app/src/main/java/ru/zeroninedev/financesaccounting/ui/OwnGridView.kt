package ru.zeroninedev.financesaccounting.ui

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.GridView

class OwnGridView: GridView {

    //Override parent constructor
    constructor(context: Context)
            : super (context)
    constructor(context: Context, attributeSet: AttributeSet)
            : super (context, attributeSet)
    constructor(context: Context, attributeSet: AttributeSet, defStyle: Int)
            : super (context, attributeSet, defStyle)


    // Override onMeasure function
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        var heightSpec: Int = if(layoutParams.height == ViewGroup.LayoutParams.WRAP_CONTENT){

            // We can't use the two leftmost bits.
            MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE shr 2,MeasureSpec.AT_MOST)

        }else{

            heightMeasureSpec

        }
        super.onMeasure(widthMeasureSpec, heightSpec)

    }
}