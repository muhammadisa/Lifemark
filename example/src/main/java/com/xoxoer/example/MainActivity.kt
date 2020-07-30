package com.xoxoer.example

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.xoxoer.lifemarklibrary.Lifemark
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val networkConnection = Lifemark(applicationContext)

        // Forever Checking example
        networkConnection.ObservableNetworkCondition()
            .observe(this@MainActivity, Observer { isConnected ->
                if (isConnected) {
                    disconnected_layout.visibility = View.GONE
                    connected_layout.visibility = View.VISIBLE
                } else {
                    connected_layout.visibility = View.GONE
                    disconnected_layout.visibility = View.VISIBLE
                }
            })

        if (networkConnection.isNetworkConnected()) {
            // do something when device connected to internet
            Log.d("INTERNET_CHECKER", "true")
        } else {
            // do something when device disconnected to internet
            Log.d("INTERNET_CHECKER", "false")
        }
    }

}