package com.ryzko.nibu.model.adapters.items

import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem
import eu.davidea.flexibleadapter.items.IFlexible
import eu.davidea.viewholders.FlexibleViewHolder
import com.ryzko.nibu.R
import com.ryzko.nibu.model.rest.routines.FoodRoutineObjectData
import kotlinx.android.synthetic.main.list_foudroutine_item.view.*

/**
 * Created by Marcin on 2017-12-06.
 */
class FoodRoutineFlexibleItem constructor(foodItem:FoodRoutineObjectData): AbstractFlexibleItem<FoodRoutineFlexibleItem.FoodViewHolder>() {

    var foodItem:FoodRoutineObjectData = foodItem



    override fun bindViewHolder(adapter: FlexibleAdapter<out IFlexible<*>>?, holder: FoodViewHolder?, position: Int, payloads: MutableList<Any?>?) {

        holder!!.breastLayout.visibility = View.GONE
        holder.solidLayout.visibility = View.GONE
        holder.bottleLayout.visibility = View.GONE


        when(foodItem.type){
            "breast"-> holder.breastLayout.visibility = View.VISIBLE
            "solid"-> holder.breastLayout.visibility = View.VISIBLE
            "bottle"-> holder.breastLayout.visibility = View.VISIBLE

        }
    }

    override fun equals(other: Any?): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getLayoutRes(): Int {
        return R.layout.list_foudroutine_item
    }

    override fun createViewHolder(view: View?, adapter: FlexibleAdapter<out IFlexible<*>>?): FoodViewHolder {
        return FoodViewHolder(view, adapter, true)
    }

    class FoodViewHolder(view: View?, adapter: FlexibleAdapter<out IFlexible<*>>?, stickyHeader: Boolean) : FlexibleViewHolder(view, adapter, stickyHeader) {
        var breastLayout:LinearLayout = view!!.findViewById(R.id.breast_item)
        var solidLayout:LinearLayout = view!!.findViewById(R.id.solid_item)
        var bottleLayout:LinearLayout = view!!.findViewById(R.id.bottle_item)
    }
}