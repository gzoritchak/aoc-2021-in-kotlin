package day12

import readInput

private val input = readInput("day12/Day12")

typealias Cave = String

val cavesBig = mutableMapOf<String, Boolean>()
val cavesLinks = mutableMapOf<String, MutableList<String>>()

val Cave.isBig:Boolean
    get() = cavesBig[this]!!

fun Cave.links(): List<Cave> = cavesLinks[this]!!

fun parseInput(lines: List<String>) {
    lines.forEach { line ->
        val (name1, name2) = line.split("-")
        cavesBig.getOrPut(name1) { name1.all { it.isUpperCase() } }
        cavesBig.getOrPut(name2) { name2.all { it.isUpperCase() } }
        cavesLinks.getOrPut(name1) { mutableListOf() }.add(name2)
        cavesLinks.getOrPut(name2) { mutableListOf() }.add(name1)
    }
}

typealias Path = List<Cave>


fun main() {
    parseInput(input)
    val allPaths = mutableListOf<Path>()
    explorePaths(listOf("start"),allPaths)
    val endedPath = allPaths.filter { it.last() == "end" }
    endedPath.forEach { println(it) }
    println(endedPath.size)
}

fun explorePaths(currentPath:Path = listOf(), allPaths: MutableList<Path> = mutableListOf()){
    val last = currentPath.last()
    val newPath = last.links()
        .filter { it.isBig || currentPath.contains(it).not() }
        .map { currentPath + it }

    if (newPath.isEmpty() ) {
        allPaths.add(currentPath)
        return
    }
    val (ended, toExplore) = newPath.partition { it.last() == "end" }

    allPaths.addAll(ended)
    toExplore.forEach {
        explorePaths(it, allPaths)
    }

}

