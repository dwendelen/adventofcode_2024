package day13

import Coordinate
import Grid
import loadGrid
import utils.asResource

fun main() {
//    val file = "day13/example.txt"
    val file = "day13/input.txt"

    val text = file.asResource().readText()
    val puzzles = text.split("\n\n")

    val price = puzzles.mapNotNull {
        val (aLine, bLine, pLine) = it.lines()
        val (ax, ay) = aLine.split(": ")[1].split(", ").map { it.substring(2).toLong() }
        val (bx, by) = bLine.split(": ")[1].split(", ").map { it.substring(2).toLong() }
        val (px, py) = pLine.split(": ")[1].split(", ").map { it.substring(2).toLong() + 10000000000000L }

        // | ax  bx  | px  |
        // | ay  by  | py  |

        // | ax  bx  | px  |
        // | 0   by2 | py2 |

        val by2 = ax * by - ay * bx
        val py2 = ax * py - ay * px

        if (py2 % by2 == 0L) {
            val b = py2 / by2
            val axa = px - b * bx
            if (axa % ax == 0L) {
                val a = axa / ax
                println("A $a, B $b")
                3 * a + b
            } else {
                null
            }
        } else {
            null
        }
    }.sum()

    println(price)
}
