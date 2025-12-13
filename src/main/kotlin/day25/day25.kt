import utils.asResource

fun main() {
//    val file = "day25/example.txt"
    val file = "day25/input.txt"

    val lines = file.asResource().readText()
        .lines()

    val locksAndKeys = lines.windowed(7, 8)
        .map { lines ->
            val lock = lines[0][0] == '#'
            val heights = (0 until lines[0].length)
                .map { idx ->
                    lines.count { it[idx] == '#' } - 1
                }
            if (lock) {
                Lock(heights)
            } else {
                Key(heights)
            }
        }
    val locks = locksAndKeys.filterIsInstance<Lock>()
    val keys = locksAndKeys.filterIsInstance<Key>()

    val result = locks.sumOf { l ->
        keys.count { k ->
            (l.heights zip k.heights)
                .all { (ll, hh) -> ll + hh < 6 }
        }
    }

    println(result)
}

data class Lock(
    val heights: List<Int>
)
data class Key(
    val heights: List<Int>
)