fun main() {

    data class Entry(val patterns: List<String>, val outputValue: List<String>) {

        fun decode(): Int {
            val sets = patterns.map { it.toSet() }.toMutableSet()

            val segmentsPerDigit = mutableMapOf<Int, Set<Char>>()
            segmentsPerDigit[1] = sets.single { it.size == 2 }.also { sets.remove(it) }
            segmentsPerDigit[4] = sets.single { it.size == 4 }.also { sets.remove(it) }
            segmentsPerDigit[7] = sets.single { it.size == 3 }.also { sets.remove(it) }
            segmentsPerDigit[8] = sets.single { it.size == 7 }.also { sets.remove(it) }
            segmentsPerDigit[9] = sets.single { it.size == 6 && it.containsAll(segmentsPerDigit[4]!!) }.also { sets.remove(it) }
            segmentsPerDigit[0] = sets.single { it.size == 6 && it.containsAll(segmentsPerDigit[1]!!) }.also { sets.remove(it) }
            segmentsPerDigit[6] = sets.single { it.size == 6 }.also { sets.remove(it) }
            segmentsPerDigit[3] = sets.single { it.size == 5 && it.containsAll(segmentsPerDigit[1]!!) }.also { sets.remove(it) }
            segmentsPerDigit[5] = sets.single { it.size == 5 && segmentsPerDigit[9]!!.containsAll(it) }.also { sets.remove(it) }
            segmentsPerDigit[2] = sets.single { it.size == 5 }

            val digitPerSegments = segmentsPerDigit.entries.associate { Pair(it.value, it.key) }

            return outputValue
                .map { it.toSet() }
                .joinToString("") { "${digitPerSegments[it]}" }
                .toInt()
        }
    }

    fun parseSignalPatterns(input: List<String>) = input
        .map { entry -> entry.split('|').map { it.trim().split(' ') }}
        .map { Entry(it[0], it[1]) }

    fun countEasyDigits(parseSignalPatterns: List<Entry>) = parseSignalPatterns
        .flatMap { it.outputValue }
        .count { it.length in listOf(2, 3, 4, 7) }

    fun part1(input: List<String>) = countEasyDigits(parseSignalPatterns(input))
    fun part2(input: List<String>) = parseSignalPatterns(input).sumOf { it.decode() }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08_test")
    check(part1(testInput) == 26)
    check(part2(testInput) == 61229)

    val input = readInput("Day08")
    println(part1(input))
    println(part2(input))
}
