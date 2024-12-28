package day09

import utils.asResource

fun main() {
//    val file = "day09/example.txt"
    val file = "day09/input.txt"

    val line = file.asResource().readText()

    val memory = mutableListOf<Long?>()
    var id = 0L
    var block = true
    line.forEach { n ->
        val nAsInt = n.toString().toInt()
        if(block) {
            (1..nAsInt).forEach {
                memory.add(id)
            }
            id++
        } else {
            (1..nAsInt).forEach {
                memory.add(null)
            }
        }
        block = !block
    }

    var i = 0
    var j = memory.size - 1

    while(i < j) {
        if(memory[i] == null) {
           memory[i] = memory[j]
           memory[j] = null
           while(memory[j] == null ) {
               j--
           }
        }
        i++
    }

    val result = memory
        .mapIndexed { idx, id ->
            if(id == null) {
                0L
            } else {
                idx * id
            }
        }
        .sum()

    println(result)
}
