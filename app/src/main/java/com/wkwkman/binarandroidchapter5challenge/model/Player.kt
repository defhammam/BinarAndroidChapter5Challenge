package com.wkwkman.binarandroidchapter5challenge.model

import com.wkwkman.binarandroidchapter5challenge.enum.PlayerChoice
import com.wkwkman.binarandroidchapter5challenge.enum.PlayerSide

data class Player(
    val playerSide: PlayerSide,
    var playerChoice: PlayerChoice
)
