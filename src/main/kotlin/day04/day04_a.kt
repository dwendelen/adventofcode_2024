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
            if(test(grid, y, x, 0, 1)) {
                acc++
            }
            if(test(grid, y, x, 0, -1)) {
                acc++
            }
            if(test(grid, y, x, 1, 0)) {
                acc++
            }
            if(test(grid, y, x, -1, 0)) {
                acc++
            }
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
    val letters = listOf('X', 'M', 'A', 'S')
    for ((i, c) in letters.withIndex()) {
        val yy = y + i * dy
        val xx = x + i * dx
        if(grid.getOrNull(yy)?.getOrNull(xx) != c) {
            return false
        }
    }
    return true
}