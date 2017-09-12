package com.shepherdjerred.tictactoe

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.GridLayout
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    var ticTacToeGame = TicTacToeGame()
    lateinit var grid: GridLayout
    val buttonCellMap: HashMap<Button, Cell> = HashMap()

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
        syncBoard()
        syncStatus()
    }

    fun gameButtonClick(view: View) {
        if (ticTacToeGame.status == TicTacToeGame.Status.PLAYING) {
            val button = view as Button
            val cell = buttonCellMap[button]
            if (cell != null) {
                if (ticTacToeGame.gameBoard.isCellOpen(cell)) {
                    ticTacToeGame.doMove(cell, CellOwner.PLAYER_ONE)
                    syncBoard()
                    syncStatus()
                } else {
                    return
                }
            }
        }
        doComputerMove()
    }

    private fun doComputerMove() {
        if (ticTacToeGame.status == TicTacToeGame.Status.PLAYING) {
            ticTacToeGame.doAutomaticMove(CellOwner.PLAYER_TWO)
            syncBoard()
            syncStatus()
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
                setButtonTextForOwner(button, CellOwner.NONE)
            }
        }
    }

    private fun setButtonTextForOwner(button: Button, cellOwner: CellOwner) {
        val ownerSymbol = when (cellOwner) {
            CellOwner.PLAYER_ONE -> PLAYER_ONE_SYMBOL
            CellOwner.PLAYER_TWO -> PLAYER_TWO_SYMBOL
            CellOwner.NONE -> NONE_SYMBOL
        }
        when (cellOwner) {
            CellOwner.PLAYER_ONE -> {
                button.setTextColor(ContextCompat.getColor(button.context, R.color.playerOne))
            }
            CellOwner.PLAYER_TWO -> {
                button.setTextColor(ContextCompat.getColor(button.context, R.color.playerTwo))
            }
        }
        button.text = ownerSymbol
    }
}
