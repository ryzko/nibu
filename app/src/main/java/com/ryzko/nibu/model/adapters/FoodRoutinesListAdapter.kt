package com.ryzko.nibu.model.adapters

import android.view.View
import android.view.ViewGroup
import com.ryzko.nibu.model.rest.routines.FoodRoutineObjectData
import org.zakariya.stickyheaders.SectioningAdapter
import android.view.LayoutInflater
import com.ryzko.nibu.R


/**
 * Created by Marcin Ryzko on 27.11.2017.
 * marcin@ryzko.com
 * http://ryzko.com
 */

class FoodRoutinesListAdapter(private val list:List<FoodRoutineObjectData>): SectioningAdapter() {

    private var sections:List<Section> = listOf()
    class Section(var date:String, var list:List<FoodRoutineObjectData>)

    override fun getItemCount(): Int {
        return list.size
    }

    fun cutToSections(){

    }


    class HeaderViewHolder(itemView:View):SectioningAdapter.HeaderViewHolder(itemView)

    override  fun onCreateHeaderViewHolder(parent: ViewGroup?, headerUserType: Int): SectioningAdapter.HeaderViewHolder {
        val inflater = LayoutInflater.from(parent!!.context)
        val v = inflater.inflate(R.layout.list_foodroutine_header, parent, false)
        return SectioningAdapter.HeaderViewHolder(v)
    }

    class ItemViewHolder(itemView:View):SectioningAdapter.ItemViewHolder(itemView){

    }

    override fun onCreateItemViewHolder(parent: ViewGroup?, itemUserType: Int): SectioningAdapter.ItemViewHolder {
        val inflater = LayoutInflater.from(parent!!.context)
        val v = inflater.inflate(R.layout.list_foudroutine_item, parent, false)
        return SectioningAdapter.ItemViewHolder(v)
    }

    class FooterViewHolder(itemView:View): SectioningAdapter.FooterViewHolder(itemView) {

    }

    override fun onCreateFooterViewHolder(parent: ViewGroup?, footerUserType: Int): SectioningAdapter.FooterViewHolder {
        val inflater = LayoutInflater.from(parent!!.context)
        val v = inflater.inflate(R.layout.list_foodroutine_footer, parent, false)
        return SectioningAdapter.FooterViewHolder(v)
    }

    override fun doesSectionHaveFooter(sectionIndex: Int): Boolean {
        return true
    }

    override fun doesSectionHaveHeader(sectionIndex: Int): Boolean {
        return true
    }

    override fun getNumberOfItemsInSection(sectionIndex: Int): Int {
        return sections.get(sectionIndex).list.size
    }

}