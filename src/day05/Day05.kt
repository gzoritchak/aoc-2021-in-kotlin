package day05

import readInput
import java.lang.Integer.max
import java.lang.Integer.min
import kotlin.math.absoluteValue

fun main() {
    println(count(readInput("day05/Day05")
        .toWinds()
        .filter { it.isVertical() || it.isHorizontal() }
    ))
    println(count(readInput("day05/Day05")
        .toWinds()
    ))
}

fun count(winds: List<Wind>): Int {
    val count = mutableMapOf<Point, Int>()
    winds
        .flatMap { it.toPoints() }
        .forEach {
            val previous = count.getOrDefault(it, 0)
            count[it] = previous + 1
        }
    return count.values.count { it>1 }
}

data class Point(val x: Int, val y: Int)

data class Wind(val from: Point, val to: Point) {
    fun isVertical()    = from.x == to.x
    fun isHorizontal()  = from.y == to.y

    fun toPoints(): List<Point> = when {
        isHorizontal()  -> { (min(from.x, to.x)..max(from.x, to.x)).map { Point(it, from.y) } }
        isVertical()    -> { (min(from.y, to.y)..max(from.y, to.y)).map { Point(from.x, it) } }
        else            -> {
            val dx = direction(from.x, to.x)
            val dy = direction(from.y, to.y)
            (0..(from.x - to.x).absoluteValue).map {
                Point(from.x + it*dx, from.y + it*dy)
            }
        }
    }

    private fun direction(from: Int, to: Int): Int =
        when {
            from > to -> -1
            from < to -> 1
            else -> 0
        }

}

const val delimiter = "->"

private fun  List<String>.toWinds(): List<Wind> = map {
    val (x0, y0) = it.substringBefore(delimiter).trim().split(",").map { it.toInt() }
    val (x1, y1) = it.substringAfter(delimiter).trim().split(",").map { it.toInt() }
    Wind(Point(x0,y0), Point(x1,y1))
}
