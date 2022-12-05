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
            .count { it[0] isContainedByOrContains it[1] }
    }

    fun part2(fileName: String): Int {
        return Inputs.readLines(fileName)
            .map { assignmentsOf(it) }
            .count { it[0] isOverlapping it[1] }
    }

    private infix fun IntRange.isContainedByOrContains(that: IntRange): Boolean =
        this isContainedBy that || that isContainedBy this

    private infix fun IntRange.isContainedBy(that: IntRange) =
        this.first <= that.first && this.last >= that.last

    private infix fun IntRange.isOverlapping(that: IntRange): Boolean =
        this overlaps that || that overlaps this

    private infix fun IntRange.overlaps(that: IntRange): Boolean =
        this.first in that || this.last in that

    private fun assignmentsOf(line: String): List<IntRange> {
        return line
            .split(",", "-")
            .chunked(2)
            .map { it[0].toInt() .. it[1].toInt() }
    }
}


