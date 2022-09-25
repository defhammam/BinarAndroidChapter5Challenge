package com.wkwkman.binarandroidchapter4challenge.manager

import com.wkwkman.binarandroidchapter4challenge.enum.*
import com.wkwkman.binarandroidchapter4challenge.model.Player
import kotlin.properties.Delegates
import kotlin.random.Random

interface GameManager {
    fun launchGame()
    fun playGame()
    fun playerChoseRock()
    fun playerChosePaper()
    fun playerChoseScissors()
}

interface GameListener {
    fun onPlayerChoiceSelected(player: Player)
    fun onGameLaunched()
    fun onGameFinished(gameState: GameState, gameResult: GameResult)
}

// Reference [8]
class RoshamboGameManager(private val listener: GameListener): GameManager {
    private lateinit var player: Player
    private lateinit var bot: Player
    private lateinit var gameState: GameState
    private var botChoiceIndex by Delegates.notNull<Int>()

    override fun launchGame() {
        player = Player(PlayerSide.PLAYER_ONE, PlayerChoice.UNDECIDED)
        bot = Player(PlayerSide.PLAYER_TWO, PlayerChoice.UNDECIDED)
        gameState = GameState.STARTED
        listener.onGameLaunched()
    }

    private fun getPlayerChoiceByOrdinal(index: Int): PlayerChoice {
        return PlayerChoice.values()[index]
    }

    private fun generateBotChoice() {
        botChoiceIndex = Random.nextInt(0, until = PlayerChoice.values().size)
        bot.playerChoice = getPlayerChoiceByOrdinal(botChoiceIndex)
        listener.onPlayerChoiceSelected(bot)
    }

    override fun playGame() {
        generateBotChoice()
        findResult()
    }

    private fun findResult() {
        val playerChoiceIndex = PlayerChoice.values().indexOf(player.playerChoice)
        // Reference [9]
        val finalResult = when {
            (playerChoiceIndex + 1) % 3 == botChoiceIndex -> GameResult.BOT_WINS
            playerChoiceIndex == botChoiceIndex -> GameResult.DRAW
            else -> GameResult.PLAYER_WINS
        }
        gameState = GameState.FINISHED
        listener.onGameFinished(gameState, finalResult)
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

    private fun setPlayerChoice(newChoice: PlayerChoice = player.playerChoice) {
        player.playerChoice = newChoice
        listener.onPlayerChoiceSelected(player)
    }
}

/*
* References:
* [8]   https://teachinghistory.org/history-content/ask-a-historian/23932
* [9]  https://learningpenguin.net/2020/02/06/a-simple-algorithm-for-calculating-the-result-of-rock-paper-scissors-game/
* */