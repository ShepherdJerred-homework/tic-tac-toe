package com.shepherdjerred.tictactoe

class GameBoard(private val rows: Int, private val columns: Int) {

    var board: HashMap<Cell, Player> = HashMap()

    fun setBoardSpace(cell: Cell, player: Player) {
        if (isValidCell(cell)) {
            board.put(cell, player)
        } else {
            throw IllegalArgumentException()
        }
    }

    private fun isValidCell(cell: Cell): Boolean {
        return !(cell.x > rows - 1 || cell.y > columns - 1)
    }

    fun isCellOpen(cell: Cell): Boolean {
        return !board.containsKey(cell)
    }

    fun anyOpenCells(): Boolean {
        for (x in 0 until rows) {
            for (y in 0 until columns) {
                if (isCellOpen(Cell(x, y))) {
                    return true
                }
            }
        }
        return false
    }

}
