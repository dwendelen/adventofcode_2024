package day17

import utils.asResource

fun main() {
//    val file = "day17/example2.txt"
    val file = "day17/input.txt"

    val fileText = file.asResource().readText()
    val programPart = fileText.split("\n\n")[1]
    var ptr = 0

    val program = programPart.substring(9).split(",").map { it.toInt() }

    fun combo(op: Int): String {
        return if(op <= 3) {
            op.toString()
        } else if (op == 4) {
            "A"
        } else if(op == 5) {
            "B"
        } else if(op == 6) {
            "C"
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
                println("A = A >> ${combo(op2)}")
            }
            1 -> {
                // bxl
                println("B = B xor $op2")
            }
            2 -> {
                // bst
                println("B = ${combo(op2)} % 8")
            }
            3 -> {
                // jnz
                println("Jump not zero $op2")
            }
            4 -> {
                // bxc
                println("B = B xor C")
            }
            5 -> {
                // out
                println("Output ${combo(op2)} % 8")
            }
            6 -> {
                // bdv
                println("B = A >> ${combo(op2)}")
            }
            7 -> {
                // cdv
                println("C = A >> ${combo(op2)}")
            }
        }
        ptr += 2
    }
}

