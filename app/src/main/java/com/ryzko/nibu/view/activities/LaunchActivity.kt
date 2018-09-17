package com.ryzko.nibu.view.activities

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.ryzko.nibu.R
import android.content.Intent
import com.ryzko.nibu.model.api.ApiManager
import com.ryzko.nibu.model.rest.BabyObjectData
import com.ryzko.nibu.model.rest.routines.ActivityRoutineObjectData
import com.ryzko.nibu.model.user.Credentials
import com.ryzko.nibu.model.user.UserCredentials
import com.ryzko.nibu.model.user.UserData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_launch.*
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper


class LaunchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch)
        text_init_info.text = getString(R.string.splash_screen_init)
        val credentials: Credentials? = UserCredentials(this.applicationContext).load()
        if (credentials != null) {
            UserData.userObj = credentials.userObject
            UserData.tokenObj = credentials.refreshToken
            UserData.selectedBabySid = credentials.selectedBabySid

            getBabies()
        } else {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    private fun getBabies() {
        text_init_info.text = getString(R.string.splash_screen_babies_list)
        ApiManager.getAllBabies()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ list: ArrayList<BabyObjectData> ->
                    this.onBabiesResult(list)
                }, { error: Throwable? ->
                    if (error != null) onBabiesFailure()

                })
    }

    private fun onBabiesResult(list: ArrayList<BabyObjectData>) {
        UserData.babyList = list
        if (UserData.selectedBabySid == "") UserData.selectedBabySid = list[0].sid
        val selectedBaby: BabyObjectData? = list.find { babyObjectData -> babyObjectData.sid == UserData.selectedBabySid }
        if (selectedBaby != null) UserData.selectedBaby = selectedBaby

        getAllActivitiesRoutines()
    }

    private fun onBabiesFailure() {
        startActivity(Intent(this, LoginActivity::class.java))
    }

    private fun getAllActivitiesRoutines() {
        text_init_info.text = getString(R.string.splash_screen_routines_list)
        ApiManager.getAllActivityRoutines()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ list: MutableList<ActivityRoutineObjectData> ->
                    onGetActivities(list)
                }) { error: Throwable? ->
                    if (error != null) onActivitiesFailure()

                }
    }

    private fun onGetActivities(list: MutableList<ActivityRoutineObjectData>) {
        UserData.activityDataService.addList(list)
        startActivity(Intent(this, DashboardActivity::class.java))
        finish()
    }

    private fun onActivitiesFailure() {

    }


    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }
}
