package com.ryzko.nibu.model.adapters

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.ryzko.nibu.R
import com.ryzko.nibu.model.BabyResponse
import com.ryzko.nibu.model.UIObjects.HomeListObject

/**
 * Created by Marcin Ryzko on 23.11.2017.
 * marcin@ryzko.com
 * http://ryzko.com
 */

class HomeListAdapter(private val list:List<HomeListObject>): RecyclerView.Adapter<HomeListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeListAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_view_home, parent, false)
        return ViewHolder(v)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: HomeListAdapter.ViewHolder, position: Int) {
        holder.bindItems(list[position])
    }

    //this method is giving the size of the list
    override fun getItemCount(): Int = list.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindItems(data: HomeListObject) {
            val itemImg: ImageView = itemView.findViewById(R.id.iv_homeList_item_img)
            val itemTitle: TextView = itemView.findViewById(R.id.tv_homeList_item_title)

            Glide.with(itemView).load(data.img).into(itemImg)
            itemTitle.text = data.title

            //set the onclick listener for the singlt list item
            itemView.setOnClickListener({
                Log.e("ItemClicked", data.title)
            })
        }
    }
}