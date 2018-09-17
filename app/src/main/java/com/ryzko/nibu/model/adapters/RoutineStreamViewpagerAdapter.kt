package com.ryzko.nibu.model.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.text.format.DateUtils
import com.ryzko.nibu.model.activities.ActivityDayData
import com.ryzko.nibu.model.user.ActivityRoutineDataService
import com.ryzko.nibu.model.user.UserData
import com.ryzko.nibu.model.utils.DateParseUtils
import com.ryzko.nibu.view.fragments.RoutineStreamPageFragment
import org.joda.time.DateTime
import org.joda.time.Days


/**
 * Created by Marcin Ryzko on 14.02.2018.
 * marcin@ryzko.com
 * http://ryzko.com
 */

class RoutineStreamViewpagerAdapter(fragmentManager: FragmentManager) :
        FragmentStatePagerAdapter(fragmentManager) {

    private var babyBirthDate: String = UserData.selectedBaby.birth_date
    private var startDate: DateTime = DateParseUtils.getDateTime(babyBirthDate, DateParseUtils.YYYYMMDD_HHMMSS)
    private var todayDate: DateTime = DateTime.now()
    lateinit var datesList: MutableList<DateTime>
    lateinit var activityRoutineDataService: ActivityRoutineDataService

    private fun propagateDates() {

        datesList = mutableListOf()
        var datesLen = Days.daysBetween(startDate.withTimeAtStartOfDay(), todayDate.withTimeAtStartOfDay()).days

        for (i in 0..datesLen) {
            datesList.add(DateTime.now().minusDays(i))
        }
        datesList.reverse()
    }

    fun initData(activityRoutineDataService: ActivityRoutineDataService) {
        propagateDates()
        this.activityRoutineDataService = activityRoutineDataService
        notifyDataSetChanged()
    }


    fun getIndexByDate(date: DateTime): Int {

        var result = -1
        for (i in datesList.indices) {
            val d = DateParseUtils.getString(datesList[i], DateParseUtils.yyyyMMdd)
            if (DateParseUtils.getString(date, DateParseUtils.yyyyMMdd) == d) {
                result = i
                break
            }

        }
        return result
    }


    override fun getItem(position: Int): Fragment {
        var fragment: RoutineStreamPageFragment = RoutineStreamPageFragment.newInstance()
        fragment.updateData(findActivityByDate(position))
        return fragment
    }

    fun findActivityByDate(position: Int): ActivityDayData? {
        var date: DateTime = datesList[position]
        return activityRoutineDataService.getDayData(DateParseUtils.getString(date, DateParseUtils.yyyyMMdd))
    }

    override fun getCount(): Int {
        return datesList.size
    }

    override fun getPageTitle(position: Int): CharSequence {

        return DateUtils.getRelativeTimeSpanString(
                datesList[position].millis,
                DateTime.now().millis,
                DateUtils.DAY_IN_MILLIS,
                DateUtils.FORMAT_SHOW_DATE).toString()
    }
}