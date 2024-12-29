package day11

import utils.asResource

fun main() {
//    val file = "day11/example.txt"
    val file = "day11/input.txt"

    val stones = file.asResource().readText().split(" ")

    val cache = hashMapOf<Pair<Long, Int>, Long>()

    val result = stones.map {
        nbStones1(it.toLong(), 75, cache)
    }.sum()

    println(result)
}

private fun nbStones1(stone: Long, blinks: Int, cache: MutableMap<Pair<Long, Int>, Long>): Long {
    val key = Pair(stone, blinks)
    if(cache.containsKey(key)) {
        return cache[key]!!
    } else {
        val value = nbStones2(stone, blinks, cache)
        cache[key] = value
        return value
    }
}

private fun nbStones2(stone: Long, blinks: Int, cache: MutableMap<Pair<Long, Int>, Long>): Long {
    if(blinks == 0) {
        return 1L
    }
    if(stone == 0L) {
        return nbStones1(1, blinks - 1, cache)
    } else  {
        val asStr = stone.toString()
        if(asStr.length % 2 == 0) {
            return nbStones1(asStr.substring(0, asStr.length / 2).toLong(), blinks - 1, cache) +
                    nbStones1(asStr.substring(asStr.length / 2).toLong(), blinks - 1, cache)
        } else {
            return nbStones1(stone * 2024, blinks - 1, cache)
        }
    }
}