import org.testng.Assert.assertEquals
import org.testng.annotations.Test

object Day2 {
    class Tests {
        @Test
        fun part1() {
            assertEquals(part1("inputs/day2_example.txt"), 15)
            assertEquals(part1("inputs/day2_example.txt"), 15)
        }

        @Test
        fun part2() {
            assertEquals(part2("inputs/day2_example.txt"), 12)
        }
    }

    class Solutions {
        @Test
        fun part1() {
            Timer.measure {
                println("Total score: ${part1("inputs/day2.txt")}")
            }
        }

        @Test
        fun part2() {
            Timer.measure {
                println("New total score: ${part2("inputs/day2.txt")}")
            }
        }
    }

    fun part1(fileName: String): Int {
        return Inputs.readLines(fileName)
            .map { Pair(moveOf(it[0]), moveOf(it[2])) }
            .sumOf { scoreGame(it.first, it.second) }
    }

    fun part2(fileName: String): Int {
        return Inputs.readLines(fileName)
            .map { line ->
                val opponent: Move = moveOf(line[0])
                Pair(opponent, counter(opponent, line[2]))
            }
            .sumOf { scoreGame(it.first, it.second) }
    }

    private fun counter(r: Move, strategy: Char): Move {
        val offset = when (strategy) {
            'X' -> 2
            'Y' -> 0
            'Z' -> 1
            else -> throw IllegalArgumentException("Unknown input $strategy")
        }
        return winOrder[(winOrder.indexOf(r) + offset) % winOrder.size]
    }

    private fun moveOf(c: Char): Move {
        return when (c) {
            'A', 'X' -> Move.Rock
            'B', 'Y' -> Move.Paper
            'C', 'Z' -> Move.Scissors
            else -> throw IllegalArgumentException("Unknown input $c")
        }
    }

    enum class Move(val value: Int) {
        Rock(1), Paper(2), Scissors(3)
    }

    private val winOrder = listOf(Move.Rock, Move.Paper, Move.Scissors)

    private fun scoreGame(l: Move, r: Move): Int {
        return win(l, r) + draw(l, r) + r.value
    }

    private fun draw(l: Move, r: Move): Int {
        return if (l == r) 3 else 0
    }

    private fun win(l: Move, r: Move): Int {
        return if (winOrder.indexOf(l) == (winOrder.indexOf(r) + 2) % winOrder.size) 6 else 0
    }
}
