package com.example.DashBoard

import android.os.Bundle
import android.content.pm.ActivityInfo
import android.util.Log
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity

class SalvyActivity : AppCompatActivity() {
    private val TAG = "SalvyActivity"
    private var currentLayout = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            Log.d(TAG, "Setting orientation to landscape")
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

            Log.d(TAG, "Setting content view to Elaine's story layout (intro)")
            currentLayout = R.layout.salvy_story // Start with the intro layout
            setContentView(currentLayout)
        } catch (e: Exception) {
            Log.e(TAG, "Error in onCreate", e)
            finish()
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_UP -> {
                Log.d(TAG, "Screen touched")

                when (currentLayout) {
                    R.layout.salvy_story -> {
                        // Switch to the second layout (salvy_story2)
                        Log.d(TAG, "Navigating to level stage 1 layout")
                        currentLayout = R.layout.salvy_story2
                        setContentView(currentLayout)
                    }

                    R.layout.salvy_story2 -> {
                        // Switch to the third layout (salvy_story3)
                        Log.d(TAG, "Navigating to level stage 1 bottle layout")
                        currentLayout = R.layout.salvy_story3
                        setContentView(currentLayout)
                    }

                    R.layout.salvy_story3 -> {
                        // Switch to the third layout (salvy_story4)
                        Log.d(TAG, "Navigating to next stage or activity after bottle stage")
                        currentLayout = R.layout.salvy_story4
                        setContentView(currentLayout)
                    }

                    R.layout.salvy_story4 -> {
                        // Switch to the third layout (salvy_story5)
                        Log.d(TAG, "Navigating to next stage or activity after bottle stage")
                        currentLayout = R.layout.salvy_story5
                        setContentView(currentLayout)
                    }

                    else -> {
                        Log.d(TAG, "Unknown layout, no action taken")
                    }
                }
                return true
            }
        }
        return super.onTouchEvent(event)
    }
}