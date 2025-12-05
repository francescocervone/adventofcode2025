fun main() {
    fun Array<Array<Char>>.getOrNull(i: Int, j: Int): Char? {
        if (i < 0) return null
        if (i >= size) return null
        val row = get(i)
        if (j < 0) return null
        if (j >= row.size) return null
        return row.get(j)
    }

    fun canBeAccessed(i: Int, j: Int, matrix: Array<Array<Char>>): Boolean {
        var count = 0
        if (matrix.getOrNull(i - 1, j) == '@') count++
        if (matrix.getOrNull(i + 1, j) == '@') count++
        if (matrix.getOrNull(i, j - 1) == '@') count++
        if (matrix.getOrNull(i, j + 1) == '@') count++
        if (matrix.getOrNull(i - 1, j - 1) == '@') count++
        if (matrix.getOrNull(i - 1, j + 1) == '@') count++
        if (matrix.getOrNull(i + 1, j + 1) == '@') count++
        if (matrix.getOrNull(i + 1, j - 1) == '@') count++
        return count < 4
    }

    fun part1(input: List<String>): Int {
        val matrix = Array(input.size) { i ->
            Array(input[i].length) { j ->
                input[i][j]
            }
        }
        var count = 0
        for (i in matrix.indices) {
            for (j in matrix[i].indices) {
                if (matrix[i][j] == '@' && canBeAccessed(i, j, matrix)) {
                    count++
                }
            }
        }
        return count
    }

    fun removeRollsOfPapers(matrix: Array<Array<Char>>): Int {
        var count = 0
        for (i in matrix.indices) {
            for (j in matrix[i].indices) {
                if (matrix[i][j] == '@' && canBeAccessed(i, j, matrix)) {
                    count++
                    matrix[i][j] = '.'
                }
            }
        }
        return count
    }

    fun part2(input: List<String>): Int {
        val matrix = Array(input.size) { i ->
            Array(input[i].length) { j ->
                input[i][j]
            }
        }
        var count = 0
        while (true) {
            val rollsOfPapersRemoved = removeRollsOfPapers(matrix)
            if (rollsOfPapersRemoved > 0) count += rollsOfPapersRemoved
            else return count
        }
    }

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 13)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}
