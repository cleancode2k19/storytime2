package com.example.storytime

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemClickListener
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.dashboard_story.*
import java.util.concurrent.TimeUnit


class DashboardActivity() : AppCompatActivity() {
    lateinit var storyList: MutableList<StoryBacklog>
    lateinit var listView: ListView
    lateinit var myRef: DatabaseReference
    lateinit var forwardBtn: ImageButton
    lateinit var backwardBtn: ImageButton
    lateinit var pauseBtn: ImageButton
    lateinit var playBtn: ImageButton
    lateinit var mediaPlayer: MediaPlayer
    private lateinit var songName: TextView
    private lateinit var startTime: TextView
    private lateinit var songTime: TextView
    lateinit var seekBar: SeekBar
    private var handler: Handler = Handler()
    private var onTime: Int = 0
    private var playTime: Int = 0
    private var endTime: Int = 0
    private var forwardTime: Int = 5000
    private var backwardTime: Int = 5000
    lateinit var logOut: TextView
    private lateinit var rName: TextView
    var rowName="story1"

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //here I am tagging the layout to the activity
        setContentView(R.layout.dashboard_story)
        //Carry the login user name
        val prefs =
            getSharedPreferences("MyApp", Context.MODE_PRIVATE)
        var username = prefs.getString("username", "UNKNOWN")
        rName = findViewById(R.id.rName)
        rName.text = username
        logOut = findViewById(R.id.logout)
        //onclick of logout, signing out of the app
        logOut.setOnClickListener {
            Toast.makeText(
                applicationContext,
                "You logged out successfully",
                Toast.LENGTH_LONG
            ).show()
            val myIntent = Intent(baseContext, LoginActivity::class.java)
            startActivity(myIntent)
        }
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
                   //list view adapter to generate dynamic rows, with story title
                    val adapter = DashboardAdapter(applicationContext,R.layout.stories,storyList)
                    listView.adapter = adapter
                }
            }

        })

        listView.setOnItemClickListener(OnItemClickListener { arg0, arg1, position, arg3 ->
            rowName = "story"+(position+1).toString()
            playStory()
        })
        playStory()

    }
    private fun playStory(){
        backwardBtn = findViewById(R.id.btnBackward)
        forwardBtn = findViewById(R.id.btnForward)
        playBtn = findViewById(R.id.btnPlay)
        pauseBtn = findViewById(R.id.btnPause)
        songName = findViewById(R.id.txtSongName)
        startTime = findViewById(R.id.txtStartTime)
        songTime = findViewById(R.id.txtSongTime)
        songName.text = "SpeakerBox -$rowName"
        mediaPlayer = MediaPlayer.create(this, R.raw.story1)
        seekBar = findViewById(R.id.seekBar)
        seekBar.isClickable = false
        pauseBtn.isEnabled = true
        //play button click functionality
        playBtn.setOnClickListener {
            pauseBtn.setVisibility(View.VISIBLE)
            playBtn.setVisibility(View.GONE)
            Toast.makeText(this, "Playing Audio", Toast.LENGTH_LONG).show()
            mediaPlayer.start()
            endTime = mediaPlayer.duration
            playTime = mediaPlayer.currentPosition
            seekBar.max = endTime
            onTime = 1
            songTime.text = String.format(
                "%d min, %d sec",
                TimeUnit.MILLISECONDS.toMinutes(endTime.toLong()),
                TimeUnit.MILLISECONDS.toSeconds(endTime.toLong()) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(endTime.toLong()))
            )
            startTime.text = String.format(
                "%d min, %d sec",
                TimeUnit.MILLISECONDS.toMinutes(playTime.toLong()),
                TimeUnit.MILLISECONDS.toSeconds(playTime.toLong()) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(playTime.toLong()))
            )
            seekBar.progress = playTime
            handler.postDelayed(updateSongTime, 100)
            pauseBtn.isEnabled = true
            playBtn.isEnabled = false
        }
        //pause button click functionality
        btnPause.setOnClickListener {
            pauseBtn.setVisibility(View.GONE)
            playBtn.setVisibility(View.VISIBLE)
            mediaPlayer.pause()
            pauseBtn.isEnabled = false
            playBtn.isEnabled = true
            Toast.makeText(applicationContext, "Audio Paused", Toast.LENGTH_SHORT).show()
        }
        //Forward button click functionality
        btnForward.setOnClickListener {
            if ((playTime + forwardTime) <= endTime) {
                playTime += forwardTime
                mediaPlayer.seekTo(playTime)
            }
            else {
                Toast.makeText(
                    applicationContext,
                    "Cannot jump forward 5 seconds",
                    Toast.LENGTH_SHORT
                ).show()
            }
            if (!playBtn.isEnabled) {
                playBtn.isEnabled = true
            }
        }
        //backward button click functionality
        btnBackward.setOnClickListener {
            if ((playTime - backwardTime) > 0) {
                playTime -= backwardTime
                mediaPlayer.seekTo(playTime)
            }
            else {
                Toast.makeText(
                    applicationContext,
                    "Cannot jump backward 5 seconds",
                    Toast.LENGTH_SHORT
                ).show()
            }
            if (!playBtn.isEnabled) {
                playBtn.isEnabled = true
            }
        }
    }
    //story time update
    private val updateSongTime = object : Runnable {
        override fun run() {
            playTime = mediaPlayer.currentPosition
            startTime.text = String.format(
                "%d min, %d sec",
                TimeUnit.MILLISECONDS.toMinutes(playTime.toLong()),
                TimeUnit.MILLISECONDS.toSeconds(playTime.toLong()) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(playTime.toLong()))
            )
            seekBar.progress = playTime
            handler.postDelayed(this, 100)
        }

    }

}
