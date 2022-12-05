import java.io.File

object Inputs {
    fun readLines(fileName: String): List<String> = File(fileName).readLines()

    fun readString(fileName: String): String = File(fileName).readText(Charsets.UTF_8)
}