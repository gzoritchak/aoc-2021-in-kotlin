package day07

import readInput
import kotlin.math.absoluteValue

val sortedInput = readInput("day07/Day07").first().split(",").map { it.toInt() }.sorted()

fun main() {
    println( sortedInput.minimizeCost { it })
    println( sortedInput.minimizeCost { it.arithmeticProgression()})
}

fun List<Int>.minimizeCost(costFunction: (Int)->Int):Int =
    (first()..last())
    .minOf { i-> sortedInput.sumOf { costFunction((i - it).absoluteValue) }}

fun Int.arithmeticProgression():Int =  (this + 1) * this /2