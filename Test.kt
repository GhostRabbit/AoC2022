fun main() {
    Timer.measure {
        val lines = Inputs.readLines("./Test.input")
        println(lines.joinToString(separator = " "))
    }
}