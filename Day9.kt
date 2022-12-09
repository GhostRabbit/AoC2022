import org.testng.Assert.assertEquals
import org.testng.annotations.Test
import kotlin.math.abs

object Day9 {
    class Tests {
        @Test
        fun part1() = assertEquals(part1("inputs/day9_example.txt"), 13)

        @Test
        fun part2e1() = assertEquals(part2("inputs/day9_example.txt"), 1)

        @Test
        fun part2e2() = assertEquals(part2("inputs/day9_example2.txt"), 36)
    }

    class Solutions {
        @Test
        fun part1() = Timer.measure { println("Tail moves: ${part1("inputs/day9.txt")}") }

        @Test
        fun part2() = Timer.measure { println("Tail moves: ${part2("inputs/day9.txt")}") }
    }

    class Rope() {
        lateinit var rope: MutableList<Pair<Int, Int>>
        val tailPositions = mutableSetOf(Pair(0, 0))

        constructor(length: Int) : this() {
            rope = MutableList(length) { Pair(0, 0) }
        }

        fun move(dir: String, steps: Int) {
            when (dir) {
                "R" -> repeat(steps) { move(0, 1) }

                "L" -> repeat(steps) { move(0, -1) }

                "U" -> repeat(steps) { move(1, 0) }

                "D" -> repeat(steps) { move(-1, 0) }
            }
        }

        private fun move(dy: Int, dx: Int) {
            rope[0] = Pair(rope[0].first + dy, rope[0].second + dx)
            follow()
        }

        private fun follow() {
            for (i in 1 until rope.size) {
                val a = rope[i - 1]
                val b = rope[i]

                val upDown = abs(a.first - b.first) == 2
                val leftRight = abs(a.second - b.second) == 2

                if (upDown && leftRight) moveSection(i, (a.first + b.first) / 2, (a.second + b.second) / 2)
                else if (upDown) moveSection(i, (a.first + b.first) / 2, a.second)
                else if (leftRight) moveSection(i, a.first, (a.second + b.second) / 2)
            }
        }

        private fun moveSection(i: Int, y: Int, x: Int) {
            rope[i] = Pair(y, x)
            if (i == rope.size - 1) tailPositions.add(rope[i])
        }
    }

    fun part1(fileName: String): Int {
        val rope = Rope(2)
        Inputs.readString(fileName).lines()
            .map { it.split(' ') }
            .forEach { rope.move(it[0], it[1].toInt()) }
        return rope.tailPositions.count()
    }

    fun part2(fileName: String): Int {
        val rope = Rope(10)
        Inputs.readString(fileName).lines()
            .map { it.split(' ') }
            .forEach { rope.move(it[0], it[1].toInt()) }
        return rope.tailPositions.count()
    }
}