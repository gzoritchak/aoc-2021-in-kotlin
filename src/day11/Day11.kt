package day11

import readInput

private val input = readInput("day11/Day11")

fun main() {
    val game = Game(input)
    println(game)

    var step = 0
    while (true) {
        val stepFlash = game.nextStep()
        println()
        println(game)
        if(step++ == 100)
            println(game.totalFlash)
        if (stepFlash == 100){
            println(step)
            return
        }
    }
}


class Game(input: List<String>) {

    var totalFlash = 0

    val points = mutableMapOf<Point, Int>()

    init {
        for (y in 0..9) {
            for (x in 0..9) {
                val value = input[y][x].toString().toInt()
                points[Point(x, y)] = value
            }
        }
    }

    data class Point(val x: Int, val y: Int) {
        fun adjacents(): List<Point> {
            val dx = (x - 1).coerceAtLeast(0)..(x + 1).coerceAtMost(9)
            val dy = (y - 1).coerceAtLeast(0)..(y + 1).coerceAtMost(9)
            val points = hashSetOf<Point>()
            for (newX in dx)
                for (newY in dy)
                    points.add(Point(newX, newY))
            return (points - this).toList()
        }
    }

    var Point.energy: Int
        get() = points[Point(x, y)]!!
        set(value) {
            points[Point(x, y)] = value
        }

    override fun toString(): String =
        (0..9).joinToString("\n") { y ->
            (0..9).joinToString(" ") { x ->
                points[Point(x, y)].toString()
            }
        }

    fun nextStep(): Int {
        points.keys.forEach { it.raiseEnergy() }
        var flash = collectFlash()
        var newFlash = flash
        while (newFlash.isNotEmpty()) {
            val adjacents = newFlash
                .fold(mutableListOf<Point>()) { acc, point -> acc.addAll(point.adjacents()); acc }
            adjacents.forEach { it.raiseEnergy() }
            newFlash = collectFlash() - flash
            flash = flash + newFlash
        }
        flash.forEach{ it.energy = 0}
        totalFlash += flash.size
        return flash.size
    }

    private fun collectFlash() = points.entries
        .filter { it.value > 9 }
        .map { it.key }.toSet()

    fun Point.raiseEnergy() {
        this.energy = this.energy +1
    }

}

