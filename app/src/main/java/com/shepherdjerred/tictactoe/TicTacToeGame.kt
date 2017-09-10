package com.shepherdjerred.tictactoe

class TicTacToeGame {

    var gameBoard: GameBoard = GameBoard(3, 3)

    fun resetBoard() {
        gameBoard = GameBoard(3, 3)
    }

}