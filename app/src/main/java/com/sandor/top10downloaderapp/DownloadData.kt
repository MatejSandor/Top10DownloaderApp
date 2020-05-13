package com.sandor.top10downloaderapp

import android.os.AsyncTask
import android.util.Log
import java.io.IOException
import java.net.MalformedURLException
import java.net.URL
import kotlin.properties.Delegates

private const val TAG = "DownloadData"

class DownloadData(private val callback: DownloaderCallBack) : AsyncTask<String, Void, String>() {

    interface DownloaderCallBack {
        fun onDataAvailable(data: List<FeedEntry>)
    }

    override fun doInBackground(vararg url: String): String {
        Log.d(TAG, "doInBackground: starts with ${url[0]}")
        val rssFeed = downloadXML(url[0])
        if (rssFeed.isEmpty()) {
            Log.e(TAG, "Error when downloading")
        }
        return rssFeed
    }

    override fun onPostExecute(result: String) {
        val parseApplications = ParseApplications()
        if (result.isNotEmpty()) {
            parseApplications.parse(result)
        }
        callback.onDataAvailable(parseApplications.applications)
    }

    private fun downloadXML(urlPath: String): String {
        try {
            return URL(urlPath).readText()
        } catch (e: MalformedURLException) {
            Log.d(TAG, "DownloadXML: MalformedURLException with ${e.message}")
        } catch (e: IOException) {
            Log.d(TAG, "DownloadXML: IOException with ${e.message}")
        } catch (e: SecurityException) {
            Log.d(TAG, "DownloadXML: SecurityException with ${e.message}")
            e.printStackTrace()
        }

        return ""
    }
}