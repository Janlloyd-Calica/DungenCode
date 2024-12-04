package com.example.DashBoard

import android.content.Intent
import android.os.Bundle
import android.content.pm.ActivityInfo
import android.util.Log
import android.view.KeyEvent
import android.view.MotionEvent
import android.widget.EditText
import android.widget.ImageView
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import android.app.AlertDialog

class ElaineActivity : AppCompatActivity() {
    private val TAG = "ElaineActivity"
    private var currentLayout = 0
    private var lives = 3 // Player starts with 3 lives

    private lateinit var ifInput: EditText
    private lateinit var elifInput: EditText
    private lateinit var potionInput: EditText
    private lateinit var mapInput: EditText
    private lateinit var beesInput: EditText
    private lateinit var potionInput2: EditText
    private lateinit var mapInput2: EditText
    private lateinit var beesInput2: EditText

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

    override fun onResume() {
        super.onResume()
        // Check if the player wants to resume
        if (currentLayout != 0) {
            showResumeDialog()
        }
    }

    override fun onStop() {
        super.onStop()

        // Avoid navigating to LandscapeMode if the activity is explicitly finishing
        if (!isFinishing) {
            // Save progress before exiting
            saveProgress()

            // Navigate to LandscapeMode
            val intent = Intent(this, LandscapeMode::class.java)
            startActivity(intent)
        }
    }

    private fun showResumeDialog() {
        val currentActivity = this::class.java.simpleName
        if (currentActivity != "LandscapeModeActivity") {
            val builder = AlertDialog.Builder(this)
            builder.setMessage("Do you want to resume from where you left off?")
                .setCancelable(false)
                .setPositiveButton("Yes") { _, _ ->
                    // Load the saved progress
                    loadProgress()
                    navigateToLayout(currentLayout)
                }
                .setNegativeButton("No") { _, _ ->
                    // Restart the game, reset progress
                    restartGame()
                }

            val alert = builder.create()
            alert.show()
        }
    }

    private fun restartGame() {
        // Restart the game from the first layout
        currentLayout = R.layout.elaine_story
        lives = 3
        saveProgress() // Save the reset progress
        navigateToLayout(currentLayout)
    }

