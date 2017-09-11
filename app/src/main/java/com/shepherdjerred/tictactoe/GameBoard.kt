package com.shepherdjerred.tictactoe

class GameBoard(rows: Int, columns: Int) {

    private var rows = rows
    private var columns = columns

    val board: HashMap<Coordinate, Player> = HashMap()

    fun setBoardSpace(coordinate: Coordinate, player: Player) {
        if (isValidCoordinate(coordinate)) {
            board.put(coordinate, player)
        } else {
            throw IllegalArgumentException()
        }
    }

    fun isValidCoordinate(coordinate: Coordinate): Boolean {
        return !(coordinate.x > rows - 1 || coordinate.y > columns - 1)
    }

    fun hasRemainingMoves(): Boolean {
        for (x in 0..rows) {
            for (y in 0..columns) {
                if (isCoordinateFree(Coordinate(x, y))) {
                    return true
                }
            }
        }
        return false
    }

    fun isCoordinateFree(coordinate: Coordinate): Boolean {
        return !board.containsKey(coordinate)
    }

    fun hasPlayerWon(): Player? {
        return null
    }

}
