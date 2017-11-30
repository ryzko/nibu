package com.ryzko.nibu.model.adapters

import android.view.View
import android.view.ViewGroup
import com.ryzko.nibu.model.rest.routines.FoodRoutineObjectData
import org.zakariya.stickyheaders.SectioningAdapter
import android.view.LayoutInflater
import android.widget.TextView
import com.ryzko.nibu.R


/**
 * Created by Marcin Ryzko on 27.11.2017.
 * marcin@ryzko.com
 * http://ryzko.com
 */

class FoodRoutinesListAdapter(private val list:List<FoodRoutineObjectData>): SectioningAdapter() {

    private var sections:MutableList<Section> = mutableListOf()
    class Section(var date:String, var list:MutableList<FoodRoutineObjectData>)


    init {
        cutToSections(this.list)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun cutToSections(list:List<FoodRoutineObjectData>){
        sections = mutableListOf()
        var date:String = ""
        var currentSection:Section? = null


        for(item:FoodRoutineObjectData in list){
            if(item.start_time.substring(0,10) != date){
                if(currentSection!=null){
                    sections.add(currentSection)
                }

                var sectionList:MutableList<FoodRoutineObjectData> = mutableListOf()
                date = item.start_time.substring(0,10)
                currentSection = Section(date, sectionList)
            }

            if(currentSection!=null){
                currentSection.list.add(item)
            }


        }

        sections.add(currentSection!!)
        notifyAllSectionsDataSetChanged()
    }




    class HeaderViewHolder(itemView:View):SectioningAdapter.HeaderViewHolder(itemView){
        var title:TextView = itemView.findViewById(R.id.food_routine_header_title)
    }

    override  fun onCreateHeaderViewHolder(parent: ViewGroup?, headerUserType: Int): SectioningAdapter.HeaderViewHolder {
        val inflater = LayoutInflater.from(parent!!.context)
        val v = inflater.inflate(R.layout.list_foodroutine_header, parent, false)
        return SectioningAdapter.HeaderViewHolder(v)
    }

    class ItemViewHolder(itemView:View):SectioningAdapter.ItemViewHolder(itemView){
        val title:TextView = itemView.findViewById(R.id.food_routine_item_title)
    }

    override fun onCreateItemViewHolder(parent: ViewGroup?, itemUserType: Int): SectioningAdapter.ItemViewHolder {
        val inflater = LayoutInflater.from(parent!!.context)
        val v = inflater.inflate(R.layout.list_foudroutine_item, parent, false)
        return SectioningAdapter.ItemViewHolder(v)
    }

    class FooterViewHolder(itemView:View): SectioningAdapter.FooterViewHolder(itemView) {
        var title:TextView = itemView.findViewById(R.id.food_routine_footer_title)
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
        return sections[sectionIndex].list.size
    }

    override fun getNumberOfSections(): Int {
        return sections.size
    }

    override fun onBindHeaderViewHolder(viewHolder: SectioningAdapter.HeaderViewHolder?, sectionIndex: Int, headerUserType: Int) {
        super.onBindHeaderViewHolder(viewHolder, sectionIndex, headerUserType)
        val s:Section = sections[sectionIndex]
        val vh:FoodRoutinesListAdapter.HeaderViewHolder = FoodRoutinesListAdapter.HeaderViewHolder(viewHolder!!.itemView)
        vh.title.text = s.date

    }

    override fun onBindItemViewHolder(viewHolder: SectioningAdapter.ItemViewHolder?, sectionIndex: Int, itemIndex: Int, itemUserType: Int) {
        super.onBindItemViewHolder(viewHolder, sectionIndex, itemIndex, itemUserType)

        val s:Section = sections[sectionIndex]
        val vh:FoodRoutinesListAdapter.ItemViewHolder = FoodRoutinesListAdapter.ItemViewHolder(viewHolder!!.itemView)
        val foodItem:FoodRoutineObjectData = s.list[itemIndex]
        vh.title.text = foodItem.type
    }

    override fun onBindFooterViewHolder(viewHolder: SectioningAdapter.FooterViewHolder?, sectionIndex: Int, footerUserType: Int) {
        super.onBindFooterViewHolder(viewHolder, sectionIndex, footerUserType)

        val s:Section = sections[sectionIndex]
        val vh:FoodRoutinesListAdapter.FooterViewHolder = FoodRoutinesListAdapter.FooterViewHolder(viewHolder!!.itemView)
        vh.title.text = "footer [...]"
    }

}