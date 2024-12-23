package day01

import utils.asResource
import kotlin.math.abs

fun main() {
//    val file = "day01/example.txt"
    val file = "day01/input.txt"

    val lines = file.asResource()
        .readText()
        .lines()
    val list1 = lines
        .map { it.split("   ")[0] }
        .map { it.toInt() }
        .sorted()
    val list2 = lines
        .map { it.split("   ")[1] }
        .map { it.toInt() }
        .sorted()

    val result = list1.zip(list2)
        .map { (a, b) -> abs(a - b) }
        .sum()

    println(result)
}

