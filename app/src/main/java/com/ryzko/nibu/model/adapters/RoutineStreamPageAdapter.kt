package com.ryzko.nibu.model.adapters

import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.ryzko.nibu.R
import com.ryzko.nibu.model.rest.routines.BaseRoutineObjectData
import com.ryzko.nibu.model.rest.routines.FoodRoutineObjectData

/**
 * Created by Marcin on 2018-02-27.
 */
class RoutineStreamPageAdapter (var dayData:RoutineStreamViewpagerAdapter.DayData): RecyclerView.Adapter<RoutineStreamPageAdapter.ViewHolder>() {

    lateinit var view:View

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoutineStreamPageAdapter.ViewHolder {

        when(viewType){
            0-> {
                view = LayoutInflater.from(parent.context).inflate(R.layout.list_foudroutine_item, parent, false)
            }
        }

        return ViewHolder(view)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: RoutineStreamPageAdapter.ViewHolder, position: Int) {
        holder.bindItems(dayData.obj[position])
    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return dayData.obj.size
    }

    override fun getItemViewType(position: Int): Int {
        // Just as an example, return 0 or 2 depending on position
        // Note that unlike in ListView adapters, types don't have to be contiguous
        var type = -1
        when(dayData.obj[position].category){
            "food" -> type = 0
        }

        return position % 2 * 2
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {


        fun bindItems(data: BaseRoutineObjectData) {

            createFoodListView(data as FoodRoutineObjectData)



            //set the onclick listener for the singlt list item
            itemView.setOnClickListener({
                Log.e("ItemClicked", data.breast_side)
            })
        }

        fun createFoodListView(data:FoodRoutineObjectData){

            val breastItem: ConstraintLayout = itemView.findViewById(R.id.breast_item)
            val bottleItem: ConstraintLayout = itemView.findViewById(R.id.bottle_item)
            val solidItem: ConstraintLayout = itemView.findViewById(R.id.solid_item)

            val breastStartTime:TextView = itemView.findViewById(R.id.textview_breast_starttime)
            val bottleStartTime:TextView = itemView.findViewById(R.id.textview_bottle_startime)
            val solidStartTime:TextView = itemView.findViewById(R.id.textview_solid_starttime)

            val breastSide:TextView = itemView.findViewById(R.id.textview_brest_side)

            val breastTime:TextView = itemView.findViewById(R.id.textview_breast_time)
            val bottleTime:TextView = itemView.findViewById(R.id.textview_bottle_time)
            val solidTime:TextView = itemView.findViewById(R.id.textview_solid_time)

            var type:String = data.type

            bottleItem.visibility = View.GONE
            solidItem.visibility = View.GONE
            breastItem.visibility = View.GONE

            when(type){
                "breast" -> {
                    breastItem.visibility = View.VISIBLE
                    breastStartTime.text = data.start_time.substring(11)
                    breastTime.text = "${Math.floor((data.breastfeeding_time_minutes/60).toDouble()).toInt()} h ${data.breastfeeding_time_minutes%60} min "
                    breastSide.text = data.breast_side
                }
                "solid" -> {
                    solidItem.visibility = View.VISIBLE
                    solidStartTime.text = data.start_time.substring(11)
                    solidTime.text = "${data.weight} g"
                }
                "bottle" -> {
                    bottleItem.visibility = View.VISIBLE
                    bottleStartTime.text = data.start_time.substring(11)
                    bottleTime.text = "${data.volume} ml"
                }
            }
        }
    }



}