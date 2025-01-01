package day14

import Coordinate
import Vector
import utils.asResource

fun main() {
//    val file = "day14/example.txt"
//    val height = 7
//    val width = 11
    val file = "day14/input.txt"
    val height = 103
    val width = 101

    val robots = file.asResource()
        .readText()
        .lines()
        .map {
            val (pPart, vPart) = it.split(" ")
            val (px, py) = pPart.substring(2).split(",").map { it.toInt() }
            val (vx, vy) = vPart.substring(2).split(",").map { it.toInt() }
            Robot(
                Coordinate(py, px),
                Vector(vy, vx),
            )
        }

    val positions = robots.map {
        val row = (it.position.row + 100 * it.velocity.rows) % height
        val col = (it.position.col + 100 * it.velocity.cols) % width
        val correctedRow = if(row < 0) row + height else row
        val correctedCol = if(col < 0) col + width else col
        Coordinate(correctedRow, correctedCol)
    }

    val result = listOf(
        Pair(0 until height / 2, 0 until width / 2),
        Pair(0 until height / 2, width / 2 + 1 until width),
        Pair(height / 2 + 1 until height, 0 until width / 2),
        Pair(height / 2 + 1 until height, width / 2 + 1 until width)
    ).map { q ->
        val count = positions.count { p ->
            p.row in q.first &&
                    p.col in q.second
        }
        count.toLong()
    }.reduce {a, b -> a * b}


    println(result)
}
