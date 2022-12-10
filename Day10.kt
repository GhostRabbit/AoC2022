import org.testng.Assert.assertEquals
import org.testng.annotations.Test

object Day10 {
    class `Tests and solutions` {
        @Test
        fun part1() = assertEquals(part1("inputs/day10_example.txt"), 13140)

        @Test
        fun part2() = assertEquals(
            part2("inputs/day10_example.txt"), listOf(
                "##..##..##..##..##..##..##..##..##..##..",
                "###...###...###...###...###...###...###.",
                "####....####....####....####....####....",
                "#####.....#####.....#####.....#####.....",
                "######......######......######......####",
                "#######.......#######.......#######.....",
            )
        )

        @Test
        fun `solve part 1`() = Timer.measure { println("Sum of six signal strengths:: ${part1("inputs/day10.txt")}") }

        @Test
        fun `solve part 2`() = Timer.measure {
            println("Display shows:")
            part2("inputs/day10.txt").forEach { println(it) }
        }
    }

    class Register {
        var value = 1
        var cycles = 0
        var history = mutableMapOf(-1 to 0)

        fun addx(v: Int) {
            value += v
            cycles += 2
            history.put(cycles, value)
        }

        fun noop() = cycles++

        fun signalAt(t: Int): Int = history.filter { it.key < t }.maxBy { it.key }.value

        fun strengthAt(t: Int): Int = signalAt(t) * t

        companion object {
            fun parseFrom(lines: List<String>): Register {
                val reg = Register()
                lines.map { it.split(' ') }
                    .forEach {
                        when (it[0]) {
                            "addx" -> reg.addx(it[1].toInt())
                            "noop" -> reg.noop()
                            else -> error("Unknown input '$it'")
                        }
                    }
                return reg
            }
        }
    }

    fun part1(fileName: String): Int {
        val X = Register.parseFrom(Inputs.readString(fileName).lines())
        return (20..220).step(40).sumOf { X.strengthAt(it) }
    }

    fun part2(fileName: String): List<String> = renderCrt(Register.parseFrom(Inputs.readString(fileName).lines()))

    private fun renderCrt(x: Register): List<String> {
        val crt = List(6) { MutableList(40) { '.' } }
        for (t in 1..crt.size * crt.first().size) {
            val signal = x.signalAt(t)
            drawPixel(crt, signal - 1, t)
            drawPixel(crt, signal, t)
            drawPixel(crt, signal + 1, t)
        }
        return crt.map { it.joinToString(separator = "") }
    }

    private fun drawPixel(crt: List<MutableList<Char>>, s: Int, t: Int) {
        if (s in crt.first().indices) crt.forEachIndexed { i, row -> if (t - 1 == i * row.size + s) row[s] = '#' }
    }
}