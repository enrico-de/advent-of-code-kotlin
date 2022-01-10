fun main() {

    data class Coordinate(val x: Int, val y: Int)

    fun parseFolds(lines: List<String>) = lines
        .takeLastWhile { it.isNotEmpty() }
        .map { it.substringAfter("fold along ") }

    fun parseCoordinates(lines: List<String>) = lines
        .takeWhile { it.isNotEmpty() }
        .map { it.split(",") }
        .map { Coordinate(it[0].toInt(), it[1].toInt()) }

    fun foldAlongX(x: Int, coordinates: List<Coordinate>) = coordinates
        .map { if (it.x > x) Coordinate(2 * x - it.x, it.y) else it }
        .distinct()

    fun foldAlongY(y: Int, coordinates: List<Coordinate>) = coordinates
        .map { if (it.y > y) Coordinate(it.x, 2 * y - it.y) else it }
        .distinct()

    fun fold(coordinates: List<Coordinate>, foldLine: String) = foldLine.split("=").let {
        if (it[0] == "x") foldAlongX(it[1].toInt(), coordinates) else foldAlongY(it[1].toInt(), coordinates)
    }

    fun fold(coordinates: List<Coordinate>, folds: List<String>) = folds
        .fold(coordinates) { acc: List<Coordinate>, foldLine: String -> fold(acc, foldLine) }
        .toSet()

    fun plot(coordinates: Set<Coordinate>) {
        val xMin = coordinates.minOf { it.x }
        val xMax = coordinates.maxOf { it.x }
        val yMin = coordinates.minOf { it.y }
        val yMax = coordinates.maxOf { it.y }

        (yMin .. yMax).forEach { y ->
            (xMin .. xMax).forEach { x ->
                if (Coordinate(x, y) in coordinates)
                    print('#')
                else
                    print('.')
            }
            println()
        }
        println()
    }

    fun part1(input: List<String>) = fold(parseCoordinates(input), parseFolds(input).first()).count()

    fun part2(input: List<String>) = plot(fold(parseCoordinates(input), parseFolds(input)))

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day13_test")
    check(part1(testInput) == 17)
    part2(testInput)

    val input = readInput("Day13")
    println(part1(input))
    part2(input)
}
