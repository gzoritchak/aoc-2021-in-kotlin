package day13

import readInput

private val input = readInput("day13/Day13")

fun main() {
    val (foldLines, pointLines) = input
        .filter { it.isNotBlank() }
        .partition { it.startsWith("fold along ") }

    val points = pointLines.toPoints()
    points.log()

    val folds = foldLines.toFolds()
    val fold1  = folds.first()

    val points1 = points.map { it + fold1 }.toSet()
    println(points1.size)

    val foldedPoints = folds.fold(points) { acc, fold ->
        acc.map { point ->
            point + fold
        }.toSet()
    }
    foldedPoints.log()
}

private operator fun Point.plus(fold: Fold): Point = when(fold.on){
    Axe.x -> copy( x = if (x > fold.distance)  fold.distance - (x - fold.distance) else x)
    Axe.y -> copy( y = if (y > fold.distance)  fold.distance - (y - fold.distance) else y)
}

// ----------------- model -----------------------
data class Point(val x: Int, val y: Int)
data class Fold(val on: Axe, val distance: Int)
enum class Axe { x, y }


// ----------------- debug -----------------------
fun Collection<Point>.log() {
    val x1 = this.maxOf { it.x }
    val y1 = this.maxOf { it.y }
    val debugDescription = (0..y1).joinToString("\n") { y ->
        (0..x1).joinToString(" ") { x ->
            if (this.contains(Point(x, y))) "#" else "-"
        }
    }
    println()
    println(debugDescription)
}

// ----------------- load inputs -----------------
val regex = """fold along (.)=(.+)""".toRegex()
private fun List<String>.toFolds(): List<Fold> = map { regex.matchEntire(it)!!.groupValues.run { Fold(Axe.valueOf(get(1)), get(2).toInt()) } }
private fun List<String>.toPoints(): Set<Point> = map { it.split(",").run { Point(get(0).toInt(), get(1).toInt()) } }.toSet()
