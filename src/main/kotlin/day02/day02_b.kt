package day02

import utils.asResource
import kotlin.math.abs
import kotlin.math.sign

fun main() {
//    val file = "day02/example.txt"
    val file = "day02/input.txt"

    val lines = file.asResource()
        .readText()
        .lines()

    val result = lines.map { line ->
        val numbers = line.split(" ")
            .map { it.toInt() }

        val isAlreadySafe = isSafe(numbers)
        val anySafeAfterFix = (0 until numbers.size)
            .map {
                val res = numbers.toMutableList()
                res.removeAt(it)
                res
            }
            .any { isSafe(it) }


        isAlreadySafe || anySafeAfterFix
    }
        .count { it }

    println(result)
}

private fun isSafe(numbers: List<Int>): Boolean {
    val direction = (numbers[1] - numbers[0]).sign

    return numbers.zipWithNext()
        .all { (a, b) ->
            val correctedDir = direction * (b - a)
            correctedDir in 1..3
        }
}


