package day06

import utils.asResource
import javax.swing.text.StyledEditorKit.BoldAction

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

    val possibleGrids = grid.indices
        .flatMap { y ->
            grid[y].indices
                .mapNotNull { x ->
                    if(grid[y][x] || pos == Pair(y, x)) {
                        null
                    } else {
                        grid.mapIndexed { yy, row ->
                            if (yy == y) {
                                row.mapIndexed { xx, cell ->
                                    if (xx == x) {
                                        true
                                    } else {
                                        cell
                                    }
                                }
                            } else {
                                row
                            }
                        }
                    }
                }
            }

    val result = possibleGrids
        .count { isLoop(it, pos) }

    println(result)
}

private fun isLoop(
    grid: List<List<Boolean>>,
    initialPos: Pair<Int, Int>
): Boolean {
    var pos = initialPos
    var velocity = Pair(-1, 0)
    val visited = mutableSetOf<Pair<Pair<Int, Int>, Pair<Int, Int>>>()

    val moved = true

    while(pos.first in grid.indices && pos.second in grid[0].indices) {
        if(moved) {
            val thing = Pair(pos, velocity)
            if(visited.contains(thing)) {
                return true
            } else {
                visited.add(thing)
            }
        }
        val next = Pair(pos.first + velocity.first, pos.second + velocity.second)
        if(grid.getOrNull(next.first)?.getOrNull(next.second) == true) {
            velocity = Pair(velocity.second, -velocity.first)
        } else {
            pos = next
        }
    }
    return false
}