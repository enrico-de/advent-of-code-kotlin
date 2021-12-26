fun main() {

    data class Node(val name: String)
    data class Edge(val node1: Node, val node2: Node) : Set<Node> by setOf(node1, node2)

    fun parseEdge(edge: String): Edge {
        val (name1, name2) = edge.split("-")
        return Edge(Node(name1), Node(name2))
    }

    fun String.isLowercase() = this == this.lowercase()

    fun containsOneLowercaseNodeTwiceElseOnceAtMax(path: List<Node>): Boolean = path
        .filter { it.name.isLowercase() }
        .groupBy { it.name }
        .filter { it.value.size > 1 }
        .let {
            it.isEmpty() || (it.count() == 1 && it.values.single().size == 2)
        }

    fun containsLowercaseNodesOnceAtMax(path: List<Node>) = path
        .filter { it.name.isLowercase() }
        .groupBy { it.name }
        .maxOf { it.value.size } == 1

    fun isValidPath(path: List<Node>) = path.count { it.name == "start" } == 1 && path.count { it.name == "end" } <= 1
    fun isValidPath1(path: List<Node>) = isValidPath(path) && containsLowercaseNodesOnceAtMax(path)
    fun isValidPath2(path: List<Node>) = isValidPath(path) && containsOneLowercaseNodeTwiceElseOnceAtMax(path)

    fun distinctPaths(input: List<String>, isValidPath: (List<Node>) -> Boolean): Set<List<Node>> {
        val edges = input.map { parseEdge(it) }.toSet()
        val nodes = edges.flatMap { it.asSequence() }.toSet()
        val adjacencies = nodes
            .associateWith { node -> edges
                .filter { edge -> node in edge }
                .flatMap { it.asSequence() }
                .filterNot { it == node }
            }

        val start = nodes.find { it.name == "start" }!!
        val end = nodes.find { it.name == "end" }!!

        val paths = mutableSetOf<List<Node>>()
        val path = mutableListOf(start)
        val next = adjacencies[start]!!.toMutableList()

        while (next.isNotEmpty()) {
            if (next.last() == path.last()) {
                next.removeLast()
                path.removeLast()
            } else {
                path.add(next.last())
                if (path.last() == end) {
                    paths += path.toList()
                } else if (isValidPath(path)) {
                    next.addAll(adjacencies[path.last()]!!)
                }
            }
        }

        return paths
    }

    fun part1(input: List<String>) = distinctPaths(input, ::isValidPath1).size
    fun part2(input: List<String>) = distinctPaths(input, ::isValidPath2).size

    // test if implementation meets criteria from the description, like:
    val testInput1 = readInput("Day12_test1")
    val testInput2 = readInput("Day12_test2")
    val testInput3 = readInput("Day12_test3")
    check(part1(testInput1) == 10)
    check(part1(testInput2) == 19)
    check(part1(testInput3) == 226)
    check(part2(testInput1) == 36)
    check(part2(testInput2) == 103)
    check(part2(testInput3) == 3509)

    val input = readInput("Day12")
    println(part1(input))
    println(part2(input))
}
