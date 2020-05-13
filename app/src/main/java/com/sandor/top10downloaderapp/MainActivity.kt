package com.sandor.top10downloaderapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_main.*


private const val TAG = "MainActivity"
private const val STATE_URL = "feedURL"
private const val STATE_LIMIT = "feedLimit"

class MainActivity : AppCompatActivity() {

    private var feedURL: String = "http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topfreeapplications/limit=%d/xml"
    private var feedLimit = 10

    val feedViewModel: FeedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val feedAdapter = FeedAdapter(this,R.layout.list_record, EMPTY_FEED_LIST)
        xmlListView.adapter = feedAdapter

        if(savedInstanceState != null) {
            feedURL = savedInstanceState.getString(STATE_URL,feedURL)
            feedLimit = savedInstanceState.getInt(STATE_URL,feedLimit)
        }

        feedViewModel.feedEntries.observe(this,
            Observer<List<FeedEntry>> { feedEntries -> feedAdapter.setFeedList(feedEntries) })

        feedViewModel.downloadURL(feedURL.format(feedLimit))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.feeds_menu,menu)

        if(feedLimit == 10) {
            menu?.findItem(R.id.mnu10)?.isChecked = true
        } else {
            menu?.findItem(R.id.mnu25)?.isChecked = true
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.mnuFree ->
                feedURL = "http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topfreeapplications/limit=%d/xml"
            R.id.mnuPaid ->
                feedURL = "http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/toppaidapplications/limit=%d/xml"
            R.id.mnuSongs ->
                feedURL = "http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topsongs/limit=%d/xml"
            R.id.mnu10, R.id.mnu25 -> {
                if(!item.isChecked) {
                    item.isChecked = true
                    feedLimit = 35 - feedLimit
                    Log.d(TAG, "onOptionsItemSelected: ${item.title} setting feed limit to $feedLimit")
                } else {
                    Log.d(TAG, "onOptionsItemSelected: ${item.title} unchanged")
                }
            }
            R.id.mnuRefresh -> feedViewModel.invalidate()

            else
                -> return super.onOptionsItemSelected(item)
        }
        feedViewModel.downloadURL(feedURL.format(feedLimit))
        return true
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(STATE_URL,feedURL)
        outState.putInt(STATE_LIMIT,feedLimit)

    }

}
