package com.ryzko.nibu.view.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.ryzko.nibu.R
import com.ryzko.nibu.model.BabyResponse
import com.ryzko.nibu.model.LoginObject
import com.ryzko.nibu.model.TokenObject
import com.ryzko.nibu.model.User
import com.ryzko.nibu.model.api.ApiError
import com.ryzko.nibu.model.api.ApiManager
import com.ryzko.nibu.model.user.UserData
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    var userData:UserData? = null
    var apiManager:ApiManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        apiManager = ApiManager.getInstance(this.applicationContext)
        userData = UserData.getInstance(this.applicationContext)

        btn_login.setOnClickListener({
            val loginObj = LoginObject(tv_username.text.toString(),tv_password.text.toString())
            apiManager!!.login(loginObj)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        token: TokenObject? ->
                        if (token != null) {
                            onLoginSuccess(token)
                        }
                    },{
                        error: Throwable? ->
                        if (error != null) {
                            onRequestFailure(error)
                        }
                    })
        })
    }


    private fun onLoginSuccess(token: TokenObject){
        userData?.tokenObj = token;
        apiManager!!.user(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({

            user: User? ->  if (user != null) onUserSuccess(user)
        },{
            error: Throwable? -> if (error != null) onRequestFailure(error)

        })
    }

    private fun onRequestFailure(error: Throwable){

    }

    private fun onUserSuccess(user:User){
        userData?.userObj = user;

        startActivity(Intent(this, DashboardActivity::class.java))

    }


}
