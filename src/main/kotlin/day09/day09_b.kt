package day09

import utils.asResource

fun main() {
//    val file = "day09/example.txt"
    val file = "day09/input.txt"

    val line = file.asResource().readText()

    val memory = mutableListOf<Block>()
    var id = 0
    var fileSize = 0
    var block = true
    line.forEach { n ->
        val size = n.toString().toInt()
        if(block) {
            fileSize = size
        } else {
            memory.add(Block(id, fileSize, size))
            id++
        }
        block = !block
    }
    memory.add(Block(id, fileSize, 0))

    println(memory.sumOf { it.size + it.padding })

    var idx = memory.size - 1
    while(idx > 0) {
        val block = memory[idx]
        val newIdx = (0 until idx).find { idx2 ->
            val block2 = memory[idx2]
            block2.padding >= block.size
        }
        if (newIdx != null) {
            val oldLeftNeighbour = memory[idx - 1]
            memory[idx - 1] = oldLeftNeighbour.copy(padding = oldLeftNeighbour.padding + block.size + block.padding)
            memory.removeAt(idx)

            // In two steps because you can get issues when idx = newIdx + 1
            val leftNeighbour = memory[newIdx]
            memory[newIdx] = leftNeighbour.copy(padding = 0)
            memory.add(newIdx + 1, block.copy(padding = leftNeighbour.padding - block.size))
            // Because we are inserting left of idx, so the pointer moves along
            idx++
        }
        idx--
    }

    println(memory.sumOf { it.size + it.padding })

    var acc = 0L
    var loc = 0L
    memory.forEach { block ->
        for (i in 0 until block.size) {
            acc += loc * block.id
            loc++
        }
        loc += block.padding
    }

    // 7614828 is too low
    // 6511148897764 is too low
    println(acc)
}

data class Block(val id: Int, val size: Int, val padding: Int)