package day10

import readInput

private val input = readInput("day10/Day10")

fun main() {

    val parseResults = input
        .map { Parser(it).parse() }
    println(parseResults.sumOf { it.corruptedScore })

    val incompleteScore = parseResults
        .filter { it.incompleteScore > 0 }
        .map { it.incompleteScore }
        .sorted()

    val middleIndex = (incompleteScore.size - 1) / 2
    println(incompleteScore[middleIndex])
}

abstract class Node {
    val children = mutableListOf<Node>()
    fun addNode(node: Node): Node {
        children.add(node)
        return node
    }
}

class RootNode: Node()
class BlockNode(val type:Char):Node()

class Parser(val tokens:String) {

    private var parseResult = ParseResult()

    private var idx = 0
    fun nextToken() { idx++ }
    fun token(): Char? = if (idx < tokens.length) tokens[idx] else null

    fun parse():ParseResult{
        val node = RootNode()
        try {
            chunksBlock(node)
        } catch (_:IllegalStateException){}
        return parseResult
    }

    private fun chunksBlock(node: Node) {
        while (listOf('(', '{', '[', '<').contains(token())) {
            when (token()) {
                '(' -> chunkBlock(node, ')')
                '[' -> chunkBlock(node, ']')
                '<' -> chunkBlock(node, '>')
                '{' -> chunkBlock(node, '}')
            }
        }
    }

    private fun chunkBlock(parent: Node, type: Char) {
        val node = parent.addNode(BlockNode(type))
        nextToken()
        chunksBlock(node)
        consume(type)
    }

    fun consume(tokenType: Char) = when (val token = token()) {
        tokenType -> nextToken()
        null -> parseResult = parseResult.copy(incompleteScore = 5 * parseResult.incompleteScore + tokenType.incompleteScore())
        else -> {
            parseResult = parseResult.copy(corruptedScore = parseResult.corruptedScore + token.corruptedScore())
            error("Corrupted line")
        }
    }
}

data class ParseResult(
    val corruptedScore: Int = 0,
    val incompleteScore: Long = 0,
)

fun Char.corruptedScore(): Int = when(this) {
    ')' -> 3
    ']' -> 57
    '}' -> 1197
    '>' -> 25137
    else -> 0
}

fun Char.incompleteScore(): Int = when(this) {
    ')' -> 1
    ']' -> 2
    '}' -> 3
    '>' -> 4
    else -> 0
}
