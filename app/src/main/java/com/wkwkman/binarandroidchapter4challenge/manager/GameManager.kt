package com.wkwkman.binarandroidchapter4challenge.manager

import com.wkwkman.binarandroidchapter4challenge.enum.*
import com.wkwkman.binarandroidchapter4challenge.model.Player

interface GameManager {
    fun beginGame()
    fun startOrRestartGame()
}

interface GameListener {
    fun onPlayerStatusChanged(player: Player, iconDrawableRes: Int)
    fun onGameStateChanged(gameState: GameState)
    fun onGameFinished(gameState: GameState, theWinner: Player)
}