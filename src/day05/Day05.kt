package day05

import readInput
import kotlin.math.absoluteValue

val winds: List<Wind> = readInput("day05/Day05").map { line ->
    val (x0,y0,x1,y1) = line.replace(" -> ", ",").split(",").map { it.toInt() }
    Wind(Point(x0,y0), Point(x1,y1))
}

fun main() {
    println(count(winds.filter { it.isVertical() || it.isHorizontal() }))
    println(count(winds))
}

fun count(winds: List<Wind>): Int = winds
    .flatMap { it.toPoints() }
    .fold(mutableMapOf<Point, Int>()) { acc, point -> acc[point] = acc.getOrDefault(point, 0) + 1; acc }
    .values.count { it > 1 }

data class Wind(val from: Point, val to: Point) {
    fun isVertical()    = from.x == to.x
    fun isHorizontal()  = from.y == to.y
    fun size()          = if (isHorizontal()) (to.x - from.x).absoluteValue else (to.y - from.y).absoluteValue

    fun toPoints(): List<Point> {
        val dx = direction(from.x, to.x)
        val dy = direction(from.y, to.y)
        return (0..size()).map { Point(from.x + it * dx, from.y + it * dy) }
    }

    private fun direction(from: Int, to: Int) = when {
            from > to -> -1
            from < to -> 1
            else -> 0
        }
}
data class Point(val x: Int, val y: Int)
