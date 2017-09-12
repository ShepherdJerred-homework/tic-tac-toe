package com.shepherdjerred.tictactoe

class GameBoard(rows: Int, columns: Int) {

    private var rows = rows
    private var columns = columns

    var board: HashMap<Cell, CellOwner> = HashMap()

    fun setBoardSpace(cell: Cell, cellOwner: CellOwner) {
        if (isValidCell(cell)) {
            board.put(cell, cellOwner)
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
