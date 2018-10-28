package com.example.henrikandersson.bbcrssapp.ui

import android.content.Context
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.henrikandersson.bbcrssapp.R
import com.example.henrikandersson.bbcrssapp.data.BbcRssClient
import com.example.henrikandersson.bbcrssapp.datamodel.NewsItemDataModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_news_list.*

class NewsListFragment : Fragment() {
    private lateinit var layoutManager: LinearLayoutManager
    private var adapter: NewsListAdapter = NewsListAdapter()
    private var getRssFeedDisposable: Disposable? = null

    companion object {
        private const val TAG = "NewsListFragment"
        fun newInstance(): NewsListFragment {
            return NewsListFragment()
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        layoutManager = LinearLayoutManager(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_news_list, container, false)
    }

    override fun onStart() {
        super.onStart()
        initNewsList()
        populateNewsList()
    }


    private fun initNewsList() {
        news_list.layoutManager = layoutManager
        news_list.adapter = adapter
    }

    private fun populateNewsList() {
        getRssFeedDisposable?.dispose()
        getRssFeedDisposable = BbcRssClient()
                .getRssFeed()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError {
                    Log.d(TAG, "Couldn't fetch RSS feed, message: ${it.message}")
                    displayInfoMessage(R.string.news_list_fragment_error_message)
                }
                .subscribe { it ->
                    if (it.size > 0) {
                        adapter.news = convertRangeToDisplayableNewsItems(it)
                    } else {
                        displayInfoMessage(R.string.news_list_fragment_no_result)
                    }
                }
    }

    private fun displayInfoMessage(@StringRes message: Int) {
        news_list.visibility = View.GONE
        info_message.visibility = View.VISIBLE
        info_message.setText(message)
    }

    override fun onStop() {
        super.onStop()
        getRssFeedDisposable?.dispose()
    }

    private fun convertToDisplayableNewsItem(dataModel: NewsItemDataModel): DisplayableNewsItem {
        val convertedItem = DisplayableNewsItem()
        convertedItem.name = dataModel.title
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