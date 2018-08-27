package com.ryzko.nibu.view.components

import android.app.DatePickerDialog
import android.content.Context
import android.os.Build
import android.support.annotation.RequiresApi
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.DatePicker
import android.widget.ImageButton
import android.widget.RadioButton
import android.widget.RelativeLayout
import com.ryzko.nibu.R
import kotlinx.android.synthetic.main.date_picker_view.view.*
import org.joda.time.DateTime
import java.io.Console
import org.joda.time.format.DateTimeFormat
import java.util.*


/**
 * Created by Marcin Ryzko on 12.08.2018.
 * marcin@ryzko.com
 * http://ryzko.com
 */

class DatePickerComponent @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {

    private val nextBtn:ImageButton
    private val prevBtn:ImageButton
    private val leftBtn:RadioButton
    private val midBtn:RadioButton
    private val rightBtn:RadioButton

    private var dateOffset = 1
    var cal = Calendar.getInstance()!!

    var fmt = DateTimeFormat.forPattern("dd MMM")

    private var dateArray:Array<DateTime> = arrayOf(DateTime.now().minusDays(2), DateTime.now().minusDays(1), DateTime.now())



    init {
        LayoutInflater.from(context).inflate(R.layout.date_picker_view, this, true)

        nextBtn = findViewById(R.id.next_btn)
        prevBtn = findViewById(R.id.prev_btn)
        leftBtn = findViewById(R.id.btn_left)
        midBtn = findViewById(R.id.btn_mid)
        rightBtn = findViewById(R.id.btn_right)
        nextBtn.isEnabled = chkNext();
        bindListeners()
        bindDatesStrings()
    }

    private fun bindListeners(){
        prevBtn.setOnClickListener {movePrev()}
        nextBtn.setOnClickListener {moveNext()}

        leftBtn.setOnClickListener {
           // Log.i("KOTLIN","next")
        }

        midBtn.setOnClickListener {
            if(midBtn.isChecked) showDatePicker(1)
        }

        rightBtn.setOnClickListener {
            //Log.i("KOTLIN","next")
        }
    }

    private fun bindDatesStrings(){


        if(dateArray[1].dayOfYear() == DateTime.now().minusDays(1).dayOfYear())
            midBtn.text = "Yesterday"
        else
            midBtn.text = fmt.print(dateArray[1])

        if(dateArray[2].dayOfYear() == DateTime.now().dayOfYear())
            rightBtn.text = "Today"
        else if (dateArray[2].dayOfYear() == DateTime.now().minusDays(1).dayOfYear())
            rightBtn.text = "Yesterday"
        else
            rightBtn.text = fmt.print(dateArray[2])


        leftBtn.text = fmt.print(dateArray[0])

    }

    private fun movePrev(){
       // dateOffset++
        nextBtn.isEnabled = chkNext();
        dateArray[0] = dateArray[0].minusDays(dateOffset)
        dateArray[1] = dateArray[1].minusDays(dateOffset)
        dateArray[2] = dateArray[2].minusDays(dateOffset)
        bindDatesStrings()
    }

    private fun moveNext(){
        //dateOffset--
        nextBtn.isEnabled = chkNext()

        dateArray[0] = dateArray[0].plusDays(dateOffset)
        dateArray[1] = dateArray[1].plusDays(dateOffset)
        dateArray[2] = dateArray[2].plusDays(dateOffset)
        bindDatesStrings()
    }

    private fun chkNext():Boolean{
        return dateOffset>0
    }

    val dateSetListener = object : DatePickerDialog.OnDateSetListener {
        override fun onDateSet(view: DatePicker, year: Int, monthOfYear: Int,
                               dayOfMonth: Int) {
            var date  = DateTime(year, monthOfYear, dayOfMonth,0,0)
            updateDateInView(date)
        }
    }

    private fun updateDateInView(dateTime:DateTime){
        dateArray = arrayOf(dateTime.minusDays(1), dateTime, dateTime.plusDays(1))
        bindDatesStrings()
    }


    private fun showDatePicker(index: Int = 0){
        DatePickerDialog(this.context,
                dateSetListener,
                // set DatePickerDialog to point to today's date when it loads up
                dateArray[index].year,
                dateArray[index].monthOfYear,
                dateArray[index].dayOfMonth).show()
    }



}