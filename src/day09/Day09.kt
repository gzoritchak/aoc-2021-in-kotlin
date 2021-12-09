package day09

import readInput

val input = readInput("day09/Day09")

fun main() {
    val board = Board(input.heights())
    println(board.sumMin())
    println(board.allBasins()
        .sortedByDescending { it.size }
        .take(3)
        .fold(1) { acc, points -> acc * points.size }
    )
}

data class Point(val x:Int, val y: Int)

class Board(val points:List<List<Int>>) {

    val height = points.size
    val width = points[0].size

    fun Point.adjacents(): List<Point> {
        val adjacents = mutableListOf<Point>()
        if (x > 0)          adjacents.add(Point(x-1,y))
        if (y > 0)          adjacents.add(Point(x,y-1))
        if (x < width-1)    adjacents.add(Point(x+1,y))
        if (y < height-1)   adjacents.add(Point(x,y+1))
        return adjacents
    }

    fun Point.height(): Int = points[y][x]

    fun sumMin() = minPoints().sumOf { it.height() + 1  }

    fun minPoints() : List<Point> {
        val mins = mutableListOf<Point>()
        for (x in 0 until width) {
            for (y in 0 until height) {
                val currentPoint = Point(x,y)
                if (  currentPoint.adjacents().all { it.height() > currentPoint.height()})
                    mins.add(currentPoint)
            }
        }
        return mins
    }

    fun allBasins(): List<Set<Point>> = minPoints().map { collectBasin(setOf(it), setOf()) }

    fun collectBasin(fromPoints:Set<Point>, alreadyVisited: Set<Point> ):Set<Point> {
        if (fromPoints.isEmpty())
            return alreadyVisited
        val toVisit: MutableSet<Point> = fromPoints
            .fold(mutableSetOf()) { acc, point -> acc.addAll(
                point.adjacents().filter {
                    alreadyVisited.contains(it).not() &&
                    point.height() < it.height() &&
                    it.height() < 9
                }
            )
            acc
        }
        return collectBasin(toVisit, alreadyVisited + fromPoints)

    }

}


private fun List<String>.heights(): List<List<Int>> = map { it.toList().map { it.toString().toInt() } }

