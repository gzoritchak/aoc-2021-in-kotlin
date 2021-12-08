package day08

import readInput
import java.util.*

val input = readInput("day08/Day08").toInput()

private fun  List<String>.toInput(): List<Pair<List<String>,List<String>>> = map {
    val (s0, s1) = it.split(" | ")
    Pair(
        s0.split(" "),
        s1.split(" "),
    )
}

fun main() {
    val count = input.sumOf { line ->
        line.second.count {
                    it.length == 2 ||
                    it.length == 3 ||
                    it.length == 4 ||
                    it.length == 7
        }
    }
    println(count)

    val sum = input.sumOf { line ->
        val dictionary = line.first.dictionary()
        line.second
            .map { dictionary.getValue(it.toSortedSet()) }
            .joinToString("")
            .toInt()
    }
    println(sum)
}

fun List<String>.dictionary(): Map<SortedSet<Char>, Int> {

    val chars: List<SortedSet<Char>> = map { it.toSortedSet() }

    val one = chars.first { it.size == 2 }
    val seven = chars.first { it.size == 3 }
    val four = chars.first { it.size == 4 }
    val eight = chars.first { it.size == 7 }
    val three = chars.first { it.size == 5 && it.containsAll(one) }

    val cornerLeftBottom = eight - (seven + four)

    val two = chars.first { it.size == 5 && it.containsAll(cornerLeftBottom) }
    val five = chars.first { it.size == 5 && it != two && it != three }
    val nine = chars.first { it.size == 6 && it.containsAll(cornerLeftBottom).not() }
    val zero = chars.first { it.size == 6 && it.containsAll(one) && it != nine }
    val six = chars.first { it.size == 6 && it != zero && it != nine }

    return mapOf(
        zero    to 0,
        one     to 1,
        two     to 2,
        three   to 3,
        four    to 4,
        five    to 5,
        six     to 6,
        seven   to 7,
        eight   to 8,
        nine    to 9
    )
}

//     0:         1:          2:          3:          4:
//    aaaa       ....        aaaa        aaaa        ....
//   b    c     .    c      .    c      .    c      b    c
//   b    c     .    c      .    c      .    c      b    c
//    ....       ....        dddd        dddd        dddd
//   e    f     .    f      e    .      .    f      .    f
//   e    f     .    f      e    .      .    f      .    f
//    gggg       ....        gggg        gggg        ....
//
//     5:         6:          7:          8:          9:
//    aaaa       aaaa        aaaa        aaaa        aaaa
//   b    .     b    .      .    c      b    c      b    c
//   b    .     b    .      .    c      b    c      b    c
//    dddd       dddd        ....        dddd        dddd
//   .    f     e    f      .    f      e    f      .    f
//   .    f     e    f      .    f      e    f      .    f
//    gggg       gggg        ....        gggg        gggg


