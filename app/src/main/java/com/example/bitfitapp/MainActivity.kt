package com.example.bitfitapp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.bitfitapp.databinding.ActivityMainBinding
import android.widget.Button
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
//import com.codepath.bitfitapp.databinding.ActivityMainBinding
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import okhttp3.Headers
import org.json.JSONException

private const val TAG = "MainActivity/"

class MainActivity : AppCompatActivity() {
    private val diaryEntries = mutableListOf<DisplayDiary>()
    private lateinit var diaryRecyclerView: RecyclerView
    private lateinit var binding: ActivityMainBinding
    private lateinit var diaryItem: DisplayDiary
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val entryButton = findViewById<Button>(R.id.entryButton)
        diaryRecyclerView = findViewById(R.id.diaryRv)
        val diaryAdapter = DiaryAdapter(this, diaryEntries)
        diaryRecyclerView.adapter = diaryAdapter


        lifecycleScope.launch(IO) {
            (application as DiaryApplication).db.diaryDao().getAll().collect { databaseList ->
                databaseList.map { entity ->
                    DisplayDiary(
                        entity.title,
                        entity.date,
                        entity.mood,
                        entity.entry
                    )
                }.also { mappedList ->
                    diaryEntries.clear()
                    diaryEntries.addAll(mappedList)
                    diaryAdapter.notifyDataSetChanged()
                }
            }
        }

        diaryRecyclerView.layoutManager = LinearLayoutManager(this).also {
            val dividerItemDecoration = DividerItemDecoration(this, it.orientation)
            diaryRecyclerView.addItemDecoration(dividerItemDecoration)
        }

        var editActivityResultLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            // If the user comes back to this activity from EditActivity
            // with no error or cancellation
            if (result.resultCode == Activity.RESULT_OK) {
                val data = result.data
                // Get the data passed from EditActivity
                if (data != null) {
//                    val editedString = data.extras!!.getString("newString")
                    val titleItem = data.extras!!.getString("title")
                    val dateItem = data.extras!!.getString("date")
                    val entryItem = data.extras!!.getString("entry")
                    val moodItem = data.extras!!.getString("mood")
                    diaryItem = DisplayDiary(titleItem, dateItem, entryItem,
                        moodItem)
                    val diaryEntity = DiaryEntity(title = titleItem,date = dateItem,
                        entry = entryItem, mood = moodItem)
                    diaryEntries.add(diaryItem)
                    lifecycleScope.launch() {
                        (application as DiaryApplication).db.diaryDao().insert(diaryEntity)
                    }
                }

                diaryAdapter.notifyDataSetChanged()
            }
        }
        entryButton.setOnClickListener{
            val intent = Intent(this, DiaryActivity::class.java)
            editActivityResultLauncher.launch(intent)
        }

    }
}