import utils.asResource

fun main() {
//    val file = "day23/example.txt"
    val file = "day23/input.txt"

    val nodes = mutableMapOf<String, Node>()

    file.asResource().readText()
        .lines()
        .forEach { line ->
            val pieces = line.split("-")
            val nodes = pieces.map {
                if (it !in nodes) {
                    nodes[it] = Node(it)
                }
                nodes[it]!!
            }
            nodes[0].edges.add(nodes[1])
            nodes[1].edges.add(nodes[0])
        }

    var edge = nodes.values.map { listOf(it) }
    while(edge.size != 1) {
        edge = edge.flatMap { clique ->
            val last = clique.last()
            last.edges
                .filter { it.name > last.name }
                .filter { e -> clique.all { e in it.edges }}
                .map { e -> clique + e }
        }
    }

    val result = edge.first().joinToString(",") { it.name }
    println(result)
}

