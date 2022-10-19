package com.wkwkman.binarandroidchapter5challenge.manager

import android.os.Handler
import android.os.Looper
import com.wkwkman.binarandroidchapter5challenge.enum.*
import com.wkwkman.binarandroidchapter5challenge.model.Player
import java.util.*
import kotlin.concurrent.schedule
import kotlin.properties.Delegates
import kotlin.random.Random

interface GameManager {
    fun launchGame()
    fun playGame()
    fun playerChoseRock()
    fun playerChosePaper()
    fun playerChoseScissors()
    fun setPlayerTwoChoice(newChoice: PlayerChoice)
}

interface GameListener {
    fun onPlayerChoiceSelected(player: Player)
    fun onGameLaunched()
    fun onResultDisplayed(gameResult: GameResult)
    fun onGameStateChanged(gameState: GameState)
}

// Reference [10]
open class RoshamboGameManagerImpl(val listener: GameListener?): GameManager {
    protected lateinit var player: Player
    protected lateinit var bot: Player
    protected lateinit var state: GameState
    private var botChoiceIndex by Delegates.notNull<Int>()

    override fun launchGame() {
        player = Player(PlayerSide.PLAYER_ONE, PlayerChoice.UNDECIDED)
        bot = Player(PlayerSide.PLAYER_TWO, PlayerChoice.UNDECIDED)
        state = GameState.STARTED
        listener?.onGameLaunched()
    }

    private fun getPlayerChoiceByOrdinal(index: Int): PlayerChoice {
        return PlayerChoice.values()[index]
    }

    private fun generateBotChoice() {
        botChoiceIndex = Random.nextInt(0, until = PlayerChoice.values().size - 1)
        bot.playerChoice = getPlayerChoiceByOrdinal(botChoiceIndex)
        listener?.onPlayerChoiceSelected(bot)
    }
    
    protected fun setGameState(newGameState: GameState) {
        state = newGameState
        listener?.onGameStateChanged(state)
    }

    override fun playGame() {
        generateBotChoice()
        findResult()
    }

    open fun findResult() {
        val playerChoiceIndex = PlayerChoice.values().indexOf(player.playerChoice)
        // Reference [11]
        val finalResult = when {
            (playerChoiceIndex + 1) % 3 == botChoiceIndex -> GameResult.BOT_WINS
            playerChoiceIndex == botChoiceIndex -> GameResult.DRAW
            else -> GameResult.PLAYER_WINS
        }
        setGameState(GameState.FINISHED)
        listener?.onResultDisplayed(finalResult)
    }

    override fun playerChoseRock() {
        setPlayerChoice(PlayerChoice.ROCK)
    }

    override fun playerChosePaper() {
        setPlayerChoice(PlayerChoice.PAPER)
    }

    override fun playerChoseScissors() {
        setPlayerChoice(PlayerChoice.SCISSORS)
    }

    open fun setPlayerChoice(newChoice: PlayerChoice = player.playerChoice) {
        player.playerChoice = newChoice
        listener?.onPlayerChoiceSelected(player)
        playGame()
    }

    override fun setPlayerTwoChoice(newChoice: PlayerChoice) {
        // Implemented in MultiplayerRoshamboGameManager
    }
}

class MultiplayerRoshamboGameManager(listener: GameListener?): RoshamboGameManagerImpl(listener) {
    override fun launchGame() {
        super.launchGame()
        setGameState(GameState.PLAYER_ONE_TURN)
    }

    override fun setPlayerChoice(newChoice: PlayerChoice) {
        player.playerChoice = newChoice
        listener?.onPlayerChoiceSelected(player)
        // Reference [12]
        Handler(Looper.getMainLooper()).postDelayed({
            setGameState(GameState.PLAYER_TWO_TURN)
        }, 100)
    }

    override fun setPlayerTwoChoice(newChoice: PlayerChoice) {
        super.setPlayerTwoChoice(newChoice)
        bot.playerChoice = newChoice
        listener?.onPlayerChoiceSelected(bot)
        this@MultiplayerRoshamboGameManager.findResult()
    }

    override fun findResult() {
        val playerOneChoiceIndex = PlayerChoice.values().indexOf(player.playerChoice)
        val playerTwoChoiceIndex = PlayerChoice.values().indexOf(bot.playerChoice)
        val finalResult = when {
            (playerOneChoiceIndex + 1) % 3 == playerTwoChoiceIndex -> GameResult.BOT_WINS
            playerOneChoiceIndex == playerTwoChoiceIndex -> GameResult.DRAW
            else -> GameResult.PLAYER_WINS
        }
        setGameState(GameState.FINISHED)
        listener?.onResultDisplayed(finalResult)
    }
    
    private fun decideTurn() {
        when (state) {
            GameState.PLAYER_ONE_TURN ->
                setGameState(GameState.PLAYER_TWO_TURN)
            GameState.PLAYER_TWO_TURN ->
                this.playGame()
            else -> return
        }
    }
}

/*
* References:
* [10]  https://teachinghistory.org/history-content/ask-a-historian/23932
* [11]  https://learningpenguin.net/2020/02/06/a-simple-algorithm-for-calculating-the-result-of-rock-paper-scissors-game/
* [12]  https://stackoverflow.com/questions/43348623/how-to-call-a-function-after-delay-in-kotlin
* */