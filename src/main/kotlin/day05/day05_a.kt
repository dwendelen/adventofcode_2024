package day05

import utils.asResource

fun main() {
//    val file = "day05/example.txt"
    val file = "day05/input.txt"

    val (input1, input2) = file.asResource()
        .readText()
        .split("\n\n")

    val rules = input1
        .lines()
        .map { l ->
            val (from, to) = l.split("|").map { it.toInt() }
            Rule(from, to)
        }

    val updates = input2
        .lines()
        .map { l ->
            l.split(",")
                .map { it.toInt() }
        }

    val result = updates.map { up ->
        val correct = rules.all { rule ->
            val b = up.indexOf(rule.before)
            val a = up.indexOf(rule.after)
            a == -1 || b == -1 || b < a
        }
        if(correct) {
            up[up.size / 2]
        } else {
            0
        }
    }.sum()

    println(result)
}
