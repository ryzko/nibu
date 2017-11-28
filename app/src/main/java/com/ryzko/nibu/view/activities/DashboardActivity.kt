package com.ryzko.nibu.view.activities

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.ryzko.nibu.R

import com.ryzko.nibu.view.fragments.DashboardDailyRoutinesFragment
import com.ryzko.nibu.view.fragments.DashboardBodyHealthFragment
import com.ryzko.nibu.view.fragments.DashboardUserFragment
import kotlinx.android.synthetic.main.activity_dashboard.*
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

class DashboardActivity : AppCompatActivity(), DashboardDailyRoutinesFragment.OnFragmentInteractionListener, DashboardUserFragment.OnFragmentInteractionListener, DashboardBodyHealthFragment.OnFragmentInteractionListener  {

    var result = mutableListOf<String>()


    override fun onFragmentInteraction(uri: Uri) {

    }


    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->

        var selectedFragment: Fragment? = null
        when (item.itemId) {

            R.id.navigation_routines -> {
                //message.setText(R.string.title_home)
                selectedFragment = DashboardDailyRoutinesFragment.newInstance()
               // return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_health -> {
                //message.setText(R.string.title_user)
                selectedFragment = DashboardUserFragment.newInstance()
               // return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_settings -> {
               // message.setText(R.string.title_settings)
                selectedFragment = DashboardBodyHealthFragment.newInstance()
              //  return@OnNavigationItemSelectedListener true
            }
        }

        replaceFragment(selectedFragment!!)

        true
    }






    fun replaceFragment(fragment: Fragment){
        println("click")
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.frame_layout, fragment)
                .commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        replaceFragment(DashboardDailyRoutinesFragment.newInstance())
        setSupportActionBar(toolbar)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }
}
