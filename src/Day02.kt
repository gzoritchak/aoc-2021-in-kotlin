fun main() {
    fun part1(input: List<Command>): Int = input
        .fold(SubmarineState()) { state, command -> state.applyCommandPart1(command)}
        .let { it.position.x * it.position.y }

    fun part2(input: List<Command>): Int = input
        .fold(SubmarineState()) { state, command -> state.applyCommandPart2(command)}
        .let { it.position.x * it.position.y }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test").map { it.toCommand() }

    check(part1(testInput) == 150)
    check(part2(testInput) == 900)

    val input = readInput("Day02").map { it.toCommand() }
    println(part1(input))
    println(part2(input))
}


fun SubmarineState.applyCommandPart1(command: Command): SubmarineState =
    when(command) {
        is Command.Forward  -> copy( position = position.copy(x = position.x + command.value))
        is Command.Down     -> copy( position = position.copy(y = position.y + command.value))
        is Command.Up       -> copy( position = position.copy(y = position.y - command.value))
    }

fun SubmarineState.applyCommandPart2(command: Command): SubmarineState =
    when(command) {
        is Command.Down     -> copy( aim = aim + command.value)
        is Command.Up       -> copy( aim = aim - command.value)
        is Command.Forward  -> copy( position = position.copy(
                x = position.x + command.value,
                y = position.y + aim * command.value
        )
        )

    }

/**
 * It increases your horizontal position by X units.
 * It increases your depth by your aim multiplied by X.
 */


data class SubmarineState(
    val aim: Int = 0,
    val position: Vector = Vector()
)

data class Vector(
    val x: Int = 0,
    val y: Int = 0
)

sealed class Command(val value: Int){
    class Forward(x:Int)    : Command(x)
    class Down(x:Int)       : Command(x)
    class Up(x:Int)         : Command(x)
}

private fun String.toCommand():Command {
    val (command, value) = this.split(" ")
    return when(command) {
        "forward"   -> Command.Forward(value.toInt())
        "down"      -> Command.Down(value.toInt())
        "up"        -> Command.Up(value.toInt())
        else        -> error("unknown $command")
    }
}
