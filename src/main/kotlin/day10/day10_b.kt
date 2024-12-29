package day10

import Coordinate
import Grid
import loadGrid
import utils.asResource

fun main() {
//    val file = "day10/example.txt"
    val file = "day10/input.txt"

    val fileText = file.asResource().readText()
    val grid = loadGrid(fileText) { it.toString().toInt() }

    val initialStartingPoints = grid.itemMap()[0]!!


    val scores = initialStartingPoints.map {
        calc(grid, it, 0)
    }

    val result = scores.sum()

    println(result)
}

private fun calc(grid: Grid<Int>, coordinate: Coordinate, expected: Int): Int {
    val height = grid[coordinate]
    if(height != expected) {
        return 0
    }
    if(height == 9) {
        return 1
    }
    return 0 +
        calc(grid, coordinate.copy(row = coordinate.row + 1), expected + 1) +
        calc(grid, coordinate.copy(row = coordinate.row - 1), expected + 1) +
        calc(grid, coordinate.copy(col = coordinate.col + 1), expected + 1) +
        calc(grid, coordinate.copy(col = coordinate.col - 1), expected + 1)
}