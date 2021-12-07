package day07

import readInput
import kotlin.math.absoluteValue

val input = readInput("day07/Day07").first().split(",").map { it.toInt() }.sorted()

fun main() {
    println( input.minDistanceBy (::cost1))
    println( input.minDistanceBy (::cost2))
}

fun List<Int>.minDistanceBy(cost: (Int)->Int):Int =
    (first()..last())
        .minOf { i-> input.sumOf { cost(i - it) }}

fun cost1(distance: Int):Int = distance.absoluteValue
fun cost2(distance: Int):Int = distance.absoluteValue.let { (it + 1) * it/2 }
