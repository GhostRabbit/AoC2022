import org.testng.Assert.assertEquals
import org.testng.annotations.Test
import kotlin.math.max
import kotlin.math.min

object Day1 {
    class Tests {
        @Test
        fun part1() {
            assertEquals(part1("inputs/day1_example.txt"), 24000)
        }

        @Test
        fun part2() {
            assertEquals(part2("inputs/day1_example.txt"), 45000)
        }
    }

    class Solutions {
        @Test
        fun part1() {
            Timer.measure {
                println("Max calories on elf: ${part1("inputs/day1.txt")}")
            }
        }

        @Test
        fun part2() {
            Timer.measure {
                println("Total top 3 calories on elfs: ${part2("inputs/day1.txt")}")
            }
        }
    }

    fun part1(fileName: String): Int {
        return findLoadedElfs(fileName, listOf(0)).sum()
    }

    fun part2(fileName: String): Int {
        return findLoadedElfs(fileName, listOf(0, 0, 0)).sum()
    }

    private fun findLoadedElfs(fileName: String, elfs: List<Int>): List<Int> {
        var maxElfs = elfs
        val lines = Inputs.readLines(fileName)
        var current = 0

        lines.forEach {
            when (it) {
                "" -> {
                    maxElfs = pushMax(maxElfs, current)
                    current = 0
                }

                else -> current += it.toInt()
            }
        }
        return pushMax(maxElfs, current)
    }

    private fun pushMax(maxes: List<Int>, value: Int): List<Int> {
        var last: Int = value
        return maxes.map {
            val r: Int = max(it, last)
            last = min(it, last)
            r
        }
    }
}
