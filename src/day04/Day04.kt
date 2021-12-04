package day04

import readInput

val lineRegex = """\s+""".toRegex()
private fun String.toInts() = this.trim().split(lineRegex).map { it.toInt() }

fun main() {
    println(part1(readInput("day04/Day04").toGame()))
    println(part2(readInput("day04/Day04").toGame()))
}

private fun part2(game: Game): Int {
    val unwinBoards = game.boards.toMutableList()
    var countNumbers = 5
    while(true){
        val currentNumbers = game.randoms.take(countNumbers++)
        val winboards = unwinBoards.filter { it.win(currentNumbers) }
        unwinBoards.removeAll(winboards)
        if (unwinBoards.size == 0){
            return boardScore(winboards.last(), currentNumbers)
        }
    }
}

private fun part1(game: Game): Int {
    var countNumbers = 5
    while(true){
        val currentNumbers = game.randoms.take(countNumbers++)
        game.boards
            .firstOrNull { it.win(currentNumbers) }
            ?.let{ board ->
                return boardScore(board, currentNumbers)
            }
    }
}

private fun boardScore(board: Board, currentNumbers: List<Int>): Int {
    val unmarkedNumbers = board
        .lines()
        .flatten()
        .filter { currentNumbers.contains(it).not() }
        .sum()
    return currentNumbers.last() * unmarkedNumbers
}

private fun Board.win(currentNumbers: List<Int>) =
    lines().any { line -> line.all { lineNumber -> currentNumbers.contains(lineNumber) } } ||
            columns().any { column -> column.all { columnNumber -> currentNumbers.contains(columnNumber) } }


typealias Line = List<Int>
typealias Column = List<Int>

var boardIds = 1

class Board(val lines: List<Line>, val id: Int = boardIds++) {
    fun lines(): List<Line> = lines
    fun columns(): List<Column> = (0..4).map { col -> (0..4).map { lines[it][col] } }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as Board
        if (id != other.id) return false
        return true
    }

    override fun hashCode(): Int {
        return id
    }

}

data class Game(
    val randoms: List<Int>,
    val boards: List<Board>)

private fun  List<String>.toGame(): Game {
    val randoms = this[0].split(',').map { it.toInt() }
    var boardLine = 2
    val boards: MutableList<Board> = mutableListOf()
    while (boardLine < this.size){
        boards.add(readBoard(boardLine, this))
        boardLine += 6
    }
    return Game(randoms, boards)
}

fun readBoard(startLine: Int, list: List<String>): Board = Board((0..4)
    .map { list[startLine + it].toInts() })
