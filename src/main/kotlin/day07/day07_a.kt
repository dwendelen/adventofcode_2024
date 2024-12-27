package day07

import utils.asResource

fun main() {
//    val file = "day07/example.txt"
    val file = "day07/input.txt"

    val lines = file.asResource()
        .readText()
        .lines()
        .map { line ->
            val (a, b) = line.split(": ")
            val numbers = b.split(" ")
                .map { it.toLong() }
            Line(a.toLong(), numbers[0], numbers.drop(1))
        }

    val result = lines
        .filter { test(it.testValue, it.first, it.rest, 0) }
        .sumOf { it.testValue }

    println(result)
}

private fun test(
    testValue: Long,
    acc: Long,
    numbers: List<Long>,
    idx: Int
): Boolean {
    if(acc > testValue) {
        return false
    }
    if(idx == numbers.size) {
        return testValue == acc
    }

    return test(testValue, acc + numbers[idx], numbers, idx + 1) ||
        test(testValue, acc * numbers[idx], numbers, idx + 1)
}