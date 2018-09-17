package com.ryzko.nibu.view.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.ryzko.nibu.Nibu
import com.ryzko.nibu.R
import com.ryzko.nibu.model.api.ApiManager
import com.ryzko.nibu.model.rest.*
import com.ryzko.nibu.model.user.Credentials
import com.ryzko.nibu.model.user.UserCredentials
import com.ryzko.nibu.model.user.UserData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
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


        btn_login.setOnClickListener {
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
                            onLoginFailure()
                        }
                    })
        }
    }


    private fun onLoginSuccess(token: TokenObjectData) {
        Nibu.showShortToast("Login success!")
        UserData.tokenObj = token
        ApiManager.user(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({

                    user: UserObjectData? ->
                    if (user != null) onUserSuccess(user)
                }, { error: Throwable? ->
                    if (error != null) onUserFailure()

                })
    }

    private fun onUserFailure() {
        Nibu.showShortToast("User GET Fail!")
    }

    private fun onLoginFailure() {
        Nibu.showShortToast("Login Fail!")
        login_container.visibility = View.VISIBLE
        login_progress.visibility = View.GONE
    }

    private fun onUserSuccess(user: UserObjectData) {
        UserData.userObj = user
        getBabies()
    }

    private fun getBabies() {
        ApiManager.getAllBabies()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ this.getBabies(it) }) { error: Throwable? ->
                    if (error != null) onLoginFailure()

                }
    }

    private fun getBabies(list: MutableList<BabyObjectData>) {

        Nibu.showShortToast("Login Fail!")
        UserData.babyList = list
        if (UserData.selectedBabySid == "") UserData.selectedBabySid = list[0].sid
        val selectedBaby: BabyObjectData? = list.find { babyObjectData -> babyObjectData.sid == UserData.selectedBabySid }
        if (selectedBaby != null) UserData.selectedBaby = selectedBaby

        val credentials = Credentials(UserData.userObj, UserData.tokenObj, UserData.selectedBabySid)
        UserCredentials(this.applicationContext).save(credentials)
        startActivity(Intent(this, DashboardActivity::class.java))
        finish()

    }


    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }



}
