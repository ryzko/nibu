package com.ryzko.nibu.view.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.ryzko.nibu.R
import android.content.Intent
import android.view.View


class LaunchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startActivity(Intent(this, LoginActivity::class.java))
        // close splash activity

        finish()
    }
}
