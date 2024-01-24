package com.example.media_player

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.ImageView
import android.widget.SeekBar
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    lateinit var mediaPlayer: MediaPlayer
    var totalTime:Int=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    mediaPlayer= MediaPlayer.create(this,R.raw.music)
        mediaPlayer.setVolume(1f,1f)
        totalTime=mediaPlayer.duration


        val btnplay=findViewById<ImageView>(R.id.play)
        val btnpause=findViewById<ImageView>(R.id.pause)
        val btnstop=findViewById<ImageView>(R.id.stop)
        val seekBarMusic=findViewById<SeekBar>(R.id.seekBar)
        val btnloop=findViewById<ImageView>(R.id.loop)

        btnloop.setOnClickListener {
            mediaPlayer.isLooping
        }

        btnplay.setOnClickListener{
            mediaPlayer.start()
        }
        btnpause.setOnClickListener {
            mediaPlayer.pause()
        }
       /* btnstop.setOnClickListener {
            mediaPlayer?.stop()
            mediaPlayer.reset()
            mediaPlayer.release()
        }*/
        btnstop.setOnClickListener {
            if (mediaPlayer.isPlaying) {
                mediaPlayer.stop()
                mediaPlayer.reset()
                mediaPlayer.release()
                mediaPlayer = MediaPlayer.create(this@MainActivity, R.raw.music)
                totalTime = mediaPlayer.duration
                seekBarMusic.max = totalTime
            }
        }

        //when the user changes the time stamp of music reflect the chamge
        seekBarMusic.max=totalTime
        seekBarMusic.setOnSeekBarChangeListener(object:SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if(fromUser){
                    mediaPlayer.seekTo(progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}

        })

        //chnage the seekbar position based on the music
        val handler=Handler()
        handler.postDelayed(object :Runnable{
            override fun run() {
                try{
                seekBarMusic.progress=mediaPlayer.currentPosition
                handler.postDelayed(this,1000)
            }catch (exception:Exception){
            seekBarMusic.progress=0
            }
            }

        },0)

    }
}