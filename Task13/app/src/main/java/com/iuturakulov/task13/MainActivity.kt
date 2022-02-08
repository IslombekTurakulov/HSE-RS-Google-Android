package com.iuturakulov.task13

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.MediaController
import android.widget.VideoView

class MainActivity : AppCompatActivity() {
    private lateinit var player: VideoView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        player = findViewById(R.id.videoView2)
        val myVideoUri: Uri = Uri.parse("android.resource://" + packageName + "/" + R.raw.dungeon)
        player.setVideoURI(myVideoUri)
        val mediaController = MediaController(this)
        player.setMediaController(mediaController)
        mediaController.setMediaPlayer(player)
        val playButton: Button = findViewById(R.id.playButton)
        val stopButton: Button = findViewById(R.id.stopButton)
        playButton.setOnClickListener{ view -> play(view)}
        stopButton.setOnClickListener{ view -> stop(view)}
    }

    fun play(view: View?) {
        player.start()
    }

    fun stop(view: View?) {
        player.stopPlayback()
        player.resume()
    }
}