    private fun saveProgress() {
        val sharedPreferences = getSharedPreferences("ElaineProgress", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("currentLayout", currentLayout)
        editor.putInt("lives", lives)
        editor.apply()
        Log.d(TAG, "Progress saved: Layout=$currentLayout, Lives=$lives")
    }

    private fun loadProgress() {
        val sharedPreferences = getSharedPreferences("ElaineProgress", MODE_PRIVATE)
        currentLayout = sharedPreferences.getInt("currentLayout", R.layout.elaine_story)
        lives = sharedPreferences.getInt("lives", 3)
        Log.d(TAG, "Progress loaded: Layout=$currentLayout, Lives=$lives")
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
                R.layout.elaine_story3 -> navigateToLayout(R.layout.elaine_story4)
                R.layout.elaine_story4 -> navigateToLayout(R.layout.elaine_lvlstage2_q2)
                R.layout.elaine_lvlstage2_q2 -> navigateToLayout(R.layout.elaine_lvlstage2_q2map)
                R.layout.elaine_lvlstage2_q2map -> navigateToLayout(R.layout.elaine_lvlstage2_q2search)
                R.layout.elaine_lvlstage2_q2search -> navigateToLayout(R.layout.elaine_lvlstage2_q2clue)
                R.layout.elaine_lvlstage2_q2clue -> navigateToLayout(R.layout.elaine_lvlstage2_q2crack)
                R.layout.elaine_lvlstage2_q2crack -> navigateToLayout(R.layout.elaine_lvlstage2_q2an)
                R.layout.elaine_lvlstage2_q2correct -> navigateToLayout(R.layout.elaine_story5)
                R.layout.elaine_lvlstage2_q2fail -> retryQuestion(R.layout.elaine_lvlstage2_q2an)
                R.layout.elaine_story5 -> navigateToLayout(R.layout.elaine_lvlstage3_q3)
                R.layout.elaine_lvlstage3_q3 -> navigateToLayout(R.layout.elaine_lvlstage3_q3guard)
                R.layout.elaine_lvlstage3_q3guard -> navigateToLayout(R.layout.elaine_lvlstage3_q3guard1)
                R.layout.elaine_lvlstage3_q3guard1 -> navigateToLayout(R.layout.elaine_lvlstage3_q3guard2)
                R.layout.elaine_lvlstage3_q3guard2 -> navigateToLayout(R.layout.elaine_lvlstage3_q3guard3)
                R.layout.elaine_lvlstage3_q3guard3 -> navigateToLayout(R.layout.elaine_lvlstage3_q3guard4)
                R.layout.elaine_lvlstage3_q3guard4 -> navigateToLayout(R.layout.elaine_lvlstage3_q3guard5)
                R.layout.elaine_lvlstage3_q3guard5 -> navigateToLayout(R.layout.elaine_lvlstage3_q3guard6)
                R.layout.elaine_lvlstage3_q3guard6 -> navigateToLayout(R.layout.elaine_lvlstage3_q3an)
                R.layout.elaine_lvlstage3_q3correct -> navigateToLayout(R.layout.elaine_storyending)
                else -> Log.d(TAG, "Unknown layout, no action taken")
            }
            return true
        }
        return super.onTouchEvent(event)
    }

    private fun navigateToLayout(layout: Int) {
        currentLayout = layout
        setContentView(currentLayout)
        when (currentLayout) {
            R.layout.elaine_lvlstage1_q1an -> setupLevel1Inputs()
            R.layout.elaine_lvlstage2_q2an -> setupLevel2Inputs()
            R.layout.elaine_lvlstage3_q3an -> setupLevel3Inputs()
            R.layout.elaine_storyending -> setupEndingTouchAreas()
        }
        updateHearts()
        saveProgress()
    }

    private fun setupLevel1Inputs() {
        val choice1 = findViewById<ImageView>(R.id.choice1)
        val choice2 = findViewById<ImageView>(R.id.choice2)
        val choice3 = findViewById<ImageView>(R.id.choice3)
        val choice4 = findViewById<ImageView>(R.id.choice4)

        val choices = listOf(choice1, choice2, choice3, choice4)

        choices.forEachIndexed { index, choice ->
            choice.setOnClickListener {
                checkAnswer(index + 1)
            }
        }
    }

    private fun checkAnswer(choiceNumber: Int) {
        val isCorrect = choiceNumber == 3
        handleAnswer(
            isCorrect,
            R.layout.elaine_lvlstage1_q1correct,
            R.layout.elaine_lvlstage1_q1fail
        )
    }


    private fun setupLevel2Inputs() {
        ifInput = findViewById(R.id.ifInput)
        elifInput = findViewById(R.id.elifInput)

        ifInput.setOnEditorActionListener { _, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_NEXT || (event != null && event.keyCode == KeyEvent.KEYCODE_ENTER)) {
                elifInput.requestFocus()
                return@setOnEditorActionListener true
            }
            false
        }

        elifInput.setOnEditorActionListener { _, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE || (event != null && event.keyCode == KeyEvent.KEYCODE_ENTER)) {
                checkLevel2Answer()
                return@setOnEditorActionListener true
            }
            false
        }
    }

    private fun setupLevel3Inputs() {
        potionInput = findViewById(R.id.potionInput)
        mapInput = findViewById(R.id.mapInput)
        beesInput = findViewById(R.id.beesInput)
        potionInput2 = findViewById(R.id.potionInput2)
        mapInput2 = findViewById(R.id.mapInput2)
        beesInput2 = findViewById(R.id.beesInput2)

        val inputs = listOf(potionInput, mapInput, beesInput, potionInput2, mapInput2, beesInput2)

        for (i in 0 until inputs.size - 1) {
            inputs[i].setOnEditorActionListener { _, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_NEXT || (event != null && event.keyCode == KeyEvent.KEYCODE_ENTER)) {
                    inputs[i + 1].requestFocus()
                    return@setOnEditorActionListener true
                }
                false
            }
        }

        inputs.last().setOnEditorActionListener { _, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE || (event != null && event.keyCode == KeyEvent.KEYCODE_ENTER)) {
                checkLevel3Answer()
                return@setOnEditorActionListener true
            }
            false
        }
    }

    private fun checkLevel2Answer() {
        val ifScore = ifInput.text.toString()
        val elifScore = elifInput.text.toString()

        if (ifScore == "score" && elifScore == "score") {
            navigateToLayout(R.layout.elaine_lvlstage2_q2correct)
        } else {
            lives--
            if (lives > 0) {
                navigateToLayout(R.layout.elaine_lvlstage2_q2fail)
            } else {
                navigateToLayout(R.layout.game_over)
            }
        }
    }

    private fun checkLevel3Answer() {
        val correctAnswers = listOf("potion", "map", "bees", "potion", "map", "bees")
        val userAnswers = listOf(
            potionInput.text.toString(),
            mapInput.text.toString(),
            beesInput.text.toString(),
            potionInput2.text.toString(),
            mapInput2.text.toString(),
            beesInput2.text.toString()
        )

        val isCorrect = userAnswers == correctAnswers
        handleAnswer(isCorrect, R.layout.elaine_lvlstage3_q3correct, R.layout.elaine_story5)
    }

    private fun handleAnswer(isCorrect: Boolean, correctLayout: Int, incorrectLayout: Int) {
        Log.d(TAG, "handleAnswer called with isCorrect = $isCorrect")

        if (isCorrect) {
            Log.d(TAG, "Correct answer selected")
            navigateToLayout(correctLayout)
        } else {
            Log.d(TAG, "Incorrect answer selected")
            lives--
            if (lives > 0) {
                navigateToLayout(incorrectLayout)
            } else {
                Log.d(TAG, "Game over")
                navigateToLayout(R.layout.game_over)
            }
        }
    }

    private fun updateHearts() {
        val heartsContainer = findViewById<ViewGroup>(R.id.heartsContainer) ?: return
        Log.d(TAG, "Updating hearts with lives remaining: $lives")

        for (i in 0 until heartsContainer.childCount) {
            val heart = heartsContainer.getChildAt(i) as? ImageView
            heart?.setImageResource(if (i < lives) R.drawable.hart_lives else R.drawable.hart_empty)
        }
    }

    private fun retryQuestion(layout: Int) {
        navigateToLayout(layout)
    }

    private fun setupEndingTouchAreas() {
        val menuArea = findViewById<View>(R.id.menuButton)
        val playAgainArea = findViewById<View>(R.id.playAgainButton)

        menuArea.setOnClickListener {
            Log.d(TAG, "Menu area clicked, navigating to LandscapeMode")
            try {
                val intent = Intent(this, LandscapeMode::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                finish()
            } catch (e: Exception) {
                Log.e(TAG, "Error launching LandscapeMode", e)
            }
        }

        playAgainArea.setOnClickListener {
            Log.d(TAG, "Play Again area clicked, navigating to gameplayss")
            try {
                val intent = Intent(this, gameplayss::class.java)
                intent.putExtra("showCharacterSelect", true)
                startActivity(intent)
                // Finish ElaineActivity to prevent unintended behavior
                finish()
            } catch (e: Exception) {
                Log.e(TAG, "Error launching gameplayss", e)
            }
        }
    }
}

