package day04

import utils.asResource

fun main() {
//    val file = "day04/example.txt"
    val file = "day04/input.txt"

    val grid = file.asResource()
        .readText()
        .lines()
        .map { it.toList() }

    var acc = 0
    for (y in grid.indices) {
        for (x in grid[y].indices) {
            if(test(grid, y, x, 1, 1)) {
                acc++
            }
            if(test(grid, y, x, -1, -1)) {
                acc++
            }
            if(test(grid, y, x, 1, -1)) {
                acc++
            }
            if(test(grid, y, x, -1, 1)) {
                acc++
            }
        }
    }

    println(acc)
}

private fun test(grid: List<List<Char>>, y: Int, x: Int, dy: Int, dx: Int): Boolean {
    val letters = listOf('M', 'A', 'S')
    for ((i, c) in letters.withIndex()) {
        val yy = y + (i - 1) * dy
        val xx = x + (i - 1) * dx
        if(grid.getOrNull(yy)?.getOrNull(xx) != c) {
            return false
        }
        // Rotated 90 degrees
        val dyy = dx
        val dxx = -dy
        val yyy = y + (i - 1) * dyy
        val xxx = x + (i - 1) * dxx
        if(grid.getOrNull(yyy)?.getOrNull(xxx) != c) {
            return false
        }
    }
    return true
}