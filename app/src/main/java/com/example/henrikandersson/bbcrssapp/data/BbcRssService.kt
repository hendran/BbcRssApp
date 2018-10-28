package com.example.henrikandersson.bbcrssapp.data

import io.reactivex.Single
import retrofit2.http.GET

interface BbcRssService {
    @GET("rss.xml?edition=uk")
    fun getRssFeed():Single<String>
}