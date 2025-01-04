package day17

import utils.asResource

fun main() {
//    val file = "day17/example1.txt"
    val file = "day17/input.txt"

    val fileText = file.asResource().readText()
    val (regPart, programPart) = fileText.split("\n\n")
    var (a, b, c) = regPart.lines().map { it.substring(12).toInt() }
    var ptr = 0

    val program = programPart.substring(9).split(",").map { it.toInt() }
    val output = mutableListOf<Int>()

    fun combo(op: Int): Int {
        return if(op <= 3) {
            op
        } else if (op == 4) {
            a
        } else if(op == 5) {
            b
        } else if(op == 6) {
            c
        } else {
            throw UnsupportedOperationException()
        }
    }

    while(ptr in program.indices) {
        val op1 = program[ptr]
        val op2 = program[ptr + 1]
        when(op1) {
            0 -> {
                // adv
                a = a shr combo(op2)
                ptr += 2
            }
            1 -> {
                // bxl
                b = b xor op2
                ptr += 2
            }
            2 -> {
                // bst
                b = combo(op2) % 8
                ptr += 2
            }
            3 -> {
                // jnz
                if(a == 0) {
                    ptr += 2
                } else {
                    ptr = op2
                }
            }
            4 -> {
                // bxc
                b = b xor c
                ptr += 2
            }
            5 -> {
                // out
                output.add(combo(op2) % 8)
                ptr += 2
            }
            6 -> {
                // bdv
                b = a shr combo(op2)
                ptr += 2
            }
            7 -> {
                // cdv
                c = a shr combo(op2)
                ptr += 2
            }
            else -> {
                throw UnsupportedOperationException()
            }
        }
    }

    val result = output
        .joinToString(",") { it.toString() }
    println(result)
}

