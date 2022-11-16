import java.io.File

object Inputs {
    fun readLines(fileName: String): List<String> = File(fileName).readLines()

    fun readLinesAsInt(fileName: String): List<Int> = File(fileName).readLines().map { it.toInt() }

    fun readCsv(fileName: String): List<Int> =
        File(fileName).useLines { it.toList() }.toList().flatMap { it.split(',') }.map { it.toInt() }

    fun readEachLine(fileName: String) = File(fileName).useLines { it.toList() }.map { it -> it.split(',') }
}