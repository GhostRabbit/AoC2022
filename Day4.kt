import org.testng.Assert.assertEquals
import org.testng.annotations.Test

object Day4 {
    class Tests {
        @Test
        fun part1() = assertEquals(part1("inputs/day4_example.txt"), 2)

        @Test
        fun part2() = assertEquals(part2("inputs/day4_example.txt"), 4)
    }

    class Solutions {
        @Test
        fun part1() = Timer.measure { println("Total fully contained assignments: ${part1("inputs/day4.txt")}") }

        @Test
        fun part2() = Timer.measure { println("Total overlapping assignments: ${part2("inputs/day4.txt")}") }
    }

    fun part1(fileName: String): Int {
        return Inputs.readLines(fileName)
            .map { assignmentsOf(it) }
            .map { countContained(it) }
            .sum()
    }

    fun part2(fileName: String): Int {
        return Inputs.readLines(fileName)
            .map { assignmentsOf(it) }
            .map { countOverLapping(it) }
            .sum()
    }


    private fun countContained(assignments: List<Pair<Int, Int>>): Int =
        if (assignments[0] isContainedByOrContains assignments[1]) 1 else 0

    private infix fun Pair<Int, Int>.isContainedByOrContains(that: Pair<Int, Int>): Boolean =
        this isContainedBy that || that isContainedBy this

    private infix fun Pair<Int, Int>.isContainedBy(that: Pair<Int, Int>) =
        this.first <= that.first && this.second >= that.second

    private fun countOverLapping(assignments: List<Pair<Int, Int>>) =
        if (assignments[0] isOverlapping assignments[1]) 1 else 0

    private infix fun Pair<Int, Int>.isOverlapping(that: Pair<Int, Int>): Boolean =
        this overlaps that || that overlaps this

    private infix fun Pair<Int, Int>.overlaps(that: Pair<Int, Int>): Boolean =
        this.first in that.first..that.second || this.second in that.first..that.second

    private fun assignmentsOf(line: String): List<Pair<Int, Int>> {
        return line
            .split(",", "-")
            .chunked(2)
            .map { Pair(it[0].toInt(), it[1].toInt()) }
    }
}


