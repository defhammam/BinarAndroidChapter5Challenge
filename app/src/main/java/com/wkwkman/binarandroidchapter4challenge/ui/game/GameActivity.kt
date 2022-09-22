package com.wkwkman.binarandroidchapter4challenge.ui.game

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.wkwkman.binarandroidchapter4challenge.databinding.ActivityGameBinding
import com.wkwkman.binarandroidchapter4challenge.enum.GameResult
import com.wkwkman.binarandroidchapter4challenge.enum.GameState
import com.wkwkman.binarandroidchapter4challenge.manager.GameListener
import com.wkwkman.binarandroidchapter4challenge.manager.GameManager
import com.wkwkman.binarandroidchapter4challenge.manager.RoshamboGameManager
import com.wkwkman.binarandroidchapter4challenge.model.Player

class GameActivity : AppCompatActivity(), GameListener {
    private val binding: ActivityGameBinding by lazy {
        ActivityGameBinding.inflate(layoutInflater)
    }

    private val gameManager: GameManager by lazy {
        RoshamboGameManager(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        gameManager.launchGame()
        getButtonResponses()
        gameManager.playGame()
        supportActionBar?.hide()
    }

    private fun getButtonResponses() {
        binding.apply {
            ivPlayerRock.setOnClickListener {
                Log.d(TAG, "initButton: Player Rock was clicked")
                gameManager.playerChoseRock()
            }
            ivPlayerPaper.setOnClickListener {
                Log.d(TAG, "initButton: Player Paper was clicked")
                gameManager.playerChosePaper()
            }
            ivPlayerScissors.setOnClickListener {
                Log.d(TAG, "initButton: Player Scissors was clicked")
                gameManager.playerChoseScissors()
            }
            flReplayButton.setOnClickListener {
                Log.d(TAG, "initButton: Replay was clicked")
                gameManager.launchGame()
            }
        }
    }

    companion object {
        private val TAG = GameActivity::class.java.simpleName
    }

    override fun onPlayerStatusChanged(player: Player, iconDrawableRes: Int) {
        TODO("Not yet implemented")
    }

    override fun onGameStateChanged(gameState: GameState) {
        TODO("Not yet implemented")
    }

    override fun onGameFinished(gameState: GameState, gameResult: GameResult) {
        TODO("Not yet implemented")
    }
}

/*
References (styling):
https://svg2vector.com/
https://stackoverflow.com/questions/51073904/svg-not-correctly-converting-to-an-xml-drawable-with-a-high-viewport-in-android
https://material.io/design/color/the-color-system.html#tools-for-picking-colors
https://material.io/resources/color/#!/?view.left=1&view.right=0&primary.color=EF6C00

 */