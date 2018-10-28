package com.example.henrikandersson.bbcrssapp.ui

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.henrikandersson.bbcrssapp.data.BbcRssClient
import com.example.henrikandersson.bbcrssapp.datamodel.NewsItemDataModel
import io.reactivex.disposables.Disposable

class RssViewModel : ViewModel() {
    private lateinit var data: MutableLiveData<List<DisplayableNewsItem>>
    private val client = BbcRssClient()
    private var getRssFeedDisposable: Disposable? = null

    fun getData(): MutableLiveData<List<DisplayableNewsItem>> {
        if (!::data.isInitialized) {
            loadData()
        }
        return data
    }

    private fun loadData() {
        data = MutableLiveData()
        getRssFeedDisposable?.dispose()
        getRssFeedDisposable = client.getRssFeed()
            .map { dataModels ->
                convertRangeToDisplayableNewsItems(dataModels)
            }.subscribe { displayableNewsItems ->
                data.postValue(displayableNewsItems)
            }
    }

    override fun onCleared() {
        super.onCleared()
        getRssFeedDisposable?.dispose()
    }

    private fun convertToDisplayableNewsItem(dataModel: NewsItemDataModel): DisplayableNewsItem {
        val convertedItem = DisplayableNewsItem()
        convertedItem.name = dataModel.title
        convertedItem.date = dataModel.date
        return convertedItem
    }

    private fun convertRangeToDisplayableNewsItems(dataModels: List<NewsItemDataModel>): ArrayList<DisplayableNewsItem> {
        val convertedItems: ArrayList<DisplayableNewsItem> = ArrayList()
        for (model in dataModels) {
            convertedItems.add(convertToDisplayableNewsItem(model))
        }
        return convertedItems
    }
}



