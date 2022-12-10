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

    data class Pos(val y:Int, val x:Int)

    class Rope() {
        lateinit var knots: MutableList<Pos>
        val tailPositions = mutableSetOf(Pos(0, 0))

        constructor(length: Int) : this() {
            knots = MutableList(length) { Pos(0, 0) }
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
            knots[0] = Pos(knots.first().y + dy, knots.first().x + dx)
            follow()
        }

        private fun follow() {
            for (i in 1 until knots.size) {
                val a = knots[i - 1]
                val b = knots[i]

                val upDown = abs(a.y - b.y) == 2
                val leftRight = abs(a.x - b.x) == 2

                if (upDown && leftRight) moveKnot(i, (a.y + b.y) / 2, (a.x + b.x) / 2)
                else if (upDown) moveKnot(i, (a.y + b.y) / 2, a.x)
                else if (leftRight) moveKnot(i, a.y, (a.x + b.x) / 2)
                else break // No more movement
            }
        }

        private fun moveKnot(i: Int, y: Int, x: Int) {
            knots[i] = Pos(y, x)
            if (i == knots.size - 1) tailPositions.add(knots[i])
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