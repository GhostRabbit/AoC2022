import org.testng.Assert.assertEquals
import org.testng.annotations.Test

object Day3 {
    class Tests {
        @Test
        fun part1() {
            assertEquals(part1("inputs/day3_example.txt"), 157)
        }

        @Test
        fun part2() {
            assertEquals(part2("inputs/day3_example.txt"), 70)
        }
    }

    class Solutions {
        @Test
        fun part1() {
            Timer.measure {
                println("Total priorities for leftover items: ${part1("inputs/day3.txt")}")
            }
        }

        @Test
        fun part2() {
            Timer.measure {
                println("Total priorities for badges: ${part2("inputs/day3.txt")}")
            }
        }
    }

    fun part1(fileName: String): Int {
        return Inputs.readLines(fileName)
            .map { Pair(it.substring(0, it.length / 2), it.substring(it.length / 2)) }
            .map { findCommon(it) }
            .map { score(it) }
            .sum()
    }

    private fun score(item: Char): Int {
        return if (item.isLowerCase()) item - 'a' + 1 else item - 'A' + 27
    }

    private fun findCommon(rucksack: Pair<String, String>): Char {
        return rucksack.first.find { rucksack.second.contains(it) }!!
    }

    private fun findCommon(elfs: List<String>): Char {
        return elfs[0].find { elfs[1].contains(it) && elfs[2].contains(it) }!!
    }

    fun part2(fileName: String): Int {
        return Inputs.readLines(fileName)
            .chunked(3)
            .map { findCommon(it) }
            .map { score(it) }
            .sum()
    }
}
