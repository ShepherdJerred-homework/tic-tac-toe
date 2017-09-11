package com.shepherdjerred.tictactoe

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.GridLayout
import java.util.*

class MainActivity : AppCompatActivity() {

    var ticTacToeGame = TicTacToeGame()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun syncBoard(view: View) {
        val viewGrid = findViewById(R.id.grid) as GridLayout
        for (entry in ticTacToeGame.gameBoard.board.entries) {

            val button = viewGrid.getChildAt(entry.key.x + entry.key.y) as Button
            button.text = entry.value.name
        }
    }

    fun resetBoard(view: View) {
        ticTacToeGame.resetBoard()
    }

    fun gameButtonClick(view: View) {
        ticTacToeGame.gameBoard.setBoardSpace()
    }

    fun clickRandomButton(view: View) {
        val parent = view.parent
        if (parent is ViewGroup) {
            var moveDone = false
            do {
                val random = Random()
                val min = 0
                val max = 8
                val randomNumber = random.nextInt(max + 1 - min) + min
                val child = parent.getChildAt(randomNumber)
                if (child is Button) {
                    if (child.text.equals("")) {
                        child.text = "O"
                        moveDone = true
                    } else {
                        continue
                    }
                }
            } while (!moveDone)
        }
    }
}
