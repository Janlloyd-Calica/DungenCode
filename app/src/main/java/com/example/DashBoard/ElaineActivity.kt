package com.example.DashBoard

import android.os.Bundle
import android.content.pm.ActivityInfo
import android.util.Log
import android.view.KeyEvent
import android.view.MotionEvent
import android.widget.ImageView
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity

class ElaineActivity : AppCompatActivity() {
    private val TAG = "ElaineActivity"
    private var currentLayout = 0
    private var lives = 3 // Player starts with 3 lives

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
        if (event.action == MotionEvent.ACTION_UP) {
            Log.d(TAG, "Screen touched. Current layout: $currentLayout")
            when (currentLayout) {
                R.layout.elaine_story -> navigateToLayout(R.layout.elaine_story2)
                R.layout.elaine_story2 -> navigateToLayout(R.layout.elaine_lvlstage1_q1)
                R.layout.elaine_lvlstage1_q1 -> navigateToLayout(R.layout.elaine_lvlstage1_q1an)
                R.layout.elaine_lvlstage1_q1correct -> navigateToLayout(R.layout.elaine_story3)
                R.layout.elaine_lvlstage1_q1fail -> retryQuestion(R.layout.elaine_lvlstage1_q1an)
                R.layout.elaine_story3 -> navigateToLayout(R.layout.elaine_lvlstage2_q2)
                R.layout.elaine_lvlstage2_q2 -> navigateToLayout(R.layout.elaine_lvlstage2_q2an)
                R.layout.elaine_lvlstage2_q2correct -> navigateToLayout(R.layout.elaine_story4)
                R.layout.elaine_lvlstage2_q2fail -> retryQuestion(R.layout.elaine_lvlstage2_q2an)
                else -> Log.d(TAG, "Unknown layout, no action taken")
            }
            return true
        }
    }

    private fun navigateToLayout(layout: Int) {
        currentLayout = layout
        setContentView(currentLayout)
        updateHearts()
    }

    private fun setupChoiceClickListeners() {
        val choice1 = findViewById<ImageView>(R.id.choice1)
        val choice2 = findViewById<ImageView>(R.id.choice2)
        val choice3 = findViewById<ImageView>(R.id.choice3)
        val choice4 = findViewById<ImageView>(R.id.choice4)

        choice1?.setOnClickListener { handleChoice(false) }
        choice2?.setOnClickListener { handleChoice(false) }
        choice3?.setOnClickListener { handleChoice(true) }
        choice4?.setOnClickListener { handleChoice(false) }
    }

    private fun handleChoice(isCorrect: Boolean) {
        Log.d(TAG, "handleChoice called with isCorrect = $isCorrect")

        if (isCorrect) {
            Log.d(TAG, "Correct choice selected")
            currentLayout = when (currentLayout) {
                R.layout.elaine_lvlstage1_q1an -> R.layout.elaine_lvlstage1_q1correct
                R.layout.elaine_lvlstage2_q2an -> R.layout.elaine_lvlstage2_q2correct
                else -> {
                    Log.d(TAG, "Unexpected layout for correct answer")
                    return
                }
            }
        } else {
            Log.d(TAG, "Incorrect choice selected")
            lives--
            currentLayout = if (lives > 0) {
                when (currentLayout) {
                    R.layout.elaine_lvlstage1_q1an -> R.layout.elaine_lvlstage1_q1fail
                    R.layout.elaine_lvlstage2_q2an -> R.layout.elaine_lvlstage2_q2fail
                    else -> {
                        Log.d(TAG, "Unexpected layout for incorrect answer")
                        return
                    }
                }
            } else {
                Log.d(TAG, "Game over")
                R.layout.game_over
            }
        }
        setContentView(currentLayout)
        updateHearts()
    }

    private fun updateHearts() {
        val heartsContainer = findViewById<ViewGroup>(R.id.heartsContainer) ?: return
        Log.d(TAG, "Updating hearts with lives remaining: $lives")

        for (i in 0 until heartsContainer.childCount) {
            val heart = heartsContainer.getChildAt(i) as? ImageView
            heart?.setImageResource(if (i < lives) R.drawable.hart_lives else R.drawable.hart_empty)
        }
    }
}
