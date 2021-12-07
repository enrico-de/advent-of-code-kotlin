import kotlin.math.absoluteValue

fun main() {

    fun parseCrabPositions(input: List<String>) = input
        .single()
        .split(',')
        .map { it.toInt() }

    fun fuelCost1(source: Int, target: Int) = (target - source).absoluteValue
    fun fuelCost2(source: Int, target: Int) = fuelCost1(source, target).let { it * (it + 1) / 2 }

    fun minimalFuel(positions: List<Int>, fuelCost: (Int, Int) -> Int) = (positions.minOf { it } .. positions.maxOf { it })
        .minOf { target -> positions.sumOf { fuelCost(it, target) } }

    fun part1(input: List<String>) = minimalFuel(parseCrabPositions(input), ::fuelCost1)
    fun part2(input: List<String>) = minimalFuel(parseCrabPositions(input), ::fuelCost2)

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    check(part1(testInput) == 37)
    check(part2(testInput) == 168)

    val input = readInput("Day07")
    println(part1(input))
    println(part2(input))
}
