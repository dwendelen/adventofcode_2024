package day17

import utils.asResource

/*
    Program:

    B = A % 8
    B = B xor 6
    C = A >> B
    B = B xor C
    B = B xor 7
    A = A >> 3
    Output B % 8
    Jump not zero 0

    - The least significant digits of A are used first
    - A is shifted, so we go from least to high significant digits
    - The calculation of A uses digits that have a higher significance

    So we want to work from highest-significance to lower significance
    because, for example, the highest significant digits directly impact
    the last output digit without having to look at the rest.

    Sometimes it could be that the higher significant digits don't work,
    then we need to back-track and find alternatives for the same outcome

    Also: we want the lowest number, so it's best to search the highest significant
    digits first, and we can stop as soon as we find a match.
 */
fun main() {
//    val file = "day17/example2.txt"
    val file = "day17/input.txt"

    val fileText = file.asResource().readText()
    val programPart = fileText.split("\n\n")[1]

    val program = programPart.substring(9).split(",").map { it.toInt() }

    val result = findA(program, 0, program.size - 1)

    println(result)
}

fun findA(program: List<Int>, acc: Long, idx: Int): Long? {
    if(idx < 0) {
        return acc
    } else {
        (0..7).forEach { piece ->
            val newA = 8 * acc + piece
            val output = runProgram(newA, program)
            if(output == program[idx]) {
                val nested = findA(program, newA, idx - 1)
                if(nested != null) {
                    return nested
                }
            }
        }
        return null
    }
}

private fun runProgram(initialA: Long, program: List<Int>): Int {
    var a = initialA
    var b = 0L
    var c = 0L
    var ptr = 0

    fun combo(op: Int): Long {
        return if (op <= 3) {
            op.toLong()
        } else if (op == 4) {
            a
        } else if (op == 5) {
            b
        } else if (op == 6) {
            c
        } else {
            throw UnsupportedOperationException()
        }
    }

    while (ptr in program.indices) {
        val op1 = program[ptr]
        val op2 = program[ptr + 1]
        when (op1) {
            0 -> {
                // adv
                a = a shr combo(op2).toInt()
                ptr += 2
            }

            1 -> {
                // bxl
                b = b xor op2.toLong()
                ptr += 2
            }

            2 -> {
                // bst
                b = combo(op2) % 8
                ptr += 2
            }

            3 -> {
                // jnz
                if (a == 0L) {
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
                // We are only interested in the new digit this time
                return (combo(op2) % 8L).toInt()
            }

            6 -> {
                // bdv
                b = a shr combo(op2).toInt()
                ptr += 2
            }

            7 -> {
                // cdv
                c = a shr combo(op2).toInt()
                ptr += 2
            }

            else -> {
                throw UnsupportedOperationException()
            }
        }
    }
    throw IllegalStateException()
}

