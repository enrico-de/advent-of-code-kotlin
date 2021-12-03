fun main() {

    fun move1(location: Location, cmd: Command) = with(location) {
        when (cmd.dir) {
            Direction.forward -> Location(pos + cmd.x, dep, aim)
            Direction.down -> Location(pos, dep + cmd.x, aim)
            Direction.up -> Location(pos, dep - cmd.x, aim)
        }
    }
    fun move2(location: Location, cmd: Command) = with(location) {
        when (cmd.dir) {
            Direction.forward -> Location(pos + cmd.x, dep + aim * cmd.x, aim)
            Direction.down -> Location(pos, dep, aim + cmd.x)
            Direction.up -> Location(pos, dep, aim - cmd.x)
        }
    }

    fun parse(command: String) = command.split(" ").let { Command(Direction.valueOf(it[0]), it[1].toInt()) }

    fun part1(input: List<String>) = input.map { parse(it) }.fold(Location(0, 0, 0), ::move1).run { pos * dep }
    fun part2(input: List<String>) = input.map { parse(it) }.fold(Location(0, 0, 0), ::move2).run { pos * dep }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 150)

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}

enum class Direction { forward, down, up }
data class Command(val dir: Direction, val x: Int)
data class Location(val pos: Int, val dep: Int, val aim: Int)
