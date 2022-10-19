package com.wkwkman.binarandroidchapter4challenge.model

import com.wkwkman.binarandroidchapter4challenge.enum.PlayerChoice
import com.wkwkman.binarandroidchapter4challenge.enum.PlayerSide

data class Player(
    val playerSide: PlayerSide,
    var playerChoice: PlayerChoice
)
