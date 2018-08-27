package com.ryzko.nibu.view.components

import android.content.Context
import android.content.res.AssetManager
import android.util.AttributeSet
import com.ogaclejapan.smarttablayout.SmartTabLayout
import android.graphics.Typeface
import android.widget.TextView



/**
 * Created by Marcin Ryzko on 23.08.2018.
 * marcin@ryzko.com
 * http://ryzko.com
 */

class CustomSmartTabLayout @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : SmartTabLayout(context, attrs, defStyleAttr) {

    override fun createDefaultTabView(title: CharSequence): TextView {
        val textView = super.createDefaultTabView(title)
        val type = Typeface.createFromAsset(context.assets, "fonts/Lato-Regular.ttf")
        textView.setTypeface(type)
        //textView.typeface = Typeface.DEFAULT
        return textView
    }
}