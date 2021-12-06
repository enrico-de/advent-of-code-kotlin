fun main() {

    /**
     * @param idx index of the bit in question (starting from the LSB)
     * @return 1 if the bit set, else 0
     */
    operator fun Int.get(idx: Int) = this shr idx and 1

    fun List<String>.asNumbers() = map { it.toInt(2) }

    /**
     * @param idx index of the bit in question (starting from the LSB)
     * @param numbers taken into account to determine the most common bit
     * @return 1 if the idx bit is set in more numbers than it is unset, else 0
     */
    fun mostCommon(idx: Int, numbers: List<Int>) = numbers.count { it[idx] == 1 } * 2 / numbers.size

    fun powerConsumption(numbers: List<Int>, bitCount: Int): Int {
        val gammaRate = IntRange(0, bitCount - 1)
                .map { mostCommon(it, numbers) shl it }
                .fold(0) { acc, i -> acc or i }

        val epsilonRate = (1 shl bitCount) - gammaRate - 1
        return gammaRate * epsilonRate
    }

    fun lifeSupportRating(numbers: List<Int>, bitCount: Int): Int {
        val numbers1 = numbers.toMutableList()
        val numbers2 = numbers.toMutableList()

        for (i in bitCount - 1 downTo 0) {
            val mostCommon = mostCommon(i, numbers1)
            if (numbers1.size > 1)
                numbers1.retainAll { it[i] == mostCommon }

            val leastCommon = mostCommon(i, numbers2) xor 1
            if (numbers2.size > 1)
                numbers2.retainAll { it[i] == leastCommon }
        }

        val oxygenGeneratorRating = numbers1.single()
        val co2ScrubberRating = numbers2.single()
        return oxygenGeneratorRating * co2ScrubberRating
    }

    fun part1(input: List<String>) = powerConsumption(input.asNumbers(), input.maxOf { it.length })
    fun part2(input: List<String>) = lifeSupportRating(input.asNumbers(), input.maxOf { it.length })

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 198)
    check(part2(testInput) == 230)

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}
