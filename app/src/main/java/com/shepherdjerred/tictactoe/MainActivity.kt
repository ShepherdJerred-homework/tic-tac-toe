package com.shepherdjerred.tictactoe

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun resetBoard(view: View) {
        val parent = findViewById(R.id.grid)
        if (parent is ViewGroup) {
            for (i in 0..9) {
                val child = parent.getChildAt(i)
                if (child is Button) {
                    child.text = ""
                }
            }
        }
    }

    fun gameButtonClick(view: View) {
        if (view is Button) {
            if (view.text.equals("")) {
                view.text = "X"
                if (!haveAllButtonsBeenTaken(view)) {
                    clickRandomButton(view)
                }
            } else {
                // invalid move
            }
        }
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

    fun haveAllButtonsBeenTaken(view: View): Boolean {
        val parent = view.parent
        if (parent is ViewGroup) {
            for (i in 0..9) {
                val child = parent.getChildAt(i)
                if (child is Button) {
                    if (child.text.equals("")) {
                        return false
                    }
                }
            }
        }
        return true
    }
}
