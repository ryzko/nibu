package com.ryzko.nibu

import android.app.Application
import android.content.Context
import android.widget.Toast
import uk.co.chrisjenx.calligraphy.CalligraphyConfig
import com.joanzapata.iconify.fonts.SimpleLineIconsModule
import com.joanzapata.iconify.Iconify
import com.joanzapata.iconify.fonts.IoniconsModule
import com.ryzko.nibu.model.events.RxBus


/**
 * Created by Marcin Ryzko on 25.11.2017.
 * marcin@ryzko.com
 * http://ryzko.com
 */

class Nibu: Application() {




    init {
        instance = this

    }

    companion object {
        private var instance: Nibu? = null
        private val DEBUG_MODE = true
        fun showShortToast(msg: String){
            if(DEBUG_MODE) Toast.makeText(instance, msg, Toast.LENGTH_SHORT).show()
        }
    }


    fun applicationContext() : Context {
        return instance!!.applicationContext
    }


    fun getInstance(): Nibu? {
        return instance
    }



    override fun onCreate() {
        super.onCreate()


        CalligraphyConfig.initDefault(CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Roboto-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build())

        Iconify.with(SimpleLineIconsModule())
                .with(IoniconsModule())

    }

}