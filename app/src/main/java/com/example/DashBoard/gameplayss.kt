package com.example.DashBoard

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class gameplayss : AppCompatActivity() {
    private val TAG = "gameplayss"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        try {
            Log.d(TAG, "Setting orientation to landscape")
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

            Log.d(TAG, "Setting content view to landscape layout")
            setContentView(R.layout.gameplayestetik)
        } catch (e: Exception) {
            Log.e(TAG, "Error in onCreate", e)
            finish() // Finish the activity if there's an error
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "gameplayss is being destroyed")
    }
}