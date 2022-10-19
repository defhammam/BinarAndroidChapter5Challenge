package com.wkwkman.binarandroidchapter5challenge.ui.game

//import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.wkwkman.binarandroidchapter5challenge.R
import com.wkwkman.binarandroidchapter5challenge.databinding.ActivityGameBinding
import com.wkwkman.binarandroidchapter5challenge.enum.*
import com.wkwkman.binarandroidchapter5challenge.manager.*
import com.wkwkman.binarandroidchapter5challenge.model.Player
import com.wkwkman.binarandroidchapter5challenge.ui.resultdialog.OnMenuSelectedListener
import com.wkwkman.binarandroidchapter5challenge.ui.resultdialog.ResultMenuDialog

class GameActivity: AppCompatActivity(), GameListener {
    private val binding: ActivityGameBinding by lazy {
        ActivityGameBinding.inflate(layoutInflater)
    }

    private val gameManager: GameManager by lazy {
        if (isUsingMultiplayerMode)
            MultiplayerRoshamboGameManager(this)
        else
            RoshamboGameManagerImpl(this)
    }
    
    private val isUsingMultiplayerMode: Boolean by lazy {
        intent.getBooleanExtra(EXTRAS_MULTIPLAYER_MODE, false)
    }

    private val playerOneName: String? by lazy {
        intent.getStringExtra(EXTRAS_NAME)
    }
    
    private val playerTwoName: String by lazy {
        getString(R.string.text_name_other_player)
    }
    
    private val botName: String by lazy {
        getString(R.string.text_name_bot)
    }
    
    private fun setPlayerNames() {
        binding.tvNameLeft.text = playerOneName
        binding.tvNameRight.text = if (isUsingMultiplayerMode) {
            playerTwoName
        } else {
            botName
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.hide()
        runGame()
    }

    private fun runGame() {
        setPlayerNames()
        gameManager.launchGame()
        getButtonResponses()
    }

    private fun getButtonResponses() {
        binding.apply {
            flCloseButton.setOnClickListener {
                if (playerOneName != null) {
                    onBackPressed()
                }
            }
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
            if (isUsingMultiplayerMode) {
                ivBotRock.setOnClickListener {
                    gameManager.setPlayerTwoChoice(PlayerChoice.ROCK)
                }
                ivBotPaper.setOnClickListener {
                    gameManager.setPlayerTwoChoice(PlayerChoice.PAPER)
                }
                ivBotScissors.setOnClickListener {
                    gameManager.setPlayerTwoChoice(PlayerChoice.SCISSORS)
                }
            }
            flRestartButton.setOnClickListener {
                Log.d(TAG, "getButtonResponses: Restart was clicked")
                runGame()
            }
        }
    }

    companion object {
        private val TAG = GameActivity::class.java.simpleName
        private const val EXTRAS_MULTIPLAYER_MODE = "EXTRAS_MULTIPLAYER_MODE"
        private const val EXTRAS_NAME = "EXTRAS_NAME"

        fun runActivity(
            context: Context,
            isUsingMultiplayerMode: Boolean,
            name: String?
        ) {
            context.startActivity(Intent(context, GameActivity::class.java).apply {
                putExtra(EXTRAS_MULTIPLAYER_MODE, isUsingMultiplayerMode)
                putExtra(EXTRAS_NAME, name)
            })
        }
    }

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
            PlayerChoice.ROCK -> {
                ivChoiceRock.setBackgroundResource(R.color.cyan_A400)
                ivChoicePaper.setBackgroundResource(R.color.custom_transparent)
                ivChoiceScissors.setBackgroundResource(R.color.custom_transparent)
            }
            PlayerChoice.PAPER -> {
                ivChoiceRock.setBackgroundResource(R.color.custom_transparent)
                ivChoicePaper.setBackgroundResource(R.color.cyan_A400)
                ivChoiceScissors.setBackgroundResource(R.color.custom_transparent)
            }
            PlayerChoice.SCISSORS -> {
                ivChoiceRock.setBackgroundResource(R.color.custom_transparent)
                ivChoicePaper.setBackgroundResource(R.color.custom_transparent)
                ivChoiceScissors.setBackgroundResource(R.color.cyan_A400)
            }
            else -> return
        }
    }

    override fun onGameLaunched() {
        binding.apply {
            // Reference [8]
            // Reset all choices to be unselected (transparent)
            ivPlayerRock.setBackgroundResource(R.color.custom_transparent)
            ivPlayerPaper.setBackgroundResource(R.color.custom_transparent)
            ivPlayerScissors.setBackgroundResource(R.color.custom_transparent)
            ivBotRock.setBackgroundResource(R.color.custom_transparent)
            ivBotPaper.setBackgroundResource(R.color.custom_transparent)
            ivBotScissors.setBackgroundResource(R.color.custom_transparent)
        }
    }
    
    private fun defineDialogData(gameResult: GameResult) {
        ResultMenuDialog.apply {
            when (gameResult) {
                GameResult.DRAW -> newInstance(endResult = gameResult.toString())
                GameResult.PLAYER_WINS -> newInstance(currentName = playerOneName)
                else -> {
                    if (isUsingMultiplayerMode)
                        newInstance(currentName = playerTwoName)
                    else
                        newInstance(currentName = botName)
                }
            }
        }
    }

    override fun onResultDisplayed(gameResult: GameResult) {
        defineDialogData(gameResult)
        
        ResultMenuDialog().apply {
            setOnMenuSelectedListener(object: OnMenuSelectedListener {
                override fun onRestartClicked(dialog: DialogFragment) {
                    dialog.dismiss()
                    runGame()
                }

                override fun onReturnToMenuClicked(dialog: DialogFragment) {
                    dialog.dismiss()
                    onBackPressed()
                    onBackPressed()
                }
            })
        }.show(supportFragmentManager, "result dialog")
    }

    override fun onGameStateChanged(gameState: GameState) {
        when (gameState) {
            GameState.STARTED ->
                setChoiceVisibility(isPlayerOneVisible = true, isPlayerTwoVisible = true)
            GameState.PLAYER_ONE_TURN -> {
                setChoiceVisibility(isPlayerOneVisible = true, isPlayerTwoVisible = false)
                Toast.makeText(this@GameActivity, getString(R.string.placeholder_turn, playerOneName), Toast.LENGTH_SHORT).show()
            }
            GameState.PLAYER_TWO_TURN -> {
                setChoiceVisibility(isPlayerOneVisible = false, isPlayerTwoVisible = true)
                if (isUsingMultiplayerMode)
                    Toast.makeText(this@GameActivity, getString(R.string.placeholder_turn, playerTwoName), Toast.LENGTH_SHORT).show()
            }
            GameState.FINISHED ->
                setChoiceVisibility(isPlayerOneVisible = true, isPlayerTwoVisible = true)
        }
    }
    
    private fun setChoiceVisibility(isPlayerOneVisible: Boolean, isPlayerTwoVisible: Boolean) {
        if (isPlayerOneVisible)
            binding.llPlayer.visibility = View.VISIBLE
        else
            binding.llPlayer.visibility = View.INVISIBLE

        if (isPlayerTwoVisible)
            binding.llBot.visibility = View.VISIBLE
        else
            binding.llBot.visibility = View.INVISIBLE
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
[8] https://stackoverflow.com/questions/1492554/set-transparent-background-of-an-imageview-on-android
[9] https://www.flaticon.com/free-icon/rock_6587391?term=rock%20paper%20scissors&page=1&position=13&page=1&position=13&related_id=6587391&origin=tag

 */