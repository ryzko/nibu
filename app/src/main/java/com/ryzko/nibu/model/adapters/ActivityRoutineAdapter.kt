package com.ryzko.nibu.model.adapters

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ryzko.nibu.R
import com.ryzko.nibu.model.activities.ActivityRoutineType

import com.ryzko.nibu.model.rest.routines.ActivityRoutineObjectData
import android.view.animation.AnimationUtils
import com.ryzko.nibu.model.utils.DateParseUtils
import kotlinx.android.synthetic.main.cardview_activity_item.view.*
import org.joda.time.Duration
import org.joda.time.format.PeriodFormatterBuilder


/**
 * Created by Marcin Ryzko on 28.08.2018.
 * marcin@ryzko.com
 * http://ryzko.com
 */

class ActivityRoutineAdapter(var list: MutableList<ActivityRoutineObjectData>) : RecyclerView.Adapter<ActivityRoutineAdapter.ViewHolder>() {

    var activityList: MutableList<ActivityRoutineObjectData>? = null


    init {
        this.activityList = list.sortedBy { it.activity_start }.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivityRoutineAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.cardview_activity_item, parent, false)
        return ActivityRoutineAdapter.ViewHolder(v)
    }

    override fun getItemViewType(position: Int): Int {
        var type: Int = 0;

        when (list[position].activity_type) {
            ActivityRoutineType.FOOD -> type = 0
            ActivityRoutineType.SLEEP -> type = 1
            ActivityRoutineType.MOTION -> type = 2
            ActivityRoutineType.NAPPY -> type = 3
            ActivityRoutineType.MEDICINE -> type = 4
        }

        return type
    }

    override fun getItemCount(): Int {
        return activityList!!.size
    }

    override fun onBindViewHolder(holder: ActivityRoutineAdapter.ViewHolder, position: Int) {
        holder.bindItems(activityList!![position], position)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var lastPosition = -1

        fun bindItems(data: ActivityRoutineObjectData, index: Int) {

            when (data.activity_type) {
                "food" -> itemView.text_activity_title.text = "${itemView.context.getString(R.string.activity_type_meal)} ${index.toString()}:"
                else -> {
                    itemView.text_activity_title.text = "${data.activity_type} ${index.toString()}:"
                }
            }

            itemView.text_activity_comment.text = data.activity_comment
            val date = DateParseUtils.getDateTime(data.activity_start, DateParseUtils.YYYYMMDD_HHMMSS)
            val dateEnd = DateParseUtils.getDateTime(data.activity_end, DateParseUtils.YYYYMMDD_HHMMSS)
            itemView.text_activity_time.text = DateParseUtils.getString(date, DateParseUtils.HHMM)
            itemView.text_activity_end_time.text = DateParseUtils.getString(dateEnd, DateParseUtils.HHMM)
            itemView.text_activity_duration.text = getDuration(data)
            //itemView.text_activity_date.text = DateParseUtils.getString(date, DateParseUtils.ddMMMyyyy)

            when (data.item_kind) {
                "left" -> {
                    itemView.text_breast_side.text = itemView.context.getString(R.string.activity_breast_side_left)
                }
                "right" -> {
                    itemView.text_breast_side.text = itemView.context.getString(R.string.activity_breast_side_right)
                }
                "both" -> {
                    itemView.text_breast_side.text = itemView.context.getString(R.string.activity_breast_side_both)
                }
            }


            if (data.activity_comment.isNullOrBlank()) {

                itemView.text_activity_comment.visibility = View.GONE
                itemView.image_coment_icon.visibility = View.GONE
            } else {
                itemView.text_activity_comment.visibility = View.VISIBLE
                itemView.image_coment_icon.visibility = View.VISIBLE
            }



            setAnimation(itemView, index)

            //set the onclick listener for the singlt list item
            itemView.setOnClickListener {
                Log.e("ItemClicked", itemView.text_activity_time.text.toString())
            }
        }

        private fun getDuration(data: ActivityRoutineObjectData): String {
            val startTime = DateParseUtils.getDateTime(data.activity_start, DateParseUtils.YYYYMMDD_HHMMSS)
            val endTime = DateParseUtils.getDateTime(data.activity_end, DateParseUtils.YYYYMMDD_HHMMSS)

            val duration = Duration(endTime.millis - startTime.millis)
            val formatter = PeriodFormatterBuilder()
                    .appendDays()
                    .appendSuffix(itemView.context.getString(R.string.day_short))
                    .appendHours()
                    .appendSuffix(itemView.context.getString(R.string.hour_short))
                    .appendMinutes()
                    .appendSuffix(itemView.context.getString(R.string.min_short))
                    .toFormatter()

            return formatter.print(duration.toPeriod())
        }

        private fun setAnimation(viewToAnimate: View, position: Int) {
            // If the bound view wasn't previously displayed on screen, it's animated
            if (position > lastPosition) {
                val animation = AnimationUtils.loadAnimation(viewToAnimate.context, android.R.anim.fade_in)
                viewToAnimate.startAnimation(animation)
                lastPosition = position
            }
        }
    }


}