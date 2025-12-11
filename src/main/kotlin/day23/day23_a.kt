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

    val nodeSet = nodes.values

    val triangles = nodeSet.flatMap { n ->
        n.edges
            .filter { it.name > n.name }
            .flatMap { e1 ->
                e1.edges
                    .filter { it.name > e1.name }
                    .filter { e2 -> e2 in n.edges }
                    .map { e2 -> Triple(n, e1, e2) }
            }
    }

    val result = triangles
        .count { it.first.name[0] == 't' || it.second.name[0] == 't' || it.third.name[0] == 't' }

    println(result)
}
