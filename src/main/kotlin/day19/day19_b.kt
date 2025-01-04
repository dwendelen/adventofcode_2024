package day19

import utils.asResource

fun main() {
//    val file = "day19/example.txt"
    val file = "day19/input.txt"


    val fileText = file.asResource().readText()
    val (towelPart, patternPart) = fileText.split("\n\n")
    val towels = towelPart.split(", ")

    val cache = mutableMapOf<String, Long>()

    val result = patternPart.lines()
        .sumOf {
            combinations(cache, towels, it)
        }

    // 102717210 is too low
    println(result)
}

fun combinations(cache: MutableMap<String, Long>, towels: List<String>, pattern: String): Long {
    if(pattern in cache) {
        return cache[pattern]!!
    } else {
        val value = if (pattern == "") {
            1L
        } else {
            towels.sumOf { towel ->
                if (pattern.startsWith(towel)) {
                    combinations(cache, towels, pattern.substring(towel.length))
                } else {
                    0L
                }
            }
        }
        cache[pattern] = value
        return value
    }
}
