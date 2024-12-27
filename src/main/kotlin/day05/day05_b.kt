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

    val badUpdates = updates.filter { up ->
        !rules.all { rule ->
            val b = up.indexOf(rule.before)
            val a = up.indexOf(rule.after)
            a == -1 || b == -1 || b < a
        }
    }

    val corrected = badUpdates
        .map { up ->
            val todo = up.toMutableSet()
            val acc = mutableListOf<Int>()
            while(todo.isNotEmpty()) {
                val nxt = todo.first { t ->
                    rules.all { rule ->
                        rule.after != t || !todo.contains(rule.before)
                    }
                }
                acc += nxt
                todo.remove(nxt)
            }
            acc
        }

    val sum = corrected
        .sumOf { it[it.size  / 2] }

    println(sum)
}
