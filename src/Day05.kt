import kotlin.math.sign

fun main() {

    data class Point(val x: Int, val y: Int)

    data class Line(val p1: Point, val p2: Point) {
        fun isHorizontal() = p1.y == p2.y
        fun isVertical() = p1.x == p2.x
    }

    class Diagram(val size: Int) {
        val numbers = IntArray(size * size)

        fun inc(x: Int, y: Int) = numbers[y * size + x]++

        fun addLine(line: Line) = with(line) {
            val xStep = (p2.x - p1.x).sign
            val yStep = (p2.y - p1.y).sign

            val xProgression = if (xStep == 0) generateSequence { p1.x } else IntProgression.fromClosedRange(p1.x, p2.x, xStep).asSequence()
            val yProgression = if (yStep == 0) generateSequence { p1.y } else IntProgression.fromClosedRange(p1.y, p2.y, yStep).asSequence()

            xProgression.zip(yProgression).forEach { (x, y) -> inc(x, y) }
        }

        fun count(predicate: (Int) -> Boolean) = numbers.count(predicate)
    }

    fun parsePoint(point: String) = point.split(',').let { Point(it[0].toInt(), it[1].toInt()) }
    fun parseLine(line: String) = line.split(" -> ").let { Line(parsePoint(it[0]), parsePoint(it[1])) }
    fun parse(input: List<String>) = input.map { parseLine(it) }

    fun analyzeHydrothermalVents(input: List<String>, predicate: (Line) -> Boolean) = parse(input)
            .filter(predicate)
            .fold(Diagram(1000)) { diagram, line -> diagram.also { it.addLine(line) }}
            .count { it > 1 }

    fun part1(input: List<String>) = analyzeHydrothermalVents(input) { it.isHorizontal() || it.isVertical() }
    fun part2(input: List<String>) = analyzeHydrothermalVents(input) { true }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    check(part1(testInput) == 5)
    check(part2(testInput) == 12)

    val input = readInput("Day05")
    println(part1(input))
    println(part2(input))
}
