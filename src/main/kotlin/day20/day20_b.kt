package day20

import Coordinate
import Vector
import day16.Empty
import day16.End
import day16.Start
import day16.Wall
import loadGrid
import neighbourVectors
import utils.asResource
import kotlin.math.abs

fun main() {
//    val file = "day20/example.txt"
//    val threshold = 50
    val file = "day20/input.txt"
    val threshold = 100

    val fileText = file.asResource().readText()

    val grid = loadGrid(fileText) {
        when (it) {
            '#' -> Wall
            'S' -> Start
            'E' -> End
            '.' -> Empty
            else -> throw IllegalArgumentException("Invalid grid character '$it'")
        }
    }

    val distance = mutableMapOf<Coordinate, Int>()
    var prev: Coordinate? = null
    var item: Coordinate? = grid.itemMap()[Start]!![0]
    var dist = 0
    while(item != null) {
        distance[item] = dist
        val nextItem = item.neighbours().firstOrNull {
            grid[it] !is Wall && it != prev
        }
        prev = item
        item = nextItem
        dist++
    }

    val cheatSize = 20
    val cheatGrid = (-cheatSize..cheatSize).flatMap { row ->
        (-cheatSize .. cheatSize).map { col ->
            Vector(row, col)
        }
    }.filter { abs(it.cols) + abs(it.rows) <= cheatSize }

    val shortcuts = distance.entries.sumOf { start ->
        cheatGrid.count { cheat ->
            val end = start.key + cheat
            val endDistance = distance[end]
            if(endDistance != null) {
                val diff = endDistance - start.value - (abs(cheat.cols) + abs(cheat.rows))
                diff >= threshold
            } else {
                false
            }
        }
    }

    println(shortcuts)
}

