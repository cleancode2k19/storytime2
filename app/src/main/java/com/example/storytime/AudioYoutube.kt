package com.example.storytime

import android.os.Bundle
import android.widget.Toast
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import kotlinx.android.synthetic.main.dashboard_story.*

class AudioYoutube : YouTubeBaseActivity(), YouTubePlayer.OnInitializedListener {

    override fun onInitializationSuccess(provider: YouTubePlayer.Provider?, player: YouTubePlayer?, wasRestored: Boolean) {
        Toast.makeText(
            applicationContext,
            "Youtube Api Initialization Success",
            Toast.LENGTH_LONG
        ).show()
        if (!wasRestored) {
            player?.cueVideo("wKJ9KzGQq0w");
        }
    }

    override fun onInitializationFailure(p0: YouTubePlayer.Provider?, p1: YouTubeInitializationResult?) {
        Toast.makeText(
            applicationContext,
            "Youtube Api Initialization Failure",
            Toast.LENGTH_LONG
        ).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dashboard_story)
        yt_pv.initialize("AIzaSyDv-gjtmZh1h9WKwQbp-aySccEFlDkxFuk", this)
    }
}