package com.ryzko.nibu.model.adapters

import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.ryzko.nibu.R
import com.ryzko.nibu.model.uiobjects.HomeListObject

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

            var passImg: Drawable? = null
            when(data.type){
                "food" -> passImg = ContextCompat.getDrawable(itemView.context,R.drawable.baby_bottle_96px)
                "sleep" -> passImg = ContextCompat.getDrawable(itemView.context,R.drawable.crib_96px)
                "diaper" -> passImg = ContextCompat.getDrawable(itemView.context,R.drawable.nappy_96px)
                "play" -> passImg = ContextCompat.getDrawable(itemView.context,R.drawable.sandbox_96px)
                "walk" -> passImg = ContextCompat.getDrawable(itemView.context,R.drawable.mother_96px)
            }
            passImg!!.setBounds(0, 0, 64, 64)
            itemImg.setImageDrawable(passImg)
            //Glide.with(itemView).load(data.img).into(itemImg)
            itemTitle.text = data.title

            //set the onclick listener for the singlt list item
            itemView.setOnClickListener({
                Log.e("ItemClicked", data.title)
            })
        }
    }
}