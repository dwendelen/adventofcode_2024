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
        val acc = mutableSetOf<Coordinate>()
        calc(grid, it, acc, 0)
        acc.size
    }

    val result = scores.sum()

    println(result)
}

private fun calc(grid: Grid<Int>, coordinate: Coordinate, acc: MutableSet<Coordinate>, expected: Int) {
    val height = grid[coordinate]
    if(height != expected) {
        return
    }
    if(height == 9) {
        acc.add(coordinate)
    }
    calc(grid, coordinate.copy(row = coordinate.row + 1), acc, expected + 1)
    calc(grid, coordinate.copy(row = coordinate.row - 1), acc, expected + 1)
    calc(grid, coordinate.copy(col = coordinate.col + 1), acc, expected + 1)
    calc(grid, coordinate.copy(col = coordinate.col - 1), acc, expected + 1)
}