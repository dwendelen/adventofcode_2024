import utils.asResource

fun main() {
//    val file = "day22/example2.txt"
    val file = "day22/input.txt"

    val prices = file.asResource().readText()
        .lines()
        .map { it.toLong() }
        .map { code ->
            val prics = (1..2000)
                .scan(code) { acc, _ ->
                    val n1 = ((acc * 64) xor acc) % 16777216
                    val n2 = ((n1 / 32) xor n1) % 16777216
                    val n3 = ((n2 * 2048) xor n2) % 16777216
                    n3
                }.map { (it % 10).toInt() }

            val p1 = prics.dropLast(4)
            val p2 = prics.drop(1).dropLast(3)
            val p3 = prics.drop(2).dropLast(2)
            val p4 = prics.drop(3).dropLast(1)
            val p5 = prics.drop(4)

            (0 until p1.size).reversed().associate { idx ->
                listOf(
                    p2[idx] - p1[idx],
                    p3[idx] - p2[idx],
                    p4[idx] - p3[idx],
                    p5[idx] - p4[idx]
                ) to p5[idx]
            }
        }

    val resultMap = mutableMapOf<List<Int>, Int>()
    for (map in prices) {
        for (entry in map) {
            if(entry.key in resultMap) {
                resultMap[entry.key] = resultMap[entry.key]!! + entry.value
            } else {
                resultMap[entry.key] = entry.value
            }
        }
    }

    val result = resultMap.values.max()

    println(result)
}
