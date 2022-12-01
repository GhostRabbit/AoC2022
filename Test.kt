fun main() {
    Timer.measure {
        val lines = Inputs.readLines("inputs/Test.input")
        println(lines.joinToString(separator = " "))
    }
}