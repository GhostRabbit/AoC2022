import org.testng.Assert.assertEquals
import org.testng.annotations.Test


object Day8 {
    class Tests {
        @Test
        fun part1() = assertEquals(part1("inputs/day8_example.txt"), 21)

        @Test
        fun part2() = assertEquals(part2("inputs/day8_example.txt"), 8)

        @Test
        fun part2_solution() = assertEquals(part2("inputs/day8.txt"), 157320)
    }

    class Solutions {
        @Test
        fun part1() = Timer.measure { println("Visible trees: ${part1("inputs/day8.txt")}") }

        @Test
        fun part2() = Timer.measure { println("Most scenic score: ${part2("inputs/day8.txt")}") }
    }

    fun part1(fileName: String): Int {
        val grid = Inputs.readString(fileName).lines()
        val visible = visibleGrid(grid)
        return visible.sumOf { it.map { if (it) 1 else 0 }.sum() }
    }

    private fun visibleGrid(grid: List<String>): Array<BooleanArray> {
        val visible = Array(grid.size) { BooleanArray(grid[0].length) }
        for (y in grid.indices) {
            var high = '0' - 1
            for (x in 0 until grid[0].length) {
                if (grid[y][x] > high) {
                    high = grid[y][x]
                    visible[y][x] = true
                }
            }
            high = '0' - 1
            for (x in (0 until grid[0].length).reversed()) {
                if (grid[y][x] > high) {
                    high = grid[y][x]
                    visible[y][x] = true
                }
            }
        }
        for (x in grid.indices) {
            var high = '0' - 1
            for (y in 0 until grid[0].length) {
                if (grid[y][x] > high) {
                    high = grid[y][x]
                    visible[y][x] = true
                }
            }
            high = '0' - 1
            for (y in (0 until grid[0].length).reversed()) {
                if (grid[y][x] > high) {
                    high = grid[y][x]
                    visible[y][x] = true
                }
            }
        }
        return visible
    }

    fun part2(fileName: String): Int {
        val grid = Inputs.readString(fileName).lines()
        var most = 0
        // Ignore border
        for (y in 1 until grid.size - 1) {
            for (x in 1 until grid[0].length - 1) {
                most = maxOf(most, scenicScore(grid, y, x))
            }
        }
        return most
    }

    private fun scenicScore(grid: List<String>, y0: Int, x0: Int): Int {
        val height = grid[y0][x0]
        var up = 0
        for (y in (0 until y0).reversed()) {
            up++
            if (grid[y][x0] >= height) break;
        }
        var left = 0
        for (x in (0 until x0).reversed()) {
            left++
            if (grid[y0][x] >= height) break;
        }
        var down = 0
        for (y in y0 + 1 until grid.size) {
            down++
            if (grid[y][x0] >= height) break;
        }
        var right = 0
        for (x in x0 + 1 until grid[0].length) {
            right++
            if (grid[y0][x] >= height) break;
        }
        return up * left * down * right
    }

}
