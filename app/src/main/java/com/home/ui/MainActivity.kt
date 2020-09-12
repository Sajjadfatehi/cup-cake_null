package com.home.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.anull.R
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainActivity : AppCompatActivity() {
    private val splashTimeOut: Long = 3000 // 1 sec
    val TAG = "gooz"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val window = window
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        val x = getSharedPreferences("asa", MODE_PRIVATE)
        x.let {

        }
        runBlocking {
            delay(1000L)
            GlobalScope.launch {
                Log.d(TAG, "1")
            }
            delay(1000L)
            doSomething()
            Log.d(TAG, "x")
            GlobalScope.launch {
                delay(1000L)
                Log.d(TAG, "2")
            }
            Log.d(TAG, "3")
        }
        Log.d(TAG, "4")
        Log.d(TAG, "end")
        runBlocking {
            val job = launch {
                for (i in 1..9999999) {
                    Log.d(TAG, "PL Coding")
                    delay(10L)
                }
            }
            delay(1000L)
            job.cancel()
            Log.d(TAG, "Canceled")
        }
    }

    suspend fun doSomething() {
        Log.d(TAG, "5")
        delay(1000L)
    }
}