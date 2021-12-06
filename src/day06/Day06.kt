package day06

import readInput

val fish = readInput("day06/Day06Test").first().split(",").map { it.toInt() }

fun main() {
    var fishState = fish
    (1..18).forEach {
        fishState = nextDay(fishState)
    }
    println(fishState.size)
}

fun nextDay(fishState: List<Int>): List<Int> {
    val newFish = mutableListOf<Int>()
    return fishState.map {
        if (it > 0)
            it -1
        else {
            newFish.add(8)
            6
        }
    } + newFish
}

