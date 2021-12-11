package day11

import readInput

private val input = readInput("day11/Day11")

fun main() {
    val game = Game(input)
    println(game)
    println()
    game.raiseEnergy()
    println(game)
}

class Game(input: List<String>) {

    private val values: Array<Array<Int>> = Array(10) { y ->
        Array(10) { x -> input[y][x].toString().toInt()}
    }

    override fun toString(): String = values.joinToString ("\n") { line ->
        line.joinToString (" ") { it.toString()  }
    }

    fun raiseEnergy() {
        for (y in 0..9){
            for (x in 0..9){
                values[y][x] = values[y][x] + 1
            }
        }
    }

}

