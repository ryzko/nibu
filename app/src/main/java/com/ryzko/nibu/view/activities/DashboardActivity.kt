package com.ryzko.nibu.view.activities

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.util.Log.d
import com.ryzko.nibu.R
import com.ryzko.nibu.model.RegisterObject
import com.ryzko.nibu.model.api.ApiError
import com.ryzko.nibu.model.api.NibuApi
import com.ryzko.nibu.view.fragments.DashboardHomeFragment
import com.ryzko.nibu.view.fragments.DashboardSettingsFragment
import com.ryzko.nibu.view.fragments.DashboardUserFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_dashboard.*
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

class DashboardActivity : AppCompatActivity(), DashboardHomeFragment.OnFragmentInteractionListener, DashboardUserFragment.OnFragmentInteractionListener, DashboardSettingsFragment.OnFragmentInteractionListener  {

    var result = mutableListOf<String>()


    override fun onFragmentInteraction(uri: Uri) {

    }


    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->

        var selectedFragment: Fragment? = null
        when (item.itemId) {

            R.id.navigation_home -> {
                //message.setText(R.string.title_home)
                selectedFragment = DashboardHomeFragment.newInstance()
               // return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_user -> {
                //message.setText(R.string.title_user)
                selectedFragment = DashboardUserFragment.newInstance()
               // return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_settings -> {
               // message.setText(R.string.title_settings)
                selectedFragment = DashboardSettingsFragment.newInstance()
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
        replaceFragment(DashboardHomeFragment.newInstance())
        setSupportActionBar(toolbar)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }
}
