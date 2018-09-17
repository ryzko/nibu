package com.ryzko.nibu.model.utils

import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import org.joda.time.format.DateTimeFormat
import java.util.*

/**
 * Created by Marcin Ryzko on 02.09.2018.
 * marcin@ryzko.com
 * http://ryzko.com
 */

class DateParseUtils {
    companion object {

        //activity types
        val YYYYMMDD_HHMMSS = "yyyy-MM-dd HH:mm:ss"
        val ddMMMyyyy = "dd MMM yyyy"
        val yyyyMMdd = "yyyy-MM-dd"
        val HHMM = "HH:mm"


        fun getDateTime(date:String, pattern:String = YYYYMMDD_HHMMSS):DateTime{
           return DateTime.parse(date, DateTimeFormat.forPattern(pattern).withLocale(Locale.getDefault()).withZone(DateTimeZone.getDefault())).toDateTime()
        }

        fun getString(dateTime:DateTime, pattern:String = YYYYMMDD_HHMMSS):String{
            val fmt = DateTimeFormat.forPattern(pattern).withLocale(Locale.getDefault())
            return fmt.print(dateTime)
        }
    }

}