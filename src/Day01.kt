fun main() {
    fun part1(input: List<String>): Int {
        val depths = input.map { it.toInt() }
        return (1 until depths.size)
            .count { depths[it] > depths[it-1] }
    }

    fun part2(input: List<String>): Int {
        val depths = input.map { it.toInt() }
        val windows = (2 until input.size)
            .map { depths[it-2] + depths[it-1] + depths[it] }
        return (1 until windows.size)
            .count { windows[it] > windows[it-1] }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 7)
    check(part2(testInput) == 5)

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
