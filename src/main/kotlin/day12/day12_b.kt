package day12

import Coordinate
import Grid
import loadGrid
import utils.asResource
import utils.frequencyMap

fun main() {
//    val file = "day12/example1.txt"
//    val file = "day12/example2.txt"
//    val file = "day12/example3.txt"
//    val file = "day12/example4.txt"
//    val file = "day12/example5.txt"
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

        // The number is sides is equal to the number of corners
        // Every cell has 4 corners.
        // The corners of the region have an odd number of cells for
        // which that corner is one of the cells corner

        val sides = region
            .flatMap {
                listOf(
                    it,
                    Coordinate(it.row, it.col + 1),
                    Coordinate(it.row + 1, it.col),
                    Coordinate(it.row + 1, it.col + 1)
                )
            }
            .toList()
            .frequencyMap()
            .entries
            .map {
                val cnt = it.value
                if(cnt == 4) {
                    0
                } else if(cnt == 1 || cnt == 3) {
                    1
                } else {
                    // Must be two
                    val BR = it.key
                    val TL = Coordinate(it.key.row - 1, it.key.col - 1)
                    val TR = Coordinate(it.key.row - 1, it.key.col)
                    val BL = Coordinate(it.key.row, it.key.col - 1)
                    if(
                        BR in region && TL in region ||
                        TR in region && BL in region
                    ) {
                        // Diagonal -> corner
                        2
                    } else {
                        0
                    }
                }
            }
            .sum()

        acc += region.size * sides
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