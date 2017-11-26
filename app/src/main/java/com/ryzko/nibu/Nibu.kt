package com.ryzko.nibu

import android.app.Application
import uk.co.chrisjenx.calligraphy.CalligraphyConfig
import com.joanzapata.iconify.fonts.SimpleLineIconsModule
import com.joanzapata.iconify.Iconify
import com.joanzapata.iconify.fonts.IoniconsModule


/**
 * Created by Marcin Ryzko on 25.11.2017.
 * marcin@ryzko.com
 * http://ryzko.com
 */

class Nibu: Application() {

    override fun onCreate() {
        super.onCreate()

        CalligraphyConfig.initDefault(CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Lato-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build())

        Iconify.with(SimpleLineIconsModule())
                .with(IoniconsModule())

    }

}