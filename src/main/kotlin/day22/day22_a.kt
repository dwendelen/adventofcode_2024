import utils.asResource

fun main() {
//    val file = "day22/example1.txt"
    val file = "day22/input.txt"

    val codes = file.asResource().readText()
        .lines()
        .map { it.toLong() }

    val result = codes.sumOf { code ->
        (1..2000)
            .fold(code) { acc, _ ->
                val n1 = ((acc * 64) xor acc) % 16777216
                val n2 = ((n1 / 32) xor n1) % 16777216
                val n3 = ((n2 * 2048) xor n2) % 16777216
                n3
            }
    }
    println(result)
}
