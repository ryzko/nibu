package com.ryzko.nibu.model.adapters

import android.content.Intent
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.ryzko.nibu.R
import com.ryzko.nibu.model.uiobjects.HomeListObject
import com.ryzko.nibu.view.activities.RoutineStreamActivity

/**
 * Created by Marcin Ryzko on 23.11.2017.
 * marcin@ryzko.com
 * http://ryzko.com
 */

class DailyRoutinesListAdapter(private val list:List<HomeListObject>): RecyclerView.Adapter<DailyRoutinesListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyRoutinesListAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_view_home, parent, false)
        return ViewHolder(v)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: DailyRoutinesListAdapter.ViewHolder, position: Int) {
        holder.bindItems(list[position])
    }

    //this method is giving the size of the list
    override fun getItemCount(): Int = list.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindItems(data: HomeListObject) {
            val itemImg: ImageView = itemView.findViewById(R.id.iv_homeList_item_img)
            var itemTitle: TextView = itemView.findViewById(R.id.tv_homeList_item_title)

            var passImg: Drawable? = null
            var title:String = ""
            when(data.type){
                "food" -> {
                    passImg = ContextCompat.getDrawable(itemView.context,R.drawable.ic_baby_bottle_light)
                    title = itemView.resources.getString(R.string.routine_list_feeding)
                }
                "sleep" -> {
                    passImg = ContextCompat.getDrawable(itemView.context,R.drawable.ic_sleep_light)
                    title = itemView.resources.getString(R.string.routine_list_sleep)
                }
                "diaper" -> {
                    passImg = ContextCompat.getDrawable(itemView.context,R.drawable.ic_nappy_light)
                    title = itemView.resources.getString(R.string.routine_list_diapers)
                }
                "play" -> {
                    passImg = ContextCompat.getDrawable(itemView.context,R.drawable.ic_breastfeeding_light)
                    title = itemView.resources.getString(R.string.routine_list_physical_activity)
                }
                "walk" -> {
                    passImg = ContextCompat.getDrawable(itemView.context,R.drawable.ic_sleep_light)
                    title = itemView.resources.getString(R.string.routine_list_walks)
                }
            }

            itemTitle.text = title

            passImg!!.setBounds(0, 0, 64, 64)
            itemImg.setImageDrawable(passImg)
            //Glide.with(itemView).load(data.img).into(itemImg)
            println(data.type)
            //set the onclick listener for the singlt list item
            itemView.setOnClickListener {v -> run{
                if (data.type == "food"){
                    v!!.context.startActivity(Intent(v.context, RoutineStreamActivity::class.java))
                }

            } }

            //view!!.context.startActivity(Intent(view.context, FoodRoutineActivity::class.java))
        }
    }
}