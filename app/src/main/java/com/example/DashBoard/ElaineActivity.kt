package com.example.DashBoard

import android.os.Bundle
import android.content.pm.ActivityInfo
import android.util.Log
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity

class ElaineActivity : AppCompatActivity() {
    private val TAG = "ElaineActivity"
    private var currentLayout = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            Log.d(TAG, "Setting orientation to landscape")
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

            Log.d(TAG, "Setting content view to Elaine's story layout (intro)")
            currentLayout = R.layout.elaine_story // Start with the intro layout
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
                    R.layout.elaine_story -> {
                        // Switch to the second layout (lvlstage1)
                        Log.d(TAG, "Navigating to level stage 1 layout")
                        currentLayout = R.layout.elaine_story2
                        setContentView(currentLayout)
                    }

                    R.layout.elaine_story2 -> {
                        // Switch to the third layout (lvlstage1_bottle)
                        Log.d(TAG, "Navigating to level stage 1 bottle layout")
                        currentLayout = R.layout.elaine_lvlstage1_q1
                        setContentView(currentLayout)
                    }

                    R.layout.elaine_lvlstage1_q1 -> {
                        Log.d(TAG, "Navigating to next stage or activity after bottle stage")
                         currentLayout = R.layout.elaine_lvlstage1_q1an
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
