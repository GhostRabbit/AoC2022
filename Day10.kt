import org.testng.Assert.assertEquals
import org.testng.annotations.Test

object Day10 {
    class Tests {
        @Test
        fun part1() = assertEquals(part1("inputs/day10_example.txt"), 13140)

        @Test
        fun part2() = part2("inputs/day10_example.txt")
    }

    @JvmStatic
    fun main(args: Array<String>) {
//        Timer.measure { println("Sum of six signal strengths: ${part1("inputs/day10.txt")}") }
        Timer.measure { part2("inputs/day10.txt") }
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

        fun signalAt(t: Int): Int {
            val signals = history.filter { it.key < t }
            return if (signals.isEmpty()) history.maxBy { it.key }.value else
                signals.maxBy { it.key }.value
        }

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
        return (0..5).toList().map { it * 40 + 20 }.map { X.strengthAt(it) }.sum()
    }

    fun part2(fileName: String): Int {
        println(fileName)
        val X = Register.parseFrom(Inputs.readString(fileName).lines())
        val crt = MutableList(40 * 6) { "." }
        printcrt(crt, X)
        crt.chunked(40)
            .map { it.joinToString(separator = "") }
            .forEach { println(it) }
        return 0
    }

    private fun printcrt(crt: MutableList<String>, x: Register) {
        println(x.history)
        for (t in 1 until 240) {
            val signal = x.signalAt(t)
            draw(crt, signal-1, t)
            draw(crt, signal, t)
            draw(crt, signal+1, t)
        }
    }

    private fun draw(crt: MutableList<String>, s: Int, t:Int) {
        (0..5).toList().map { it * 40 }
            .forEach {
                val pos= it+s
                if(pos ==t-1) crt[pos] = "#"
            }
    }
}