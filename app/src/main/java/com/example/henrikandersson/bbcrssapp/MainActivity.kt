package com.example.henrikandersson.bbcrssapp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.example.henrikandersson.bbcrssapp.data.BbcRssClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val foo = BbcRssClient().getRssFeed()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { it ->
                Log.d("FOOO", "" + it.size)
            }
    }

}
