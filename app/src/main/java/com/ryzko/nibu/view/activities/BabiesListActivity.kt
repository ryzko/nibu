package com.ryzko.nibu.view.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.widget.LinearLayout
import com.ryzko.nibu.R
import com.ryzko.nibu.model.BabyResponse
import com.ryzko.nibu.model.adapters.BabiesListAdapter
import com.ryzko.nibu.model.api.ApiManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_babies_list.*

class BabiesListActivity : AppCompatActivity() {


    var apiManager: ApiManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_babies_list)
        rv_babies_list.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        apiManager = ApiManager.getInstance(this.applicationContext)
        getBabies();
    }

    fun getBabies() {
        apiManager!!.getAllBabies()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ list: ArrayList<BabyResponse> ->
                    if (list != null) getBabies(list)
                }, { error: Throwable? ->
                    if (error != null) onRequestFailure(error)

                })
    }

    fun getBabies(list: ArrayList<BabyResponse>) {
        val adapter = BabiesListAdapter(list)
        rv_babies_list.adapter = adapter;
    }

    private fun onRequestFailure(error: Throwable) {

    }
}
