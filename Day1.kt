import org.testng.Assert.assertEquals
import org.testng.annotations.Test
import kotlin.math.max
import kotlin.math.min


class Tests {
    @Test
    fun day1part1() {
        assertEquals(Day1.part1("inputs/input_d1_example.txt"), 24000)
    }

    @Test
    fun day1part2() {
        assertEquals(Day1.part2("inputs/input_d1_example.txt"), 45000)
    }
}

class Solutions {
    @Test
    fun day1part1() {
        Timer.measure {
            println("Max calories on single elf: ${Day1.part1("inputs/input_d1.txt")}")
        }
    }

    @Test
    fun day1part2() {
        Timer.measure {
            println("Total top 3 calories on elfs: ${Day1.part2("inputs/input_d1.txt")}")
        }
    }
}

object Day1 {
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
