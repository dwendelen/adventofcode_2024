package day21

import Coordinate
import utils.asResource
import java.util.*

fun main() {
//    val file = "day21/example.txt"
    val file = "day21/input.txt"

    val codes = file.asResource().readText()
        .lines()

    val numericKeyPad = mapOf(
        'A' to Coordinate(0,0),
        '0' to Coordinate(0,-1),
        '1' to Coordinate(-1,-2),
        '2' to Coordinate(-1,-1),
        '3' to Coordinate(-1,0),
        '4' to Coordinate(-2,-2),
        '5' to Coordinate(-2,-1),
        '6' to Coordinate(-2,0),
        '7' to Coordinate(-3,-2),
        '8' to Coordinate(-3,-1),
        '9' to Coordinate(-3,0),
    )

    val directionalKeyPad = mapOf(
        'A' to Coordinate(0,0),
        '^' to Coordinate(0,-1),
        '<' to Coordinate(1,-2),
        'v' to Coordinate(1,-1),
        '>' to Coordinate(1,0),
    )

    val cache = mutableMapOf<Pair<String, Int>, Long>()

    val result = codes.sumOf { code ->
        var from = numericKeyPad['A']!!
        val cost = code.sumOf { toc ->
            val to = numericKeyPad[toc]!!
            val options = options(from, to, numericKeyPad.values)
            from = to
            options.minOf { path ->
                robot(path, 25, directionalKeyPad, cache)
            }
        }
        code.dropLast(1).toInt() * cost
    }

    println(result)
}

fun robot(path: String, nbOfRobots: Int, directionalKeyPad: Map<Char, Coordinate>, cache: MutableMap<Pair<String, Int>, Long>): Long {
    val key = Pair(path, nbOfRobots)
    if(key in cache) {
        return cache[key]!!
    } else {
        val res = robot2(path, nbOfRobots, directionalKeyPad, cache)
        cache[key] = res
        return res
    }
}

fun robot2(path: String, nbOfRobots: Int, directionalKeyPad: Map<Char, Coordinate>, cache: MutableMap<Pair<String, Int>, Long>): Long {
    if(nbOfRobots == 0) return path.length.toLong()

    var from = directionalKeyPad['A']!!
    return path.sumOf { p ->
        val to = directionalKeyPad[p]!!
        val options = options(from, to, directionalKeyPad.values)
        from = to
        options.minOf { path ->
            robot(path, nbOfRobots - 1, directionalKeyPad, cache)
        }
    }
}

fun options(from: Coordinate, to: Coordinate, validCoordinates: Collection<Coordinate>): Collection<String> {
    if(from !in validCoordinates) return emptyList()
    if(from == to) return listOf("A")
    val acc1 =
        if(from.col < to.col) {
            options(from.copy(col = from.col + 1), to, validCoordinates)
                .map { ">$it" }
        } else if(to.col < from.col) {
            options(from.copy(col = from.col - 1), to, validCoordinates)
                .map { "<$it" }
        } else {
            emptyList()
        }
    val acc2 =
        if(from.row < to.row) {
            options(from.copy(row = from.row + 1), to, validCoordinates)
                .map { "v$it" }
        } else if(to.row < from.row) {
            options(from.copy(row = from.row - 1), to, validCoordinates)
                .map { "^$it" }
        } else {
            emptyList()
        }
    return acc1 + acc2
}