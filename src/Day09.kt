fun main() {

    class HeightMap(val values: IntArray, val rowCount: Int) {
        val colCount by lazy { values.size / rowCount }
        val offsets = listOf(0 to 1, 0 to -1, 1 to 0, -1 to 0)
        val lowPointIndices by lazy { values.indices.filter { isLowPoint(it) } }

        fun isLowPoint(idx: Int) = offsets.all { (dr, dc) -> isStrictlyLessThan(values[idx], row(idx) + dr, col(idx) + dc) }
        fun isStrictlyLessThan(value: Int, row: Int, col: Int) = !isValid(row, col) || value < get(row, col)
        fun isValid(row: Int, col: Int) = row in 0 until rowCount && col in 0 until colCount

        fun col(idx: Int) = idx % colCount
        fun row(idx: Int) = idx / colCount
        fun idx(row: Int, col: Int) = row * colCount + col
        fun get(row: Int, col: Int) = values[idx(row, col)]

        fun getBasin(seedIdx: Int) = mutableSetOf<Int>().also { growBasin(seedIdx, it) }
        fun growBasin(idx: Int, basin: MutableSet<Int>) {
            if (idx in basin || values[idx] == 9)
                return

            basin += idx
            offsets.forEach { (dr, dc) ->
                val row = row(idx) + dr
                val col = col(idx) + dc
                if (isValid(row, col))
                    growBasin(idx(row, col), basin)
            }
        }
    }

    fun parseHeightLine(line: String) = line.map { it.digitToInt() }

    fun parseHeightMap(lines: List<String>) = HeightMap(lines.flatMap { parseHeightLine(it) }.toIntArray(), lines.size)

    fun sumOfRiskLevels(heightMap: HeightMap) = heightMap.lowPointIndices.sumOf { heightMap.values[it] + 1 }
    fun findBasins(heightMap: HeightMap) = heightMap.lowPointIndices.map { heightMap.getBasin(it) }

    fun part1(input: List<String>) = sumOfRiskLevels(parseHeightMap(input))
    fun part2(input: List<String>) = findBasins(parseHeightMap(input))
        .map { it.size }
        .sortedDescending()
        .take(3)
        .let { it[0] * it[1] * it[2] }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day09_test")
    check(part1(testInput) == 15)
    check(part2(testInput) == 1134)

    val input = readInput("Day09")
    println(part1(input))
    println(part2(input))
}
