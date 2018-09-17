package com.ryzko.nibu.view.components

import android.app.DatePickerDialog
import android.content.Context
import com.ryzko.nibu.model.user.UserData
import com.ryzko.nibu.model.utils.DateParseUtils
import org.jetbrains.anko.custom.style
import org.joda.time.DateTime


/**
 * Created by Marcin Ryzko on 02.09.2018.
 * marcin@ryzko.com
 * http://ryzko.com
 */

class BabyDatePickerDialog(context: Context, callBack: DatePickerDialog.OnDateSetListener,
                             year: Int, monthOfYear: Int, dayOfMonth: Int) : DatePickerDialog(context, callBack, year, monthOfYear, dayOfMonth) {


    init {

        this.datePicker.maxDate = DateTime().millis
        this.datePicker.minDate = DateParseUtils.getDateTime(UserData.selectedBaby.birth_date, DateParseUtils.YYYYMMDD_HHMMSS).millis



    }



    companion object {


    }
}