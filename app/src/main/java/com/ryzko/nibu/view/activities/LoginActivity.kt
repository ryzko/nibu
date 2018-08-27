package com.ryzko.nibu.view.activities

import android.content.Context
import android.content.Intent
import android.opengl.Visibility
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.ryzko.nibu.R
import com.ryzko.nibu.R.id.login_container
import com.ryzko.nibu.R.id.login_progress
import com.ryzko.nibu.model.adapters.BabiesListAdapter
import com.ryzko.nibu.model.api.ApiManager
import com.ryzko.nibu.model.rest.*
import com.ryzko.nibu.model.rest.routines.ActivityRoutineObjectData
import com.ryzko.nibu.model.user.UserData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_babies_list.*
import kotlinx.android.synthetic.main.activity_login.*
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper


class LoginActivity : AppCompatActivity() {

   // lateinit var userData:UserData
   // lateinit var apiManager:ApiManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //userData = UserData.instance
       // apiManager = ApiManager.instance

        setDrawables()


        btn_login.setOnClickListener({
            val loginObj = LoginObjectData(tv_username.text.toString(), tv_password.text.toString())
            login_container.visibility = View.GONE
            login_progress.visibility = View.VISIBLE
            ApiManager.login(loginObj)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ token: TokenObjectData? ->
                        if (token != null) {
                            onLoginSuccess(token)
                        }
                    }, { error: Throwable? ->
                        if (error != null) {
                            onRequestFailure(error)
                        }
                    })
        })
    }


    private fun onLoginSuccess(token: TokenObjectData) {
        UserData.tokenObj = token
        ApiManager.user(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({

                    user: UserObjectData? ->
                    if (user != null) onUserSuccess(user)
                }, { error: Throwable? ->
                    if (error != null) onRequestFailure(error)

                })
    }

    private fun onRequestFailure(error: Throwable) {
        login_container.visibility = View.VISIBLE
        login_progress.visibility = View.GONE
    }

    private fun onUserSuccess(user: UserObjectData) {
        UserData.userObj = user


        startActivity(Intent(this, DashboardActivity::class.java))

        getBabies()
    }

    fun getBabies() {
        ApiManager.getAllBabies()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ list: ArrayList<BabyObjectData> ->
                    if (list != null) getBabies(list)
                }, { error: Throwable? ->
                    if (error != null) onRequestFailure(error)

                })
    }

    fun getBabies(list: MutableList<BabyObjectData>) {

        UserData.babyList = list
        UserData.selectedBaby = list.get(0)


        ApiManager.getActivityRoutinesByDay()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ alist: MutableList<GroupedByDayObject> ->
                    if (alist != null) getActivities(alist)
                }, { error: Throwable? ->
                    if (error != null) onRequestFailure(error)

                })

    }

    fun getActivities(list:MutableList<GroupedByDayObject>){
        var ls = list
    }


    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }

    private fun setDrawables() {


        val loginImg = ContextCompat.getDrawable(this.baseContext, R.drawable.message_96px)
        val h = loginImg!!.intrinsicHeight
        val w = loginImg.intrinsicWidth
        loginImg.setBounds(0, 0, 64, 64)

        val passImg = ContextCompat.getDrawable(this.baseContext, R.drawable.key_96px)
        val h1 = passImg!!.intrinsicHeight
        val w1 = passImg.intrinsicWidth
        passImg.setBounds(0, 0, 64, 64)

        tv_username.setCompoundDrawables(loginImg, null, null, null)
        tv_password.setCompoundDrawables(passImg, null, null, null)

    }


}
