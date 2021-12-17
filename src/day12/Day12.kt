package day12

import readInput

typealias Cave = String
typealias Path = List<Cave>


private class Day12(lines:List<String>){

    val cavesBig = mutableMapOf<String, Boolean>()
    val cavesLinks = mutableMapOf<String, MutableList<String>>()

    val Cave.isBig:Boolean
        get() = cavesBig[this]!!

    fun Cave.links(): List<Cave> =
        cavesLinks[this]!!


    init {
        lines.forEach { line ->
            val (name1, name2) = line.split("-")
            cavesBig.getOrPut(name1) { name1.all { it.isUpperCase() } }
            cavesBig.getOrPut(name2) { name2.all { it.isUpperCase() } }
            cavesLinks.getOrPut(name1) { mutableListOf() }.add(name2)
            cavesLinks.getOrPut(name2) { mutableListOf() }.add(name1)
        }
    }

    fun part1()= explorePaths2(listOf("start"), null)

    fun part2(): List<String> {
        val smallCaves = cavesLinks.keys.filter { it.isBig.not() && it != "start" && it != "end" }
        val allPaths = mutableSetOf<Path>()
        for (cave in smallCaves) {
            allPaths.addAll(explorePaths2(listOf("start"), cave, allPaths))
        }
        return allPaths.map { it.joinToString (separator = ",").trim() }
    }

    fun explorePaths2(currentPath:Path = listOf(), smallCaveTwice: Cave?, allPaths: MutableSet<Path> = mutableSetOf()): MutableSet<Path> {
        val last = currentPath.last()
        val newPath = last.links()
            .filter { caveToExplore: Cave ->
                val big = caveToExplore.isBig
                val count = currentPath.count { it == caveToExplore }
                big || (caveToExplore == smallCaveTwice && count < 2) || (caveToExplore != "start" && count == 0)
            }.map { currentPath + it }

        if (newPath.isEmpty() ) {
            if (currentPath.last() == "end")
                allPaths.add(currentPath)
            return allPaths
        }
        val (ended, toExplore) = newPath.partition { it.last() == "end" }
        allPaths.addAll(ended)
        toExplore.forEach {
            explorePaths2(it, smallCaveTwice, allPaths)
        }
        return allPaths
    }

}

fun main() {
    val part1 = Day12(readInput("day12/Day12")).part1()
    check(part1.size == 5178)

    val part2: List<String> = Day12(readInput("day12/Day12")).part2()
    check(part2.size == 130094)
    println(part2.size)
}
