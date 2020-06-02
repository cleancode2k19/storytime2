package com.example.storytime
import android.content.Intent
import android.media.MediaPlayer
import android.os.Handler
import android.widget.ImageButton
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ListView
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dashboard_story)
        logOut = findViewById(R.id.logout)
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
                    val adapter = DashboardAdapter(applicationContext,R.layout.stories,storyList)
                    listView.adapter = adapter
                }
            }

        })
        backwardBtn = findViewById(R.id.btnBackward)
        forwardBtn = findViewById(R.id.btnForward)
        playBtn = findViewById(R.id.btnPlay)
        pauseBtn = findViewById(R.id.btnPause)
        songName = findViewById(R.id.txtSongName)
        startTime = findViewById(R.id.txtStartTime)
        songTime = findViewById(R.id.txtSongTime)
        songName.text = "SpeakerBox - song2"
        mediaPlayer = MediaPlayer.create(this, R.raw.song2)
        seekBar = findViewById(R.id.seekBar)
        seekBar.isClickable = false
        pauseBtn.isEnabled = true
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
        btnPause.setOnClickListener {
            pauseBtn.setVisibility(View.GONE)
            playBtn.setVisibility(View.VISIBLE)
            mediaPlayer.pause()
            pauseBtn.isEnabled = false
            playBtn.isEnabled = true
            Toast.makeText(applicationContext, "Audio Paused", Toast.LENGTH_SHORT).show()
        }
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
