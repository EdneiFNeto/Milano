package com.example.milano.utils

import android.content.Context
import android.media.MediaPlayer
import android.os.Handler
import com.example.milano.R

class MediaPlayerUtils {

    companion object{

        fun playSound(context: Context) {
            try {
                var mediaPlayer: MediaPlayer? = MediaPlayer.create(context, R.raw.bip2)
                mediaPlayer?.start() // no need to call prepare(); create() does that for you
                Handler().postDelayed({
                    mediaPlayer?.stop()
                },300)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}
