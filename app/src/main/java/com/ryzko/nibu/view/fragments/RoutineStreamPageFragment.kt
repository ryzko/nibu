package com.ryzko.nibu.view.fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ryzko.nibu.R
import com.ryzko.nibu.model.activities.ActivityDayData
import com.ryzko.nibu.model.adapters.ActivityRoutineAdapter
import com.ryzko.nibu.model.events.ActivitiesResultEvent
import com.ryzko.nibu.model.events.RoutinesScrollEvent
import com.ryzko.nibu.model.rest.routines.ActivityRoutineObjectData
import com.ryzko.nibu.model.user.UserData
import com.ryzko.rxminibus.RxMiniBus
import com.ryzko.rxminibus.registerWith
import kotlinx.android.synthetic.main.activity_routine_stream.*
import kotlinx.android.synthetic.main.fragment_routine_stream_page.*


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [RoutineStreamPageFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [RoutineStreamPageFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RoutineStreamPageFragment : Fragment() {

    // TODO: Rename and change types of parameters
    private var mParam1: String? = null
    private var mParam2: String? = null

    private var mListener: OnFragmentInteractionListener? = null
    private var activityList: MutableList<ActivityRoutineObjectData>? = null

    private var thisView:View? = null


    private lateinit var activityAdapter: ActivityRoutineAdapter

    fun updateData(data: ActivityDayData?){

        activityList = data?.activityList

    }

    fun initData(){
        if(activityList!=null && recycler_view_activities!=null){
            activityAdapter = ActivityRoutineAdapter(activityList!!)
            recycler_view_activities.layoutManager = LinearLayoutManager(this.context)
            recycler_view_activities?.adapter = activityAdapter
            activityAdapter.notifyDataSetChanged()

        }

    }

    fun getRecyclerView():RecyclerView?{
        return recycler_view_activities
    }

    fun setVisibility(){
        if(thisView == null) return

        if(activityList==null){
            noDataText.visibility = View.VISIBLE
            recycler_view_activities.visibility = View.GONE
            iconEmpty?.visibility = View.VISIBLE
        }else{
            noDataText.visibility = View.GONE
            recycler_view_activities.visibility = View.VISIBLE
            iconEmpty?.visibility = View.GONE
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setVisibility()
        initData()
        layout_swipe_refresh.setOnRefreshListener {
            UserData.activityDataService.getAllActivitiesRoutines()
        }

        RxMiniBus.observe<ActivitiesResultEvent>().subscribe {
            activityAdapter = ActivityRoutineAdapter(it.list)
            activityAdapter.notifyDataSetChanged()
            layout_swipe_refresh.isRefreshing = false
        }.registerWith(this)

        recycler_view_activities.addOnScrollListener(object:RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0)
                {
                    RxMiniBus.post(RoutinesScrollEvent(true))
                }else{
                    RxMiniBus.post(RoutinesScrollEvent(false))
                }
            }

        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        thisView = inflater.inflate(R.layout.fragment_routine_stream_page, container, false)

        return thisView
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        if (mListener != null) {
            mListener!!.onFragmentInteraction(uri)
        }
    }


    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            mListener = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        RxMiniBus.unregister(this)
        mListener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html) for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        // TODO: Rename parameter arguments, choose names that match

        fun newInstance(): RoutineStreamPageFragment {
            return RoutineStreamPageFragment()
        }
    }
}// Required empty public constructor
