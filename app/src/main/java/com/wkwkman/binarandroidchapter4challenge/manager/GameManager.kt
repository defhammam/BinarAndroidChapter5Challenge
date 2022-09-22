package com.wkwkman.binarandroidchapter4challenge.manager

import com.wkwkman.binarandroidchapter4challenge.R
import com.wkwkman.binarandroidchapter4challenge.enum.*
import com.wkwkman.binarandroidchapter4challenge.model.Player
import kotlin.random.Random

interface GameManager {
    fun cleanStartGame()
    fun startOrRestartGame()
}

interface GameListener {
    fun onPlayerStatusChanged(player: Player, iconDrawableRes: Int)
    fun onGameStateChanged(gameState: GameState)
    fun onGameFinished(gameState: GameState, gameResult: GameResult)
}

// https://teachinghistory.org/history-content/ask-a-historian/23932
class RoshamboGameManager(private val listener: GameListener): GameManager {
    private lateinit var player: Player
    private lateinit var bot: Player
    private lateinit var gameState: GameState

    /*
    private fun notifyPlayerDataChanged() {
        TODO("Not yet implemented")
    }
    */

    override fun cleanStartGame() {
        setGameState(GameState.IDLE)
        player = Player(PlayerSide.PLAYER_ONE, PlayerChoice.UNDECIDED)
        bot = Player(PlayerSide.PLAYER_TWO, PlayerChoice.UNDECIDED)
        setGameState(GameState.STARTED)
    }

    private fun setGameState(newGameState: GameState) {
        gameState = newGameState
        listener.onGameStateChanged(gameState)
    }

    private fun getPlayerChoiceByOrdinal(index: Int): PlayerChoice {
        return PlayerChoice.values()[index]
    }

    private fun getBotChoice(): PlayerChoice {
        val randomChoice = Random.nextInt(0, until = PlayerChoice.values().size)
        return getPlayerChoiceByOrdinal(randomChoice)
    }

    override fun startOrRestartGame() {
        if (gameState != GameState.FINISHED) { startGame() }
        else { cleanStartGame() }
    }

    private fun startGame() {
        bot.apply {
            playerChoice = getBotChoice()
        }
        findResult()
    }

    private fun findResult() {
        val playerChoiceIndex = PlayerChoice.values().indexOf(player.playerChoice)
        val botChoiceIndex = PlayerChoice.values().indexOf(bot.playerChoice)
        val finalResult = when {
            (playerChoiceIndex + 1) % 3 == botChoiceIndex -> GameResult.BOT_WINS
            playerChoiceIndex == botChoiceIndex -> GameResult.DRAW
            else -> GameResult.PLAYER_WINS
        }
        setGameState(GameState.FINISHED)
        listener.onGameFinished(gameState, finalResult)
    }

    private fun setPlayerChoice(newChoice: PlayerChoice = player.playerChoice) {
        player.apply { this.playerChoice = newChoice }
        listener.onPlayerStatusChanged(
            player,
            getDrawableByChoice(player.playerChoice)
        )
    }

    private fun setBotChoice(newChoice: PlayerChoice = bot.playerChoice) {
        bot.apply { this.playerChoice = newChoice }
        listener.onPlayerStatusChanged(
            bot,
            getDrawableByChoice(bot.playerChoice)
        )
    }

    private fun getDrawableByChoice(desiredChoice: PlayerChoice): Int {
        return when (desiredChoice) {
            PlayerChoice.ROCK -> R.drawable.ic_rock
            PlayerChoice.PAPER -> R.drawable.ic_paper
            PlayerChoice.SCISSORS -> R.drawable.ic_scissors
            else -> Random.nextInt(0, until = PlayerChoice.values().size)
        }
    }
}