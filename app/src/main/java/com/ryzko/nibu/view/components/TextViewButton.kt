package com.ryzko.nibu.view.components

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.ryzko.nibu.R

/**
 * Created by Marcin Ryzko on 07.09.2018.
 * marcin@ryzko.com
 * http://ryzko.com
 */

class TextViewButton @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyle: Int = 0,
        defStyleRes: Int = 0
) : LinearLayout(context, attrs, defStyle, defStyleRes) {

    var textView: Button
    var labelView:TextView


    init {
        LayoutInflater.from(context)
                .inflate(R.layout.button_date, this, true)
        textView = findViewById(R.id.button_date_text)
        labelView = findViewById(R.id.button_date_label)
        labelView.setOnClickListener {
            callOnClick()
        }

        orientation = VERTICAL
    }

    fun getText():String{
        return textView.text.toString()
    }

    fun setText(text:String){
        textView.text = text
    }

    fun getLabel():String{
        return  labelView.text.toString()
    }

    fun setLabel(label:String){
        labelView.text = label
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        onTouchEvent(ev)

        return false

    }
}
