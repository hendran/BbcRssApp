package com.example.henrikandersson.bbcrssapp.data

import com.example.henrikandersson.bbcrssapp.datamodel.NewsItemDataModel
import com.example.henrikandersson.bbcrssapp.xml.XmlParser
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
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

    fun getRssFeed(): Single<ArrayList<NewsItemDataModel>> {
        return Single.fromObservable(
            bbcRssService.getRssFeed()
                .subscribeOn(Schedulers.computation())
                .map { rawXml ->
                    XmlParser().parse(rawXml)
                }.toObservable()
        )
    }
}