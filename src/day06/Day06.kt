package day06

import readInput

val fish = readInput("day06/Day06").first().split(",").map { it.toInt() }

fun main() {
    val fishState = FishState(fish)
    (1..256).forEach {
        fishState.nextDay()
        if (it == 80)
            println(fishState.total())
    }
    println(fishState.total())
}

class FishState(fish: List<Int>) {

    val fishPerDay = LongArray(9){0L}

    init {
        (0..8).forEach { idx ->
            fishPerDay[idx] = fish.count { it == idx }.toLong()
        }
    }

    fun nextDay() {
        val zeroDayFish = fishPerDay[0]
        (0..7).forEach {
            fishPerDay[it] = fishPerDay[it + 1] + if (it == 6) zeroDayFish else 0
        }
        fishPerDay[8] = zeroDayFish
    }

    fun total(): Long = fishPerDay.sum()

}

