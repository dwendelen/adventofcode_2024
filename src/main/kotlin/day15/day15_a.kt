package day15

import Coordinate
import Grid
import Vector
import loadGrid
import utils.asResource

fun main() {
//    val file = "day15/example1.txt"
//    val file = "day15/example2.txt"
    val file = "day15/input.txt"

    val fileText = file.asResource().readText()

    val (mapText, actionsText) = fileText.split("\n\n")
    val grid = loadGrid(mapText) {
        when(it) {
            '#' -> Wall
            '@' -> Robot
            '.' -> Nothing
            'O' -> Box
            else -> throw IllegalArgumentException("Invalid grid character '$it'")
        }
    }
    val actions = actionsText.replace("\n", "")
        .map {
            when(it) {
                '^' -> Vector(-1, 0)
                '>' -> Vector(0, 1)
                'v' -> Vector(1, 0)
                '<' -> Vector(0, -1)
                else -> throw IllegalArgumentException("Invalid grid character '$it'")
            }
        }
    val initialPosition = grid.itemMap()[Robot]!![0]

    val initialState = State(grid, initialPosition)
    val finalState = actions
        .fold(initialState) { state, action ->
            val newGrid = moveObject(state.grid, state.position, action)
            if(newGrid == null) {
                state
            } else {
                State(newGrid, state.position + action)
            }
        }

    val result = finalState.grid.itemMap()[Box]!!
        .sumOf { it.row * 100 + it.col }

    println(result)
}

private fun moveObject(grid: Grid<Cell>, position: Coordinate, direction: Vector): Grid<Cell>? {
    val item = grid[position]!!
    return when (item) {
        is Nothing -> grid
        is Wall -> null
        else -> {
            moveObject(grid, position + direction, direction)
                ?.set(position + direction, item)
                ?.set(position, Nothing)
        }
    }
}


private sealed interface Cell
private data object Wall : Cell
private data object Box : Cell
private data object Robot : Cell
private data object Nothing : Cell

private data class State(
    val grid: Grid<Cell>,
    val position: Coordinate
)