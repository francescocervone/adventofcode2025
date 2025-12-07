fun main() {
    fun findStart(input: List<String>): Pair<Int, Int> {
        for (i in input.indices) {
            for (j in input[i].indices) {
                if (input[i][j] == 'S')
                    return i to j
            }
        }
        error("Not found")
    }

    fun goDown(input: List<String>, i: Int, j: Int, splitPoints: MutableSet<Pair<Int, Int>>): Int {
        if (i >= input.size) return 0
        if (input[i][j] == '^') {
            if ((i to j) in splitPoints) return 0
            splitPoints.add(i to j)
            return 1 + goDown(input, i, j - 1, splitPoints) + goDown(input, i, j + 1, splitPoints)
        } else {
            return goDown(input, i + 1, j, splitPoints)
        }
    }

    fun part1(input: List<String>): Int {
        val (i, j) = findStart(input)
        return goDown(input, i, j, mutableSetOf())
    }

    fun goDown2(input: List<String>, i: Int, j: Int, memo: MutableMap<Pair<Int, Int>, Int>): Int {
        memo[i to j]?.let { return it }
        if (i >= input.size) return 1
        return if (input[i][j] == '^') {
            goDown2(input, i, j - 1, memo) + goDown2(input, i, j + 1, memo)
        } else {
            goDown2(input, i + 1, j, memo)
        }.also { memo[i to j] = it }
    }

    fun part2(input: List<String>): Int {
        val (i, j) = findStart(input)
        return goDown2(input, i, j, mutableMapOf())
    }

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day07_test")
    part1(testInput).println()
    check(part1(testInput) == 21)
    part2(testInput).println()
    check(part2(testInput) == 40)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day07")
    part1(input).println()
    part2(input).println()
}
