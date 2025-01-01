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

    var robots = file.asResource()
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

    var bestT = 0
    var bestL = 0
    var bestPositions = emptySet<Coordinate>()

    (0..width * height).forEach { t ->
        val positions = robots.map {
            val row = (it.position.row + t * it.velocity.rows).mod(height)
            val col = (it.position.col + t * it.velocity.cols).mod(width)
            Coordinate(row, col)
        }.toSet()
        val maxRobotsInRow = (0 until height)
            .maxOf { r ->
                var maxContinuousL = 0
                var l = 0
                (0 until width).forEach { c ->
                    if(Coordinate(r, c) in positions) {
                        l++
                    } else {
                        if(l != 0) {
                            if(l > maxContinuousL) {
                                maxContinuousL = l
                            }
                            l = 0
                        }
                    }
                }

                maxContinuousL
            }
        if(maxRobotsInRow > bestL) {
            bestT = t
            bestL = maxRobotsInRow
            bestPositions = positions
        }
    }


    val stringBuilder = StringBuilder()
    repeat(height) {
        repeat(width) {
            stringBuilder.append(' ')
        }
        stringBuilder.append('\n')
    }

    bestPositions.forEach {
        val idx = it.row * (width + 1) + it.col
        stringBuilder[idx] = '#'
    }

    println("---------------------------------")
    println(bestT)
    println(stringBuilder.toString())
    println("---------------------------------")
    println()
    println()
}

