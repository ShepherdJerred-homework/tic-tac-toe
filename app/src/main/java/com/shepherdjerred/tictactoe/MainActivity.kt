/*
Extra credit done for changing button colors and AI delay
 */

package com.shepherdjerred.tictactoe

import android.os.Bundle
import android.os.Handler
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.GridLayout
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    var ticTacToeGame = TicTacToeGame()
    lateinit var grid: GridLayout
    val buttonCellMap: HashMap<Button, Cell> = HashMap()
    var currentPlayersTurn: Player = Player.PLAYER_ONE

    // Constants
    companion object {
        val PLAYER_ONE_SYMBOL = "X"
        val PLAYER_TWO_SYMBOL = "O"
        val NONE_SYMBOL = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        grid = findViewById(R.id.grid) as GridLayout
        mapButtonToCells()
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        if (savedInstanceState != null) {
            ticTacToeGame.status = TicTacToeGame.Status.valueOf(savedInstanceState.getString("status"))
            currentPlayersTurn = Player.valueOf(savedInstanceState.getString("currentTurn"))
            var boardString = savedInstanceState.getString("boardString")
            parseBoardString(boardString)
            syncBoard()
            syncStatus()
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        var boardString = boardToString()
        outState?.putString("status", ticTacToeGame.status.toString())
        outState?.putString("boardString", boardString)
        outState?.putString("currentTurn", currentPlayersTurn.toString())
    }

    private fun parseBoardString(boardString: String) {
        Log.i("TICTACTOE", boardString)
        var charArray = boardString.toCharArray()
        var x = 0
        var y = 0
        for (c in charArray) {
            var player = when (c) {
                '1' -> Player.PLAYER_ONE
                '2' -> Player.PLAYER_TWO
                else -> Player.NONE
            }
            ticTacToeGame.gameBoard.board.put(Cell(x, y), player)
            if (x > 2) {
                x = 0
                y++
            }
            x++
        }
    }

    private fun boardToString(): String {
        var boardString = ""
        for (y in 2 downTo 0) {
            for (x in 2 downTo 0) {
                val player = ticTacToeGame.gameBoard.board[Cell(x, y)]
                var playerChar = when (player) {
                    Player.PLAYER_ONE -> "1"
                    Player.PLAYER_TWO -> "2"
                    else -> "N"
                }
                boardString += playerChar
            }
        }
        Log.i("TICTACTOE", boardString)
        return boardString
    }

    private fun mapButtonToCells() {
        buttonCellMap.put(findViewById(R.id.x0y0) as Button, Cell(0, 0))
        buttonCellMap.put(findViewById(R.id.x1y0) as Button, Cell(1, 0))
        buttonCellMap.put(findViewById(R.id.x2y0) as Button, Cell(2, 0))
        buttonCellMap.put(findViewById(R.id.x0y1) as Button, Cell(0, 1))
        buttonCellMap.put(findViewById(R.id.x1y1) as Button, Cell(1, 1))
        buttonCellMap.put(findViewById(R.id.x2y1) as Button, Cell(2, 1))
        buttonCellMap.put(findViewById(R.id.x0y2) as Button, Cell(0, 2))
        buttonCellMap.put(findViewById(R.id.x1y2) as Button, Cell(1, 2))
        buttonCellMap.put(findViewById(R.id.x2y2) as Button, Cell(2, 2))
    }

    fun resetGame(view: View) {
        ticTacToeGame.resetGame()
        currentPlayersTurn = Player.PLAYER_ONE
        syncBoard()
        syncStatus()
    }

    fun gameButtonClick(view: View) {
        if (ticTacToeGame.status == TicTacToeGame.Status.PLAYING && currentPlayersTurn == Player.PLAYER_ONE) {
            val button = view as Button
            val cell = buttonCellMap[button]
            if (cell != null) {
                if (ticTacToeGame.gameBoard.isCellOpen(cell)) {
                    ticTacToeGame.doMove(cell, Player.PLAYER_ONE)
                    syncBoard()
                    syncStatus()
                    currentPlayersTurn = Player.PLAYER_TWO
                    doComputerMove()
                }
            }
        }
    }

    private fun doComputerMove() {
        if (ticTacToeGame.status == TicTacToeGame.Status.PLAYING) {
            val handler = Handler()
            handler.postDelayed({
                ticTacToeGame.doAutomaticMove(Player.PLAYER_TWO)
                syncBoard()
                syncStatus()
                currentPlayersTurn = Player.PLAYER_ONE
            }, 1000)
        }
    }

    private fun syncStatus() {
        val message = when (ticTacToeGame.status) {
            TicTacToeGame.Status.PLAYING -> ""
            TicTacToeGame.Status.NO_WINNER -> getString(R.string.Tie)
            TicTacToeGame.Status.PLAYER_ONE_WINNER -> getString(R.string.PlayerOneWins)
            TicTacToeGame.Status.PLAYER_TWO_WINNER -> getString(R.string.PlayerTwoWins)
        }
        (findViewById(R.id.result) as TextView).text = message
    }

    private fun syncBoard() {
        for ((button, value) in buttonCellMap) {
            val cellOwner = ticTacToeGame.gameBoard.board[value]
            if (cellOwner != null) {
                setButtonTextForOwner(button, cellOwner)
            } else {
                setButtonTextForOwner(button, Player.NONE)
            }
        }
    }

    private fun setButtonTextForOwner(button: Button, player: Player) {
        val ownerSymbol = when (player) {
            Player.PLAYER_ONE -> PLAYER_ONE_SYMBOL
            Player.PLAYER_TWO -> PLAYER_TWO_SYMBOL
            Player.NONE -> NONE_SYMBOL
        }
        when (player) {
            Player.PLAYER_ONE -> {
                button.setTextColor(ContextCompat.getColor(button.context, R.color.playerOne))
            }
            Player.PLAYER_TWO -> {
                button.setTextColor(ContextCompat.getColor(button.context, R.color.playerTwo))
            }
        }
        button.text = ownerSymbol
    }
}
