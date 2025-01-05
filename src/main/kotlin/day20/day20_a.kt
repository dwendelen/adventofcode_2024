package day20

import Coordinate
import day16.Empty
import day16.End
import day16.Start
import day16.Wall
import loadGrid
import neighbourVectors
import utils.asResource

fun main() {
//    val file = "day20/example.txt"
//    val threshold = 20
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

    val shortcuts = distance.entries.sumOf { start ->
        neighbourVectors.count { vec ->
            val end = start.key + vec + vec
            val endDistance = distance[end]
            if(endDistance != null) {
                val diff = endDistance - start.value - 2
                diff >= threshold
            } else {
                false
            }
        }
    }

    println(shortcuts)
}

sealed interface Cell
data object Wall : Cell
data object Start : Cell
data object End : Cell
data object Empty : Cell