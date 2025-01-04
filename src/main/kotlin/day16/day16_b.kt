package day16

import Coordinate
import Vector
import loadGrid
import utils.asResource
import java.util.PriorityQueue

fun main() {
//    val file = "day16/example1.txt"
//    val file = "day16/example2.txt"
    val file = "day16/input.txt"

    val fileText = file.asResource().readText()

    val grid = loadGrid(fileText) {
        when (it) {
            '#' -> Wall
            'S' -> Start
            'E' -> End
            '.' -> Empty
            else -> throw IllegalArgumentException("Invalid grid character '$it'")
        }
    }

    val start = grid.itemMap()[Start]!![0]
    val end = grid.itemMap()[End]!![0]

    val cleanedGrid = grid.set(start, Empty).set(end, Empty)
    val initialState = State2(start, Vector(0, 1), 0, setOf(start))
    val priorityQueue = PriorityQueue<State2>()
    priorityQueue.add(initialState)

    val result = mutableSetOf<Coordinate>()
    val best = mutableMapOf<Pair<Coordinate, Vector>, Int>()
    var bestDistance: Int? = null
    while (true) {
        val state = priorityQueue.poll()
        if(bestDistance != null && state.cost > bestDistance) {
            break
        }
        if(state.coordinate == end) {
            bestDistance = state.cost
            result.addAll(state.path)
        } else {
            val key = Pair(state.coordinate, state.direction)
            val bestDistance = best[key] ?: Integer.MAX_VALUE
            if(state.cost <= bestDistance) {
                best[key] = state.cost
                val forward = state.coordinate + state.direction
                if(cleanedGrid[forward] is Empty) {
                    priorityQueue.add(State2(forward, state.direction, state.cost + 1, state.path + forward))
                }
                val left = state.direction.rotateCcw()
                val leftCell = state.coordinate + left
                if(cleanedGrid[leftCell] is Empty) {
                    priorityQueue.add(State2(leftCell, left, state.cost + 1001, state.path + leftCell))
                }
                val right = state.direction.rotateCw()
                val rightCell = state.coordinate + right
                if(cleanedGrid[rightCell] is Empty) {
                    priorityQueue.add(State2(rightCell, right, state.cost + 1001, state.path + rightCell))
                }
            }
        }
    }

    println(result.size)
}

data class State2(
    val coordinate: Coordinate,
    val direction: Vector,
    val cost: Int,
    val path: Set<Coordinate>
): Comparable<State2> {
    override fun compareTo(other: State2): Int {
        return cost.compareTo(other.cost)
    }
}
