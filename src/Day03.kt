fun main() {

    fun part1(input: List<String>): Int {
        val bitCount = input.maxOf { it.length }
        var gammaRate = 0

        for (i in 0 until bitCount) {
            if (input.map { it.toInt(2) }.map { it shr i }.count { it.mod(2) == 1 } > input.size / 2)
                gammaRate = gammaRate or (1 shl i)
        }

        val epsilonRate = (1 shl bitCount) - gammaRate - 1
        return gammaRate * epsilonRate
    }

    fun part2(input: List<String>): Int {
        val bitCount = input.maxOf { it.length }

//        for (i in bitCount - 1 downTo 0) {
//        }

        val oxygenGeneratorRating = 0
        val co2ScrubberRating = 0
        return oxygenGeneratorRating * co2ScrubberRating
    }

    fun mostCommon(index: Int, input: List<Int>) = if (input
        .map { it shr index }
        .count { it.mod(2) == 1 } > input.size / 2) 1 else 0

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 198)
    check(part2(testInput) == 230)

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}
