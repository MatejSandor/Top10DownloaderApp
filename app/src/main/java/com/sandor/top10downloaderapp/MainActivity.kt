package com.sandor.top10downloaderapp

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log


class MainActivity : AppCompatActivity() {
    private val TAG1 = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG1,"onCreate: called")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    private inner class DownloadData : AsyncTask<String,Void,String>() {
        private val TAG = "DownloadData"

        override fun doInBackground(vararg params: String?): String {
            Log.d(TAG,"doInBackground: called")
            TODO("Not yet implemented")
        }

        override fun onPostExecute(result: String?) {
            Log.d(TAG,"onPostExecute: called")
            super.onPostExecute(result)
        }
    }
}
