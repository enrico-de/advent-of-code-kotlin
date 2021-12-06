import kotlin.math.sqrt

fun main() {

    class Board(val numbers: IntArray) {
        val dim = sqrt(numbers.size.toDouble()).toInt()
        val markers = BooleanArray(numbers.size)

        fun row(idx: Int) = markers.sliceArray(idx * dim until idx * dim + dim)
        val rows get() = (0 until dim).map { row(it) }

        fun col(idx: Int) = rows.map { it[idx] }.toBooleanArray()
        val columns get() = (0 until dim).map { col(it) }

        fun marked(array: BooleanArray) = array.all { it }
        fun won() = rows.any { marked(it) } || columns.any { marked(it) }

        fun mark(number: Int) {
            val idx = numbers.indexOf(number)
            if (idx >= 0)
                markers[idx] = true
        }

        fun sumUnmarked() = numbers.filterIndexed { idx, _ -> !markers[idx] }.sum()
    }

    fun getRandomNumbers(input: List<String>) = input[0].split(",").map { it.toInt() }
    fun getBoards(input: List<String>) = input
            .asSequence()
            .drop(1)
            .filter { it.isNotBlank() }
            .map { row -> row.trim().split(Regex("\\s+")).map { it.toInt() } }
            .windowed(5, 5)
            .map { Board(it.flatten().toIntArray()) }
            .toList()

    fun part1(input: List<String>): Int {
        val boards = getBoards(input)
        for (number in getRandomNumbers(input)) {
            boards.forEach { it.mark(number) }
            boards.find { it.won() }?.let { return number * it.sumUnmarked() }
        }
        return -1
    }

    fun part2(input: List<String>): Int {
        val boards = getBoards(input).toMutableList()
        for (number in getRandomNumbers(input)) {
            boards.forEach { it.mark(number) }
            if (boards.size == 1 && boards.single().won())
                return number * boards.single().sumUnmarked()
            boards.removeAll { it.won() }
        }
        return -1
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 4512)
    check(part2(testInput) == 1924)

    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}
