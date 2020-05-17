package com.example.storytime


import androidx.appcompat.app.AppCompatActivity
import android.content.Context
import android.os.Bundle
import android.widget.ListView
import android.widget.Toast
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.dashboard_story.*


class DashboardActivity() : AppCompatActivity() {
    lateinit var storyList: MutableList<StoryBacklog>
    lateinit var listView: ListView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dashboard_story)
        storyList = mutableListOf()
        listView = findViewById(R.id.storyList)
        getData()
    }

    private fun getData(){
        val database = FirebaseDatabase.getInstance()

        val myRef: DatabaseReference = database.getReference("story_backlog")
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
                    Toast.makeText(
                        applicationContext,
                        "You are in dashboard",
                        Toast.LENGTH_LONG
                    ).show()
                    val adapter = DashboardAdapter(applicationContext,R.layout.stories,storyList)
                    listView.adapter = adapter
                }
            }

        })


    }
}
