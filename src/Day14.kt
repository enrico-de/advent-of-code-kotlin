fun main() {

    fun insert(polymer: MutableMap<String, Long>, rules: Map<String, Char>) {
        // find matching sequences
        val countsPerSeq = polymer.filterKeys { it in rules }

        // first remove old sequences
        countsPerSeq.keys.forEach { polymer.remove(it) }

        // count elements and new sequences
        countsPerSeq.forEach { (oldSeq, count) ->
            val element = rules.getValue(oldSeq).toString()
            polymer.compute(element) { _, v -> (v ?: 0) + count }
            polymer.compute(oldSeq[0] + element) { _, v -> (v ?: 0) + count }
            polymer.compute(element + oldSeq[1]) { _, v -> (v ?: 0) + count }
        }
    }

    fun parseWindows(template: String, windowSize: Int) = template
        .windowed(windowSize)
        .groupingBy { it }
        .eachCount()
        .mapValues { it.value.toLong() }

    fun parseTemplate(template: String) = parseWindows(template, 1) + parseWindows(template, 2)

    fun parseRules(rules: List<String>): Map<String, Char> = mutableMapOf<String, Char>().apply {
        rules
            .map { it.split("->") }
            .forEach {
                val seq = it[0].trim()
                val element = it[1].trim().single()
                this[seq] = element
            }
    }

    fun solve(insertionCount: Int, input: List<String>): Long {
        val polymer = parseTemplate(input[0]).toMutableMap()
        val rules = parseRules(input.drop(2))

        repeat(insertionCount) {
            insert(polymer, rules)
        }

        val elements = polymer.filterKeys { it.length == 1 }
        return elements.maxOf { it.value } - elements.minOf { it.value }
    }

    fun part1(input: List<String>) = solve(10, input)
    fun part2(input: List<String>) = solve(40, input)

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day14_test")
    check(part1(testInput) == 1588L)
    check(part2(testInput) == 2188189693529L)

    val input = readInput("Day14")
    println(part1(input))
    println(part2(input))
}
