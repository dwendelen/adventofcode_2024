package day18

import Coordinate
import utils.asResource
import java.util.*

fun main() {
//    val file = "day18/example.txt"
//    val n = 12
//    val bounds = (0..6)
    val file = "day18/input.txt"
    val n = 1024
    val bounds = (0..70)

    val drops = file.asResource().readText()
        .lines()
        .map {
            val (row, col) = it.split(",").map(String::toInt)
            Coordinate(row, col)
        }

    val blocked = drops
        .take(n)
        .toSet()

    val start = Coordinate(0, 0)

    val priorityQueue = PriorityQueue<State>()
    priorityQueue.add(State(start, 0))

    val best = mutableMapOf<Coordinate, Int>()

    val result: Int
    while(true) {
        val state = priorityQueue.poll()
        if(state.cost >= (best[state.coordinate] ?: Int.MAX_VALUE)) {
            continue
        }
        best[state.coordinate] = state.cost
        if(state.coordinate.row == bounds.last && state.coordinate.col == bounds.last) {
            result = state.cost
            break
        } else {
            state.coordinate.neighbours()
                .filter { it.row in bounds && it.col in bounds && it !in blocked }
                .forEach {
                    priorityQueue.add(State(it, state.cost + 1))
                }
        }
    }

    println(result)
}

data class State(
    val coordinate: Coordinate,
    val cost: Int
): Comparable<State> {
    override fun compareTo(other: State): Int {
        return cost.compareTo(other.cost)
    }
}
