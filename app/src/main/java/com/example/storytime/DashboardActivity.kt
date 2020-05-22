package com.example.storytime


import androidx.appcompat.app.AppCompatActivity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import android.widget.Toast
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.dashboard_story.*
import java.util.*


class DashboardActivity() : AppCompatActivity() {
    lateinit var storyList: MutableList<StoryBacklog>
    lateinit var listView: ListView
    lateinit var myRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dashboard_story)
        storyList = mutableListOf()
        listView = findViewById(R.id.storyListUI)
        myRef = FirebaseDatabase.getInstance().getReference("story_backlog")
        //getData()

        myRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(p0: DataSnapshot) {

               if(p0!!.exists()){ //P0!! to address null value
                    for(item in p0.children){
                        val story = item.getValue(StoryBacklog::class.java) //.getvalue method only takes java class
                        storyList.add(story!!)
                    }
                    Log.d("test3", "Gulu")
                    val adapter = DashboardAdapter(applicationContext,R.layout.stories,storyList)
                    listView.adapter = adapter
                }
            }

        })
    }

}
