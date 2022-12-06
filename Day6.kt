import org.testng.Assert.assertEquals
import org.testng.annotations.Test
import kotlin.streams.asSequence

object Day6 {
    class Tests {
        @Test
        fun part1() {
            assertEquals(findStart("mjqjpqmgbljsphdztnvjfqwrcgsmlb"), 7)
            assertEquals(findStart("bvwbjplbgvbhsrlpgdmjqwftvncz"), 5)
            assertEquals(findStart("nppdvjthqldpwncqszvftbrmjlhg"), 6)
            assertEquals(findStart("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg"), 10)
            assertEquals(findStart("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw"), 11)
        }

        @Test
        fun part2() {
            assertEquals(findStartOfMessage("mjqjpqmgbljsphdztnvjfqwrcgsmlb"), 19)
            assertEquals(findStartOfMessage("bvwbjplbgvbhsrlpgdmjqwftvncz"), 23)
            assertEquals(findStartOfMessage("nppdvjthqldpwncqszvftbrmjlhg"), 23)
            assertEquals(findStartOfMessage("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg"), 29)
            assertEquals(findStartOfMessage("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw"), 26)
        }
    }

    class Solutions {
        @Test
        fun part1() = Timer.measure { println("first marker after character: ${part1("inputs/day6.txt")}") }

        @Test
        fun part2() = Timer.measure { println("start-of-message after character: ${part2("inputs/day6.txt")}") }
    }

    fun part1(fileName: String): Int {
        return findStart(Inputs.readString(fileName))
    }

    private fun findStart(input: String): Int = findDistinctOfLength(input, 4)

    private fun findDistinctOfLength(input: String, n: Int): Int {
        var index = n
        while (input.substring(index - n, index).isNotDistinct()) index++
        return index
    }

    private fun findStartOfMessage(input: String): Int = findDistinctOfLength(input, 14)

    private fun String.isNotDistinct(): Boolean {
        return chars().asSequence().toSet().size != length
    }

    fun part2(fileName: String): Int {
        return findStartOfMessage(Inputs.readString(fileName))
    }
}

