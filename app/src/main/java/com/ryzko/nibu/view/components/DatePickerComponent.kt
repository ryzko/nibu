package com.ryzko.nibu.view.components

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageButton
import android.widget.RelativeLayout
import com.ryzko.nibu.R
import kotlinx.android.synthetic.main.date_picker_view.view.*
import java.io.Console

/**
 * Created by Marcin Ryzko on 12.08.2018.
 * marcin@ryzko.com
 * http://ryzko.com
 */

class DatePickerComponent @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {

    var prevBtn: ImageButton? = prev_btn

    init {
        LayoutInflater.from(context)
                .inflate(R.layout.date_picker_view, this, true)
        if (prev_btn!=null){
            Log.i("KOTLIN","KOTLIN")
            prev_btn!!.setOnClickListener {
                Log.i("KOTLIN","KOTLIN")
            }

        }

    }





}