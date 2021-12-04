package day03

import readInput


val input = readInput("day03/Day03")

fun main() {
    println(part1(input))
    println(part2(input))
}

private fun part1(input: List<String>): Int {
    val size = input.size
    val colSize = input[0].length
    val gamma = mutableListOf<Char>()
    val epsilon = mutableListOf<Char>()
    for( c in 0 until colSize){
        val one = input.count { it[c] == '1' } > (size/2)
        gamma.add(if(one) '1' else '0')
        epsilon.add(if(one) '0' else '1')
    }
    return gamma.joinToString(separator = "").toInt(2) * epsilon.joinToString(separator = "").toInt(2)
}

private fun part2(input: List<String>): Int {
    val oxygen = input.filterInputs(0) {ones, zeros -> if (zeros.size > ones.size) zeros else ones}.first().toInt(2)
    val scrubber = input.filterInputs(0){ones, zeros -> if (zeros.size > ones.size) ones else zeros}.first().toInt(2)
    return scrubber * oxygen
}

private fun List<String>.filterInputs(pos: Int = 0, filterBlock: (List<String>, List<String>) -> List<String>): List<String> {
    if (size == 1) return this
    val ones = filter { it[pos] == '1'}
    val zeros = filter { it[pos] == '0'}
    val keep = filterBlock(ones,zeros)
    return keep.filterInputs(pos+1, filterBlock)
}
