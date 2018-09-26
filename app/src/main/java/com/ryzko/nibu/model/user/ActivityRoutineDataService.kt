package com.ryzko.nibu.model.user

import com.ryzko.nibu.Nibu
import com.ryzko.nibu.model.activities.ActivityDayData
import com.ryzko.nibu.model.api.ApiManager
import com.ryzko.nibu.model.events.ActivitiesResultEvent
import com.ryzko.nibu.model.rest.routines.ActivityRoutineObjectData
import com.ryzko.nibu.model.utils.DateParseUtils
import com.ryzko.rxminibus.RxMiniBus
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


/**
 * Created by Marcin Ryzko on 05.09.2018.
 * marcin@ryzko.com
 * http://ryzko.com
 */

class ActivityRoutineDataService() {

    lateinit var flatList:MutableList<ActivityRoutineObjectData>
    var activitiesCollection:HashMap<String, MutableList<ActivityRoutineObjectData>> = hashMapOf()


    fun addList(flatList:MutableList<ActivityRoutineObjectData>){
        this.flatList = flatList
        generateActivityByDayList()
    }

    private fun generateActivityByDayList(){
        for(i in flatList.indices){
            val activity = flatList[i]
            val date = DateParseUtils.getDateTime(activity.activity_start, DateParseUtils.YYYYMMDD_HHMMSS)
            val dayString = DateParseUtils.getString(date, DateParseUtils.yyyyMMdd)
            if(activitiesCollection[dayString]==null) {
                val list = flatList.asSequence().filter { it.activity_start.substring(0,10) == dayString }.toMutableList()
                activitiesCollection[dayString] = list.asSequence().sortedBy { it.activity_start }.toMutableList()
            }
        }

    }

    fun getDayActivityies(day:String): MutableList<ActivityRoutineObjectData>? {
        return activitiesCollection[day]
    }

    fun getDayData(day:String): ActivityDayData? {
        if(activitiesCollection[day]!=null){
            return ActivityDayData(day, activitiesCollection[day])
        }
        return null
    }

    fun addActivityByDate(activity:ActivityRoutineObjectData){
        val day = activity.activity_start.substring(0,10)
        activitiesCollection[day]?.add(activity)
        activitiesCollection[day]?.sortBy { it.activity_start }

    }

    fun removeActivity(activity:ActivityRoutineObjectData){
        var date = DateParseUtils.getDateTime(activity.activity_start, DateParseUtils.YYYYMMDD_HHMMSS)
        var day = DateParseUtils.getString(date, DateParseUtils.yyyyMMdd)
        activitiesCollection[day]?.remove(activity)
    }

    fun getAllActivitiesRoutines() {
        ApiManager.getAllActivityRoutines()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ list: MutableList<ActivityRoutineObjectData> ->
                    onGetActivities(list)
                }) { error: Throwable? ->
                    if (error != null) onRequestFailure(error)

                }
    }

    private fun onGetActivities(list: MutableList<ActivityRoutineObjectData>){
        flatList = list
        RxMiniBus.post(ActivitiesResultEvent(list))

    }

    private fun onRequestFailure(error: Throwable?){

    }

    interface OnActivitiesResultListener{
        fun onActivitiesResult(list: MutableList<ActivityRoutineObjectData>) = Unit
    }
}