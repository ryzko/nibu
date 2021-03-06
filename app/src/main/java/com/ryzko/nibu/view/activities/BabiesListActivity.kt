package com.ryzko.nibu.view.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.widget.LinearLayout
import com.ryzko.nibu.R
import com.ryzko.nibu.model.adapters.BabiesListAdapter
import com.ryzko.nibu.model.api.ApiManager
import com.ryzko.nibu.model.rest.BabyObjectData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_babies_list.*

class BabiesListActivity : AppCompatActivity() {


    var apiManager: ApiManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_babies_list)
        rv_babies_list.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        getBabies()
    }

    fun getBabies() {
        apiManager!!.getAllBabies()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ list: ArrayList<BabyObjectData> ->
                    getBabies(list)
                }, { error: Throwable? ->
                    if (error != null) onRequestFailure(error)

                })
    }

    fun getBabies(list: ArrayList<BabyObjectData>) {
        val adapter = BabiesListAdapter(list)
        rv_babies_list.adapter = adapter
    }

    private fun onRequestFailure(error: Throwable) {

    }
}
