package day21

import Coordinate
import Vector
import neighbourVectors
import utils.asResource
import java.util.*

/*
    You always start and end at A
    -> order of up, down, left, right does not matter
    -> but best first all up and then all left
 */
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

    val numericShortest = shortestPaths(numericKeyPad)
    val directionalShortest = shortestPaths(directionalKeyPad)

    val result = codes.sumOf { code ->
        val codes1 = resolveCode(code.toList(), numericShortest)
        val codes1Shortest = codes1.minOf { it.size }
        val code1Filtered = codes1.filter { it.size == codes1Shortest }
        val codes2 = code1Filtered.flatMap {
            resolveCode(it, directionalShortest)
        }
        val codes2Shortest = codes2.minOf { it.size }
        val code2Filtered = codes2.filter { it.size == codes2Shortest }
        val codes3 = code2Filtered.flatMap {
            resolveCode(it, directionalShortest)
        }.toSet()
        val codes3Shortest = codes3.minOf { it.size }

        println("$code  $codes3Shortest")
        code.replace("A", "").toInt() * codes3Shortest
    }

    println(result)
}

private fun resolveCode(code: List<Char>, shortestPaths: Map<Pair<Char, Char>, Set<List<Char>>>): Set<List<Char>> {
    var current = 'A'
    var acc = setOf(emptyList<Char>())
    code.forEach { to ->
        acc = acc.flatMap { path ->
            shortestPaths[current to to]!!.map {
                path + it + 'A'
            }
        }.toSet()
        current = to
    }
    val shortest = acc.minOf { it.size }
    return acc.filter { it.size == shortest }.toSet()
}

private fun shortestPaths(keypad: Map<Char, Coordinate>): Map<Pair<Char, Char>, Set<List<Char>>> {
    val coordinates = keypad.values.toSet()
    return coordinates.flatMap { from ->
        coordinates.map { to ->
            val initialState = State(from, emptyList())
            val priorityQueue = PriorityQueue<State>()
            priorityQueue.add(initialState)

            val result = mutableSetOf<List<Vector>>()
            val best = mutableMapOf<Coordinate, Int>()
            var bestDistance: Int? = null
            while (priorityQueue.isNotEmpty()) {
                val state = priorityQueue.poll()
                if(bestDistance != null && state.path.size > bestDistance) {
                    break
                }
                if(state.coordinate == to) {
                    bestDistance = state.path.size
                    result.add(state.path)
                } else {
                    val bestDistance = best[state.coordinate] ?: Integer.MAX_VALUE
                    if(state.path.size <= bestDistance) {
                        best[state.coordinate] = state.path.size
                        neighbourVectors.forEach { vec ->
                            val newCoordinate = state.coordinate + vec
                            if(newCoordinate in coordinates) {
                                priorityQueue.add(State(newCoordinate, state.path + vec))
                            }
                        }
                    }
                }
            }
            (from to to) to result
        }
    }.map { (key, setOfPaths) ->
        val (from, to) = key
        val fromKey = keypad.entries.filter { it.value == from }[0].key
        val toKey = keypad.entries.filter { it.value == to }[0].key
        val mappedPaths = setOfPaths.map { path ->
            path.map { vec ->
                when(vec) {
                    Vector(0, -1) -> '<'
                    Vector(0, 1) -> '>'
                    Vector(-1, 0) -> '^'
                    Vector(1, 0) -> 'v'
                    else -> throw UnsupportedOperationException()
                }
            }
        }.toSet()
        (fromKey to toKey) to mappedPaths
    }.toMap()
}

private data class State(
    val coordinate: Coordinate,
    val path: List<Vector>
): Comparable<State> {
    override fun compareTo(other: State): Int {
        return path.size.compareTo(other.path.size)
    }
}
