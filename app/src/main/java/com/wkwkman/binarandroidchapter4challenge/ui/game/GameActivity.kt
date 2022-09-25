package com.wkwkman.binarandroidchapter4challenge.ui.game

//import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import com.wkwkman.binarandroidchapter4challenge.R
import com.wkwkman.binarandroidchapter4challenge.databinding.ActivityGameBinding
import com.wkwkman.binarandroidchapter4challenge.enum.*
import com.wkwkman.binarandroidchapter4challenge.manager.GameListener
import com.wkwkman.binarandroidchapter4challenge.manager.GameManager
import com.wkwkman.binarandroidchapter4challenge.manager.RoshamboGameManager
import com.wkwkman.binarandroidchapter4challenge.model.Player

class GameActivity: AppCompatActivity(), GameListener {
    private val binding: ActivityGameBinding by lazy {
        ActivityGameBinding.inflate(layoutInflater)
    }

    private val gameManager: GameManager by lazy {
        RoshamboGameManager(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.hide()
        gameManager.launchGame()
        getButtonResponses()
        gameManager.playGame()
    }

    private fun getButtonResponses() {
        binding.apply {
            ivPlayerRock.setOnClickListener {
                Log.d(TAG, "getButtonResponses: Player Rock was clicked")
                gameManager.playerChoseRock()
            }
            ivPlayerPaper.setOnClickListener {
                Log.d(TAG, "getButtonResponses: Player Paper was clicked")
                gameManager.playerChosePaper()
            }
            ivPlayerScissors.setOnClickListener {
                Log.d(TAG, "getButtonResponses: Player Scissors was clicked")
                gameManager.playerChoseScissors()
            }
            flReplayButton.setOnClickListener {
                Log.d(TAG, "getButtonResponses: Replay was clicked")
                gameManager.launchGame()
            }
        }
    }

    companion object {
        private val TAG = GameActivity::class.java.simpleName
    }

    //@SuppressLint("ResourceAsColor")
    override fun onPlayerChoiceSelected(player: Player) {
        val ivChoiceRock: ImageView?
        val ivChoicePaper: ImageView?
        val ivChoiceScissors: ImageView?

        if (player.playerSide == PlayerSide.PLAYER_ONE) {
            ivChoiceRock = binding.ivPlayerRock
            ivChoicePaper = binding.ivPlayerPaper
            ivChoiceScissors = binding.ivPlayerScissors
        } else {
            ivChoiceRock = binding.ivBotRock
            ivChoicePaper = binding.ivBotPaper
            ivChoiceScissors = binding.ivBotScissors
        }

        when (player.playerChoice) {
            // Reference [6]
            // Background color of the selected choice will not be transparent
            PlayerChoice.ROCK ->
                ivChoiceRock.setBackgroundResource(R.color.cyan_A400)
            PlayerChoice.PAPER ->
                ivChoicePaper.setBackgroundResource(R.color.cyan_A400)
            PlayerChoice.SCISSORS ->
                ivChoiceScissors.setBackgroundResource(R.color.cyan_A400)
            else -> {}
        }
    }

    override fun onGameLaunched() {
        binding.apply {
            tvVersus.visibility = View.VISIBLE
            cvResultDraw.visibility = View.INVISIBLE
            llResultWin.visibility = View.INVISIBLE
            // Reset all choices to be unselected (transparent)
            ivPlayerRock.setBackgroundResource(R.color.cyan_transparent)
            ivPlayerPaper.setBackgroundResource(R.color.cyan_transparent)
            ivPlayerScissors.setBackgroundResource(R.color.cyan_transparent)
            ivBotRock.setBackgroundResource(R.color.cyan_transparent)
            ivBotPaper.setBackgroundResource(R.color.cyan_transparent)
            ivBotScissors.setBackgroundResource(R.color.cyan_transparent)
            // Mitigation in case the string in "winner result" will be concatenated
            tvWinner.text = ""
        }
    }

    override fun onGameFinished(gameState: GameState, gameResult: GameResult) {
        binding.tvVersus.visibility = View.INVISIBLE
        when (gameResult) {
            GameResult.DRAW -> {
                binding.apply {
                    cvResultDraw.visibility = View.VISIBLE
                    llResultWin.visibility = View.INVISIBLE
                }
            }
            else -> {
                binding.apply {
                    cvResultDraw.visibility = View.INVISIBLE
                    llResultWin.visibility = View.VISIBLE
                    // Reference [7]
                    tvWinner.text = when (gameResult) {
                        GameResult.PLAYER_WINS -> getString(R.string.player_one)
                        else -> getString(R.string.player_cpu)
                    }
                }
            }
        }
    }
}

/*
References:
[1] https://svg2vector.com/
[2] https://stackoverflow.com/questions/51073904/svg-not-correctly-converting-to-an-xml-drawable-with-a-high-viewport-in-android
[3] https://material.io/design/color/the-color-system.html#tools-for-picking-colors
[4] https://material.io/resources/color/#!/?view.left=1&view.right=0&primary.color=EF6C00
[5] https://stackoverflow.com/questions/2459916/how-to-make-an-imageview-with-rounded-corners
[6] https://stackoverflow.com/questions/8090459/android-dynamically-change-textview-background-color
[7] https://stackoverflow.com/questions/44096838/kotlin-how-to-get-and-set-a-text-to-textview-in-android-using-kotlin

 */