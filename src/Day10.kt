import java.util.*

fun main() {

    val errorScores = mapOf(
        ')' to 3,
        ']' to 57,
        '}' to 1197,
        '>' to 25137)

    val autocompleteScores = mapOf(
        ')' to 1,
        ']' to 2,
        '}' to 3,
        '>' to 4)

    val brackets = mapOf(
        '(' to ')',
        '[' to ']',
        '{' to '}',
        '<' to '>'
    )

    fun computeCorruptedScore(line: String): Int {
        val stack = Stack<Char>()

        line.forEach {
            if (it in brackets.keys)
                stack.push(it)
            else if (it != brackets[stack.pop()])
                return errorScores[it]!!
        }

        return 0
    }

    fun computeIncompleteScore(line: String): Long {
        val stack = Stack<Char>()

        line.forEach {
            if (it in brackets.keys)
                stack.push(it)
            else
                stack.pop()
        }

        return stack
            .reversed()
            .map { brackets[it]!! }
            .map { autocompleteScores[it]!! }
            .fold(0) { acc: Long, score: Int -> acc * 5 + score }
    }

    fun part1(input: List<String>) = input.sumOf { computeCorruptedScore(it) }
    fun part2(input: List<String>) = input
        .filter { computeCorruptedScore(it) == 0 }
        .map { computeIncompleteScore(it) }
        .sorted()
        .let { it[it.size / 2] }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day10_test")
    check(part1(testInput) == 26397)
    check(part2(testInput) == 288957L)

    val input = readInput("Day10")
    println(part1(input))
    println(part2(input))
}
