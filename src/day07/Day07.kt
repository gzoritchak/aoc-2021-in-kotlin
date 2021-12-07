package day07

import readInput
import kotlin.math.absoluteValue

val input = readInput("day07/Day07").first().split(",").map { it.toInt() }.sorted()

fun main() {
    println("Day 07:: ${input.sum() / input.size}  $input")
    val min = (input.first()..input.last()).minOf { i->
        input.sumOf { (i - it).absoluteValue }
    }
    println(min)
}
