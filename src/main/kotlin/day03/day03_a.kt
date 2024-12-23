package day03

import utils.asResource
import kotlin.math.abs
import kotlin.math.sign

fun main() {
//    val file = "day03/example1.txt"
    val file = "day03/input.txt"

    val regex = Regex("mul\\((\\d\\d?\\d?),(\\d\\d?\\d?)\\)")

    val text = file.asResource().readText()

    val result = regex.findAll(text)
        .map { it.groupValues[1].toLong() * it.groupValues[2].toLong() }
        .sum()

    println(result)
}

