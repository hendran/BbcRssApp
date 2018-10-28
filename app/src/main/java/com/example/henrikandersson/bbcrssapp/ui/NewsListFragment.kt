package com.example.henrikandersson.bbcrssapp.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.henrikandersson.bbcrssapp.R
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_news_list.*

class NewsListFragment : Fragment() {
    private lateinit var layoutManager: LinearLayoutManager
    private var adapter: NewsListAdapter = NewsListAdapter()
    private var getRssFeedDisposable: Disposable? = null
    private lateinit var rssViewModel: RssViewModel

    companion object {
        const val TAG = "NewsListFragment"
        fun newInstance(): NewsListFragment {
            return NewsListFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        rssViewModel = ViewModelProviders.of(this).get(RssViewModel::class.java)
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
        rssViewModel.getData().observe(this, Observer { newsItems ->
            newsItems?.let {
                if (it.isNotEmpty()) {
                    adapter.updateData(it)
                } else {
                    displayInfoMessage(R.string.news_list_fragment_no_result)
                }
            }
        })
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

}