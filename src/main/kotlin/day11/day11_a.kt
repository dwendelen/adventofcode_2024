package day11

import utils.asResource

fun main() {
//    val file = "day11/example.txt"
    val file = "day11/input.txt"

    val stones = file.asResource().readText().split(" ")

    val result = stones.map {
        nbStones(it.toLong(), 25)
    }.sum()

    println(result)
}

private fun nbStones(stone: Long, blinks: Int): Long {
    if(blinks == 0) {
        return 1L
    }
    if(stone == 0L) {
        return nbStones(1, blinks - 1)
    } else  {
        val asStr = stone.toString()
        if(asStr.length % 2 == 0) {
            return nbStones(asStr.substring(0, asStr.length / 2).toLong(), blinks - 1) +
                    nbStones(asStr.substring(asStr.length / 2).toLong(), blinks - 1)
        } else {
            return nbStones(stone * 2024, blinks - 1)
        }
    }
}