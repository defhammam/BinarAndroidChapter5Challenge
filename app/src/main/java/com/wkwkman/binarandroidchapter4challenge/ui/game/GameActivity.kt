package com.wkwkman.binarandroidchapter4challenge.ui.game

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.wkwkman.binarandroidchapter4challenge.databinding.ActivityGameBinding

class GameActivity : AppCompatActivity() {
    private val binding: ActivityGameBinding by lazy {
        ActivityGameBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initButton()
    }
    
    private fun initButton(): Unit {
        binding.apply {
            ivPlayerRock.setOnClickListener {
                Log.d(TAG, "initButton: Player Rock was clicked")
            }
            ivBotRock.setOnClickListener {
                Log.d(TAG, "initButton: Bot Rock was clicked")
            }
            ivPlayerPaper.setOnClickListener {
                Log.d(TAG, "initButton: Player Paper was clicked")
            }
            ivBotPaper.setOnClickListener {
                Log.d(TAG, "initButton: Bot Paper was clicked")
            }
            ivPlayerScissors.setOnClickListener {
                Log.d(TAG, "initButton: Player Scissors was clicked")
            }
            ivBotScissors.setOnClickListener {
                Log.d(TAG, "initButton: Bot Scissors was clicked")
            }
            flReplayButton.setOnClickListener {
                Log.d(TAG, "initButton: Replay was clicked")
            }
        }
    }

    companion object {
        private val TAG = GameActivity::class.java.simpleName
    }
}

/*
References (styling):
https://svg2vector.com/
https://stackoverflow.com/questions/51073904/svg-not-correctly-converting-to-an-xml-drawable-with-a-high-viewport-in-android
https://material.io/design/color/the-color-system.html#tools-for-picking-colors
https://material.io/resources/color/#!/?view.left=1&view.right=0&primary.color=EF6C00

 */