package day18

import Coordinate
import utils.asResource
import kotlin.collections.ArrayDeque

fun main() {
//    val file = "day18/example.txt"
//    val bounds = (0..6)
    val file = "day18/input.txt"
    val bounds = (0..70)

    val drops = file.asResource().readText()
        .lines()
        .map {
            val (row, col) = it.split(",").map(String::toInt)
            Coordinate(row, col)
        }

    val lastGoodN = binarySearch(drops.size - 1) { n ->
        blocked(bounds, drops, n)
    }

    // First bad one equals last good N
    val badBlock = drops[lastGoodN]
    println("${badBlock.row},${badBlock.col}")
}

fun blocked(bounds: IntRange, drops: List<Coordinate>, n: Int): Boolean {
    val blocked = drops
        .take(n)
        .toSet()

    val start = Coordinate(0, 0)

    val visited = mutableSetOf<Coordinate>()
    val todo = ArrayDeque<Coordinate>()
    todo.add(start)

    while(todo.isNotEmpty()) {
        val item = todo.removeFirst()
        if(item in visited) {
            continue
        } else {
            if(item.row == bounds.last && item.col == bounds.last) {
                return true
            }
            visited.add(item)
            item.neighbours()
                .filter { it.row in bounds && it.col in bounds && it !in blocked }
                .forEach { todo.add(it) }
        }
    }
    return false
}

fun binarySearch(max: Int, fn: (Int) -> Boolean): Int {
    var good = 0
    var bad = max
    while(bad - good > 1) {
        val middle = (good + bad) / 2
        val outcome = fn(middle)
        if(outcome) {
            println("$middle GOOD")
            good = middle
        } else {
            println("$middle BAD")
            bad = middle
        }
    }
    return good
}
