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
    val initialState = State(start, Vector(0, 1), 0)
    val priorityQueue = PriorityQueue<State>()
    priorityQueue.add(initialState)

    val result: Int
    val best = mutableMapOf<Pair<Coordinate, Vector>, Int>()
    while (true) {
        val state = priorityQueue.poll()
        if(state.coordinate == end) {
            result = state.cost
            break
        } else {
            val key = Pair(state.coordinate, state.direction)
            val bestDistance = best[key] ?: Integer.MAX_VALUE
            if(state.cost < bestDistance) {
                best[key] = state.cost
                val forward = state.coordinate + state.direction
                if(cleanedGrid[forward] is Empty) {
                    priorityQueue.add(State(forward, state.direction, state.cost + 1))
                }
                val left = state.direction.rotateCcw()
                val leftCell = state.coordinate + left
                if(cleanedGrid[leftCell] is Empty) {
                    priorityQueue.add(State(leftCell, left, state.cost + 1001))
                }
                val right = state.direction.rotateCw()
                val rightCell = state.coordinate + right
                if(cleanedGrid[rightCell] is Empty) {
                    priorityQueue.add(State(rightCell, right, state.cost + 1001))
                }
            }

        }
    }

    println(result)
}

data class State(
    val coordinate: Coordinate,
    val direction: Vector,
    val cost: Int
): Comparable<State> {
    override fun compareTo(other: State): Int {
        return cost.compareTo(other.cost)
    }
}
