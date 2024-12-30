package day12

import Coordinate
import Grid
import loadGrid
import utils.asResource

fun main() {
//    val file = "day12/example1.txt"
//    val file = "day12/example2.txt"
//    val file = "day12/example3.txt"
    val file = "day12/input.txt"

    val fileText = file.asResource().readText()
    val grid = loadGrid(fileText) { it }

    val todo = grid.coordinates().toMutableSet()

    var acc = 0L
    while(todo.isNotEmpty()) {
        val startingPoint = todo.first()
        val char = grid[startingPoint]!!
        val region = mutableSetOf<Coordinate>()

        flood(startingPoint, grid, char, region)

        val perimeter = region.sumOf { coor ->
            coor.neighbours()
                .count { grid[it] != char }
        }

        acc += region.size * perimeter
        todo.removeAll(region)
    }

    println(acc)
}

private fun flood(coor: Coordinate, grid: Grid<Char>, char: Char, acc: MutableSet<Coordinate>) {
    if(grid[coor] == char && coor !in acc) {
        acc.add(coor)
        coor.neighbours().forEach {
            flood(it, grid, char, acc)
        }
    }
}