package com.example.bitfitapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.material.textfield.TextInputEditText

private const val TAG = "DiaryActivity"

class DiaryActivity: AppCompatActivity() {
    private lateinit var titleInput: EditText
    private lateinit var dateInput: EditText
    private lateinit var entryInput: EditText
    private lateinit var moodInput: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.diary_input)

        titleInput = findViewById(R.id.titleInput)
        dateInput = findViewById(R.id.dateInput)
        moodInput = findViewById(R.id.moodInput)
        entryInput = findViewById(R.id.entryInput)

        val submitBtn = findViewById<Button>(R.id.submitBtn)

        submitBtn.setOnClickListener{
            val intent = Intent()
            intent.putExtra("title", titleInput.text.toString())
            intent.putExtra("date", dateInput.text.toString())
            intent.putExtra("entry", entryInput.text.toString())
            intent.putExtra("mood", moodInput.text.toString())
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }
}
