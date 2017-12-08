package com.ryzko.nibu.model.adapters

import android.support.v7.widget.RecyclerView
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem

/**
 * Created by Marcin on 2017-12-06.
 */
class FoodRoutineFlexibleAdapter( items: MutableList<AbstractFlexibleItem<RecyclerView.ViewHolder>>?, listeners: Any?)
    : FlexibleAdapter<AbstractFlexibleItem<RecyclerView.ViewHolder>>(items, listeners) {

    override fun isEmpty(): Boolean {
        return super.isEmpty()
    }

    override fun updateDataSet(items: MutableList<AbstractFlexibleItem<RecyclerView.ViewHolder>>?) {
        super.updateDataSet(items)
    }

}