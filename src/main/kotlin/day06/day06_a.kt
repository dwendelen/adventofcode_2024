package day06

import utils.asResource

fun main() {
//    val file = "day06/example.txt"
    val file = "day06/input.txt"
    var pos: Pair<Int, Int> = -1 to -1
    val grid = file.asResource()
        .readText()
        .lines()
        .mapIndexed { y, line ->
            line.mapIndexed { x, char ->
                    when(char) {
                    '#' -> true
                    '.' -> false
                    '^' -> {
                        pos = Pair(y, x)
                        false
                    }
                    else -> throw RuntimeException("Unexpected char $line")
                }
            }
        }

    var velocity = Pair(-1, 0)
    val visited = mutableSetOf<Pair<Int, Int>>()
    while(pos.first in grid.indices && pos.second in grid[0].indices) {
        visited.add(pos)
        val next = Pair(pos.first + velocity.first, pos.second + velocity.second)
        if(grid.getOrNull(next.first)?.getOrNull(next.second) == true) {
            velocity = Pair(velocity.second, -velocity.first)
        } else {
            pos = next
        }
    }


    println(visited.size)
}
