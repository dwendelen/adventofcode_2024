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
        .replace("#", "##")
        .replace("O", "[]")
        .replace(".", "..")
        .replace("@", "@.")

    val (mapText, actionsText) = fileText.split("\n\n")
    val grid = loadGrid(mapText) {
        when(it) {
            '#' -> Wall2
            '@' -> Robot2
            '.' -> Nothing2
            '[' -> BoxL
            ']' -> BoxR
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
    val initialPosition = grid.itemMap()[Robot2]!![0]

    val initialState = State2(grid, initialPosition)
    val finalState = actions
        .fold(initialState) { state, action ->
            val newGrid = moveObject(state.grid, state.position, action)
            if(newGrid == null) {
                state
            } else {
                State2(newGrid, state.position + action)
            }
        }

    val result = finalState.grid.itemMap()[BoxL]!!
        .sumOf { it.row * 100 + it.col }

    println(result)
}

private fun moveObject(grid: Grid<Cell2>, position: Coordinate, direction: Vector): Grid<Cell2>? {
    val item = grid[position]!!
    return when (item) {
        // Always move the other box first
        // If going to right, you will always touch the left box first, so it's better to move the right one first
        is BoxL -> {
            moveSingleCell(grid, Coordinate(position.row, position.col + 1), direction)
                ?.let { movedR ->
                    moveSingleCell(movedR, position, direction)
                }
        }
        is BoxR -> {
            moveSingleCell(grid, Coordinate(position.row, position.col - 1), direction)
                ?.let { movedL ->
                    moveSingleCell(movedL, position, direction)
                }
        }
        else -> {
            moveSingleCell(grid, position, direction)
        }
    }
}

private fun moveSingleCell(grid: Grid<Cell2>, position: Coordinate, direction: Vector): Grid<Cell2>? {
    val item = grid[position]!!
    return when (item) {
        is Nothing2 -> grid
        is Wall2 -> null
        else -> {
            moveObject(grid, position + direction, direction)
                ?.set(position + direction, item)
                ?.set(position, Nothing2)
        }
    }
}

private sealed interface Cell2
private data object Wall2 : Cell2
private data object BoxL : Cell2
private data object BoxR : Cell2
private data object Robot2 : Cell2
private data object Nothing2 : Cell2

private data class State2(
    val grid: Grid<Cell2>,
    val position: Coordinate
)