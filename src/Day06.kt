fun main() {

    fun parsePopulation(input: List<String>) = input
        .single()
        .split(',')
        .map { it.toInt() }
        .fold(LongArray(9)) { population, incidence -> population.also { it[incidence]++ }}

    fun nextDay(population: LongArray) {
        val births = population[0]
        population.indices
            .drop(1)
            .forEach { population[it - 1] = population[it] }
        population[6] += births
        population[8] = births
    }

    fun grow(days: Int, population: LongArray): Long {
        repeat(days) {
            nextDay(population)
        }
        return population.sum()
    }

    fun part1(input: List<String>) = grow(80, parsePopulation(input))
    fun part2(input: List<String>) = grow(256, parsePopulation(input))

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    check(part1(testInput) == 5934L)
    check(part2(testInput) == 26984457539)

    val input = readInput("Day06")
    println(part1(input))
    println(part2(input))
}
