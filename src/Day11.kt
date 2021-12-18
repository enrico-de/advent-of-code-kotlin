fun main() {

    class EnergyLevels(var values: IntArray, val rowCount: Int) {
        val colCount by lazy { values.size / rowCount }
        val offsets = listOf(-1 to -1, -1 to 0, -1 to 1, 0 to -1, 0 to 1, 1 to -1, 1 to 0, 1 to 1)

        fun step() : Int {
            values.indices.forEach { values[it]++ }

            var flashes = 0

            while (true) {
                val flashIndex = values.indexOfFirst { it == 10 }
                if (flashIndex == -1)
                    break

                flashes++
                values[flashIndex]++

                offsets.forEach { (dr, dc) ->
                    val row = row(flashIndex) + dr
                    val col = col(flashIndex) + dc
                    val idx = idx(row, col)
                    if (isValid(row, col) && values[idx] < 10)
                        values[idx]++
                }
            }

            values.indices.forEach {
                if (values[it] > 10)
                    values[it] = 0
            }

            return flashes
        }

        fun isValid(row: Int, col: Int) = row in 0 until rowCount && col in 0 until colCount

        fun col(idx: Int) = idx % colCount
        fun row(idx: Int) = idx / colCount
        fun idx(row: Int, col: Int) = row * colCount + col
    }

    fun parseEnergyLevelLine(line: String) = line.map { it.digitToInt() }
    fun parseEnergyLevels(lines: List<String>) = EnergyLevels(lines.flatMap { parseEnergyLevelLine(it) }.toIntArray(), lines.size)

    fun part1(input: List<String>) = parseEnergyLevels(input).let { levels ->
        (1..100).sumOf { levels.step() }
    }

    fun part2(input: List<String>) = parseEnergyLevels(input).let { levels ->
        generateSequence(1) { it + 1 }
            .find { levels.step() == levels.values.size }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day11_test")
    check(part1(testInput) == 1656)
    check(part2(testInput) == 195)

    val input = readInput("Day11")
    println(part1(input))
    println(part2(input))
}
