package com.ryzko.nibu.model.adapters

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.ryzko.nibu.R
import com.ryzko.nibu.model.rest.BabyObjectData

/**
 * Created by Marcin Ryzko on 20.11.2017.
 * marcin@ryzko.com
 * http://ryzko.com
 */

class BabiesListAdapter(val list: List<BabyObjectData>) : RecyclerView.Adapter<BabiesListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BabiesListAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_view_baby, parent, false)
        return ViewHolder(v)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: BabiesListAdapter.ViewHolder, position: Int) {
        holder.bindItems(list[position])
    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindItems(data: BabyObjectData) {
            val babyName: TextView = itemView.findViewById(R.id.tv_baby_name)
            val babyBirth: TextView = itemView.findViewById(R.id.tv_baby_bitrh)
            babyName.text = data.name
            babyBirth.text = data.birth_date

            //set the onclick listener for the singlt list item
            itemView.setOnClickListener {
                Log.e("ItemClicked", data.name)
            }
        }
    }
}