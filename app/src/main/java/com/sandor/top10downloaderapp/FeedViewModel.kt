package com.sandor.top10downloaderapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*

private const val TAG = "FeedViewModel"

val EMPTY_FEED_LIST: List<FeedEntry> = Collections.emptyList()

class FeedViewModel : ViewModel(), DownloadData.DownloaderCallBack {

    private var downloadData: DownloadData? = null
    private var feedCachedURL = "INVALIDATED"

    private val feed = MutableLiveData<List<FeedEntry>>()
    val feedEntries: LiveData<List<FeedEntry>>
        get() = feed

    init {
        feed.postValue(EMPTY_FEED_LIST)
    }

    fun downloadURL(feedURL: String) {
        if(feedURL != feedCachedURL) {
            downloadData = DownloadData(this)
            downloadData?.execute(feedURL)
            feedCachedURL = feedURL
        } else {
            Log.d(TAG, "downloadURL: URL not changed")
        }

    }

    fun invalidate() {
        feedCachedURL = "INVALIDATE"
    }

    override fun onDataAvailable(data: List<FeedEntry>) {
        Log.d(TAG, "onDataAvailable: data is $data")
        feed.value = data
    }

    override fun onCleared() {
        Log.d(TAG, "onCleared: called")
        downloadData?.cancel(true)
    }
}