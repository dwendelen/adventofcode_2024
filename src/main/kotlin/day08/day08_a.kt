package day08

import Coordinate
import loadGrid
import utils.asResource

fun main() {
//    val file = "day08/example.txt"
    val file = "day08/input.txt"

    val lines = file.asResource().readText()
    val grid = loadGrid(lines) { it }
    val coordsByItem = grid.itemMap() - '.'

    val antinodes = mutableSetOf<Coordinate>()
    coordsByItem.values.forEach { coords ->
        coords.forEachIndexed { i1, c1 ->
            coords.forEachIndexed { i2, c2 ->
                if(i1 < i2) {
                    val vec = c1 - c2
                    val p1 = c1 + vec
                    val p2 = c2 + -vec
                    antinodes.add(p1)
                    antinodes.add(p2)
                }
            }
        }
    }

    val result = antinodes
        .count { it in grid }

    println(result)
}
