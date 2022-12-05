import org.testng.Assert.assertEquals
import org.testng.annotations.Test
import java.util.stream.Collectors

object Day5 {
    class Tests {
        @Test
        fun part1() = assertEquals(part1("inputs/day5_example.txt"), "CMZ")

        @Test
        fun part2() = assertEquals(part2("inputs/day5_example.txt"), "MCD")
    }

    class Solutions {
        @Test
        fun part1() = Timer.measure { println("Crates on top: ${part1("inputs/day5.txt")}") }

        @Test
        fun part2() = Timer.measure { println("New crates on top: ${part2("inputs/day5.txt")}") }
    }

    fun part1(fileName: String): String {
        val inputParts = Inputs.readString(fileName).split("\r\n\r\n")
        val stacks = parseStacksAndCrates(inputParts[0].lines())
        executeMoves(inputParts[1].lines()) { (count, from, to) ->
            repeat(count) {
                stacks[from] moveOneCrateTo stacks[to]
            }
        }
        return readTopCrates(stacks)
    }

    private fun readTopCrates(stacks: List<ArrayDeque<Char>>): String =
        stacks.stream().skip(1).map { it.removeLast().toString() }.collect(Collectors.joining())

    private infix fun ArrayDeque<Char>.moveOneCrateTo(that: ArrayDeque<Char>) {
        that.addLast(removeLast())
    }

    private fun parseStacksAndCrates(lines: List<String>): List<ArrayDeque<Char>> {
        // stack[0] not used, can be used as temp.
        val highest = lines.last()
            .split(' ')
            .last { it.isNotEmpty() }
            .toInt()
        val stacks = List(1 + highest) { ArrayDeque<Char>() }
        for (stackIndex in 1 until stacks.size) {
            for (lineIndex in (0..lines.size - 2).reversed()) {
                val crate = lines[lineIndex][4 * stackIndex - 3]
                if (crate.isLetter()) stacks[stackIndex].addLast(crate)
            }
        }
        return stacks
    }

    fun part2(fileName: String): String {
        val inputParts = Inputs.readString(fileName).split("\r\n\r\n")
        val stacks = parseStacksAndCrates(inputParts[0].lines())
        val temp = 0
        executeMoves(inputParts[1].lines()) { (count, from, to) ->
            repeat(count) {
                stacks[from] moveOneCrateTo stacks[temp]
            }
            while (stacks[temp].isNotEmpty()) {
                stacks[temp] moveOneCrateTo stacks[to]
            }
        }
        return readTopCrates(stacks)
    }

    data class Command(val count: Int, val from: Int, val to: Int)

    private fun executeMoves(moves: List<String>, action: (Command) -> Unit) {
        moves
            .map { it.split(' ') }
            .map { Command(it[1].toInt(), it[3].toInt(), it[5].toInt()) }
            .forEach { action(it) }
    }
}