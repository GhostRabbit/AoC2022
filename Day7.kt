import com.sun.org.apache.bcel.internal.generic.NOP
import org.testng.Assert.assertEquals
import org.testng.annotations.Test


object Day7 {
    class Tests {
        @Test
        fun part1() = assertEquals(part1("inputs/day7_example.txt"), 95437)

        @Test
        fun part2() = assertEquals(part2("inputs/day7_example.txt"), 24933642)
    }

    class Solutions {
        @Test
        fun part1() = Timer.measure { println("Total size of 'small' directories: ${part1("inputs/day7.txt")}") }

        @Test
        fun part2() = Timer.measure { println("Size of dir to delete: ${part2("inputs/day7.txt")}") }
    }

    class FileSystem {
        val currentDir = ArrayDeque(listOf(Dir("")))
        fun cd(name: String) {
            when (name) {
                ".." -> currentDir.removeLast()
                else -> {
                    val dir = currentDir.last().findDir(name)
                    if (dir == null) {
                        val newDir = Dir(name)
                        currentDir.last().add(newDir)
                        currentDir.addLast(newDir)
                    } else {
                        currentDir.addLast(dir)
                    }
                }
            }
        }

        fun dirSizes(): List<Long> {
            val sizes = mutableListOf<Long>()
            currentDir.first().addSizeTo(sizes)
            return sizes
        }

        fun mkdir(name: String) = currentDir.last().add(Dir(name))
        fun mkFile(size: Long, name: String) = currentDir.last().add(File(size, name))

        data class File(val size: Long, val name: String)

        data class Dir(val name: String) {
            val dirs = ArrayList<Dir>()
            val files = ArrayList<File>()
            fun findDir(name: String): Dir? {
                return dirs.find { it.name == name }
            }

            fun add(dir: Dir) = dirs.add(dir)

            fun size(): Long = dirs.sumOf { it.size() } + files.sumOf { it.size }

            fun addSizeTo(sizes: MutableList<Long>) {
                sizes.add(size())
                dirs.forEach { it.addSizeTo(sizes) }
            }

            fun add(file: Day7.FileSystem.File) {
                files.add(file)
            }
        }
    }

    fun part1(fileName: String): Long {
        return fileSystemFrom(Inputs.readString(fileName).lines()).dirSizes().filter { it <= 100000 }.sum()
    }

    private fun fileSystemFrom(lines: List<String>): FileSystem {
        val fs = FileSystem()
        lines.forEach {
            val command = it.split(' ')
            when (command[0]) {
                "$" -> when (command[1]) {
                    "cd" -> fs.cd(command[2])
                    "ls" -> {} // Do nothing
                }

                "dir" -> // assume ls output of dir
                    fs.mkdir(command[1])

                else -> // assume ls output of file
                    fs.mkFile(command[0].toLong(), command[1])
            }
        }
        return fs
    }

    fun part2(fileName: String): Long {
        val fs = fileSystemFrom(Inputs.readString(fileName).lines())
        val used = fs.currentDir.first().size()
        val free = 70000000 - used
        val needed = 30000000
        val toDelete = needed - free
        return fs.dirSizes().filter { it >= toDelete }.min()
    }

}
