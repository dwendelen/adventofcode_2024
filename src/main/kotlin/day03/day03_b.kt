package day03

import utils.asResource

fun main() {
//    val file = "day03/example2.txt"
    val file = "day03/input.txt"

    val regex = Regex("mul\\((\\d\\d?\\d?),(\\d\\d?\\d?)\\)|(do\\(\\))|(don't\\(\\))")

    val text = file.asResource().readText()

    val result = regex.findAll(text)
        .fold(State(0, true)) { state, op ->
            if(op.groupValues[1] != "") {
                val res = op.groupValues[1].toLong() * op.groupValues[2].toLong()
                if(state.enabled) {
                    state.copy(acc = state.acc + res)
                } else {
                    state
                }
            } else if(op.groupValues[3] != "") {
                state.copy(enabled = true)
            } else if(op.groupValues[4] != "") {
                state.copy(enabled = false)
            } else {
                throw UnsupportedOperationException()
            }
        }


    println(result.acc)
}

data class State(
    val acc: Long,
    val enabled: Boolean
)