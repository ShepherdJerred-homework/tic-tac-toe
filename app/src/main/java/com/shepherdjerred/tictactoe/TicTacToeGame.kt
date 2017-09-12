package com.shepherdjerred.tictactoe

import android.util.Log
import java.util.*

class TicTacToeGame {

    var status: Status = Status.PLAYING
    var gameBoard: GameBoard = GameBoard(3, 3)

    fun resetGame() {
        status = Status.PLAYING
        gameBoard = GameBoard(3, 3)
    }

    // https://stackoverflow.com/questions/1056316/algorithm-for-determining-tic-tac-toe-game-over
    fun checkWinner(cell: Cell, cellOwner: CellOwner) {
        for (row in 0..2) {
            if (gameBoard.board[Cell(row, cell.y)] != cellOwner) {
                break
            }
            if (row == 2) {
                if (cellOwner == CellOwner.PLAYER_ONE) {
                    status = Status.PLAYER_ONE_WINNER
                    return
                } else if (cellOwner == CellOwner.PLAYER_TWO) {
                    status = Status.PLAYER_TWO_WINNER
                    return
                }
            }
        }

        for (column in 0..2) {
            if (gameBoard.board[Cell(cell.x, column)] != cellOwner) {
                break
            }
            if (column == 2) {
                if (cellOwner == CellOwner.PLAYER_ONE) {
                    status = Status.PLAYER_ONE_WINNER
                    return
                } else if (cellOwner == CellOwner.PLAYER_TWO) {
                    status = Status.PLAYER_TWO_WINNER
                    return
                }
            }
        }

        if (cell.x == cell.y) {
            for (i in 0 until 3) {
                if (gameBoard.board[Cell(i, i)] != cellOwner) {
                    break
                }
                if (i == 2) {
                    if (cellOwner == CellOwner.PLAYER_ONE) {
                        status = Status.PLAYER_ONE_WINNER
                        return
                    } else if (cellOwner == CellOwner.PLAYER_TWO) {
                        status = Status.PLAYER_TWO_WINNER
                        return
                    }
                }
            }
        }

        if (cell.x + cell.y == 3 - 1) {
            for (i in 0 until 3) {
                if (gameBoard.board[Cell(i, 2 - i)] != cellOwner) {
                    break
                }
                if (i == 2) {
                    if (cellOwner == CellOwner.PLAYER_ONE) {
                        status = Status.PLAYER_ONE_WINNER
                        return
                    } else if (cellOwner == CellOwner.PLAYER_TWO) {
                        status = Status.PLAYER_TWO_WINNER
                        return
                    }
                }
            }
        }

        if (!gameBoard.anyOpenCells()) {
            status = Status.NO_WINNER
            return
        }
    }

    fun doMove(cell: Cell, cellOwner: CellOwner) {
        if (gameBoard.isCellOpen(cell)) {
            try {
                gameBoard.setBoardSpace(cell, cellOwner)
            } catch (e: IllegalArgumentException) {
                Log.e(this.javaClass.name, e.message)
            }
        }
        checkWinner(cell, cellOwner)
    }

    fun doAutomaticMove(cellOwner: CellOwner) {
        if (gameBoard.anyOpenCells()) {
            var moveDone = false
            do {
                val random = Random()
                val min = 0
                val max = 2
                val randomColumn = random.nextInt(max + 1 - min) + min
                val randomRow = random.nextInt(max + 1 - min) + min
                val cell = Cell(randomRow, randomColumn)
                if (gameBoard.isCellOpen(cell)) {
                    doMove(cell, cellOwner)
                    moveDone = true
                }
            } while (!moveDone)
        }
    }

    enum class Status {
        PLAYER_ONE_WINNER,
        PLAYER_TWO_WINNER,
        NO_WINNER,
        PLAYING
    }

}