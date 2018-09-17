package com.ryzko.nibu.view.fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide

import com.ryzko.nibu.R
import com.ryzko.nibu.model.uiobjects.HomeListObject
import com.ryzko.nibu.model.adapters.DailyRoutinesListAdapter
import com.ryzko.nibu.model.user.UserData
import com.ryzko.nibu.model.utils.DateParseUtils
import kotlinx.android.synthetic.main.fragment_dashboard_home.*
import org.joda.time.DateTime
import org.joda.time.Weeks
import java.time.DateTimeException

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [DashboardDailyRoutinesFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [DashboardDailyRoutinesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DashboardDailyRoutinesFragment : Fragment() {

    // TODO: Rename and change types of parameters
    private var mParam1: String? = null
    private var mParam2: String? = null

    private var mListener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = arguments!!.getString(ARG_PARAM1)
            mParam2 = arguments!!.getString(ARG_PARAM2)
        }


    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Glide.with(this).load(UserData.selectedBaby.avatar).into(image_baby_avatar)
        text_baby_name.text = UserData.selectedBaby.name
        val birthday = DateParseUtils.getDateTime(UserData.selectedBaby.birth_date, DateParseUtils.YYYYMMDD_HHMMSS)
        val weekNo = Weeks.weeksBetween(birthday, DateTime.now()).weeks
        text_baby_birth_date.text = String.format(resources.getQuantityString(R.plurals.number_of_weeks, weekNo, DateParseUtils.getString(birthday, DateParseUtils.ddMMMyyyy), weekNo))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view: View = inflater.inflate(R.layout.fragment_dashboard_home, container, false)
        val list: List<HomeListObject> = listOf(HomeListObject("food"), HomeListObject("diaper"), HomeListObject("sleep"), HomeListObject("play"), HomeListObject("walk"))
        val adapter = DailyRoutinesListAdapter(list)
        val grid: RecyclerView = view.findViewById(R.id.home_grid)

        grid.layoutManager = LinearLayoutManager(context)
        grid.adapter = adapter


        return view
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
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private val ARG_PARAM1 = "param1"
        private val ARG_PARAM2 = "param2"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DashboardDailyRoutinesFragment.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(param1: String = "a", param2: String = "b"): DashboardDailyRoutinesFragment {
            val fragment = DashboardDailyRoutinesFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }
}// Required empty public constructor
