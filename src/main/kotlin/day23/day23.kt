class Node(
    val name: String
) {
    val edges = mutableSetOf<Node>()
}