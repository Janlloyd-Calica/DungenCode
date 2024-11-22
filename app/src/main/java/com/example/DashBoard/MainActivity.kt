package com.example.DashBoard
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Link the activity to the XML layout file
        setContentView(R.layout.activity_main)


        val myButton: Button = findViewById(R.id.PLAY_BUTTON)

        PLAY_BUTTON.setonCLickListener {
            val intent = Intent(this, MainActivity2::class.java)
            startActivity(intent)

        // Access the Button and TextView by their IDs

        //val myTextView: TextView = findViewById(R.id.my_textview)

        }
    }
}