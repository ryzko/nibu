package com.ryzko.nibu.model.adapters

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.view.View
import android.view.ViewGroup
import com.ryzko.nibu.model.rest.routines.FoodRoutineObjectData
import org.zakariya.stickyheaders.SectioningAdapter
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import com.ryzko.nibu.Nibu
import com.ryzko.nibu.R
import com.ryzko.nibu.model.user.UserData
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter


/**
 * Created by Marcin Ryzko on 27.11.2017.
 * marcin@ryzko.com
 * http://ryzko.com
 */

class FoodRoutinesListAdapter(private var list:MutableList<FoodRoutineObjectData>): SectioningAdapter() {

    lateinit var sections:MutableList<Section>

    class Section(var date:String, var list:MutableList<FoodRoutineObjectData>, var time:Int = 0, var volume:Int = 0, var weight:Int = 0)


    init {
        cutToSections(this.list)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun cutToSections(list:MutableList<FoodRoutineObjectData>){
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
                currentSection.volume+=item.volume
                currentSection.time+=item.breastfeeding_time_minutes
                currentSection.weight+=item.weight
                currentSection.list.add(item)
            }


        }

        sections.add(currentSection!!)

        //UserData.sectionedFoodList = sections
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
        val breastItem:ConstraintLayout = itemView.findViewById(R.id.breast_item)
        val bottleItem:ConstraintLayout = itemView.findViewById(R.id.bottle_item)
        val solidItem:ConstraintLayout = itemView.findViewById(R.id.solid_item)

        val breastStartTime:TextView = itemView.findViewById(R.id.textview_breast_starttime)
        val bottleStartTime:TextView = itemView.findViewById(R.id.textview_bottle_startime)
        val solidStartTime:TextView = itemView.findViewById(R.id.textview_solid_starttime)

        val breastSide:TextView = itemView.findViewById(R.id.textview_brest_side)

        val breastTime:TextView = itemView.findViewById(R.id.textview_breast_time)
        val bottleTime:TextView = itemView.findViewById(R.id.textview_bottle_time)
        val solidTime:TextView = itemView.findViewById(R.id.textview_solid_time)

    }


    override fun onCreateItemViewHolder(parent: ViewGroup?, itemUserType: Int): SectioningAdapter.ItemViewHolder {
        val inflater = LayoutInflater.from(parent!!.context)
        val v = inflater.inflate(R.layout.list_foudroutine_item, parent, false)
        return SectioningAdapter.ItemViewHolder(v)
    }

    class FooterViewHolder(itemView:View): SectioningAdapter.FooterViewHolder(itemView) {
        var breastFooterItem:LinearLayout = itemView.findViewById(R.id.breast_footer_item)
        var bottleFooterItem:LinearLayout = itemView.findViewById(R.id.bottle_footer_item)
        var solidFooterItem:LinearLayout = itemView.findViewById(R.id.solid_footer_item)

        var breastTotal:TextView = itemView.findViewById(R.id.breast_total)
        var bottleTotal:TextView = itemView.findViewById(R.id.bottle_total)
        var solidTotal:TextView = itemView.findViewById(R.id.solid_total)
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
       // super.onBindHeaderViewHolder(viewHolder, sectionIndex, headerUserType)
        val s:Section = sections[sectionIndex]
        val vh:FoodRoutinesListAdapter.HeaderViewHolder = FoodRoutinesListAdapter.HeaderViewHolder(viewHolder!!.itemView)
        val date = SimpleDateFormat("yyyy-MM-dd").parse(s.date)//LocalDate.parse(s.date, DateTimeFormatter.ofPattern("dd-MM-yyyy"))
        val headerFormat = SimpleDateFormat("EEEE, dd MMMM yyyy")
        vh.title.text = headerFormat.format(date)

    }

    override fun onBindItemViewHolder(viewHolder: SectioningAdapter.ItemViewHolder?, sectionIndex: Int, itemIndex: Int, itemUserType: Int) {
        //super.onBindItemViewHolder(viewHolder, sectionIndex, itemIndex, itemUserType)

        val s:Section = sections[sectionIndex]
        val vh:FoodRoutinesListAdapter.ItemViewHolder = FoodRoutinesListAdapter.ItemViewHolder(viewHolder!!.itemView)
        val foodItem:FoodRoutineObjectData = s.list[itemIndex]

        var type:String = foodItem.type

        vh.bottleItem.visibility = View.GONE
        vh.solidItem.visibility = View.GONE
        vh.breastItem.visibility = View.GONE

        when(type){
            "breast" -> {
                vh.breastItem.visibility = View.VISIBLE
                vh.breastStartTime.text = foodItem.start_time.substring(11)
                vh.breastTime.text = "${Math.floor((foodItem.breastfeeding_time_minutes/60).toDouble()).toInt()} h ${foodItem.breastfeeding_time_minutes%60} min "
                vh.breastSide.text = foodItem.breast_side
            }
            "solid" -> {
                vh.solidItem.visibility = View.VISIBLE
                vh.solidStartTime.text = foodItem.start_time.substring(11)
                vh.solidTime.text = "${foodItem.weight} g"
            }
            "bottle" -> {
                vh.bottleItem.visibility = View.VISIBLE
                vh.bottleStartTime.text = foodItem.start_time.substring(11)
                vh.bottleTime.text = "${foodItem.volume} ml"
            }
        }





    }


    override fun onBindFooterViewHolder(viewHolder: SectioningAdapter.FooterViewHolder?, sectionIndex: Int, footerUserType: Int) {
        super.onBindFooterViewHolder(viewHolder, sectionIndex, footerUserType)

        val s:Section = sections[sectionIndex]
        val vh:FoodRoutinesListAdapter.FooterViewHolder = FoodRoutinesListAdapter.FooterViewHolder(viewHolder!!.itemView)
        vh.bottleFooterItem.visibility = View.GONE
        vh.breastFooterItem.visibility = View.GONE
        vh.solidFooterItem.visibility = View.GONE

        if(s.time>0){
            vh.breastFooterItem.visibility = View.VISIBLE
            vh.breastTotal.text = "${Math.floor((s.time/60).toDouble()).toInt()} h ${s.time%60} min "
        }

        if(s.volume>0){
            vh.bottleFooterItem.visibility = View.VISIBLE
            vh.bottleTotal.text = "${s.volume} ml"
        }

        if(s.volume>0){
            vh.solidFooterItem.visibility = View.VISIBLE
            vh.solidTotal.text = "${s.weight} g"
        }
    }

}