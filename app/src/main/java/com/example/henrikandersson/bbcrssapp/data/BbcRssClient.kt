package com.example.henrikandersson.bbcrssapp.data

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import io.reactivex.Single
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

class BbcRssClient {
    companion object {
        private const val BASE_URL = "http://feeds.bbci.co.uk/news/video_and_audio/news_front_page/"
    }

    private var bbcRssService: BbcRssService

    init {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(OkHttpClient.Builder().build())
            .addConverterFactory(ScalarsConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
        bbcRssService = retrofit.create(BbcRssService::class.java)
    }

    fun getRssFeed(): Single<String> {
        return bbcRssService.getRssFeed()
    }
}