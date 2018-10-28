package com.example.henrikandersson.bbcrssapp.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.henrikandersson.bbcrssapp.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, NewsListFragment.newInstance())
                .commit()
    }

}
