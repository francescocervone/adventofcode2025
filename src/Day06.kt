fun main() {
    fun performOperation(operator: String, numbers: List<Long>): Long {
        val accumulator = when (operator) {
            "+" -> 0L
            "*" -> 1L
            else -> error("Unexpected operator $operator")
        }
        return numbers.fold(accumulator) { acc, number ->
            when (operator) {
                "+" -> acc + number
                "*" -> acc * number
                else -> error("Unexpected operator $operator")
            }
        }
    }

    fun part1(input: List<String>): Long {
        val matrix = Array(input.size) { i ->
            val line = input[i].replace("\\s+".toRegex(), ",").split(',').filter { it.isNotBlank() }
            Array(line.size) { j ->
                line[j]
            }
        }
        val equationsCount = matrix[0].size
        val equationSize = matrix.size
        var sum = 0L
        for (equationIndex in 0 until equationsCount) {
            val operator = matrix[equationSize - 1][equationIndex]
            val numbers = mutableListOf<Long>()
            for (i in 0 until equationSize - 1) {
                numbers += matrix[i][equationIndex].toLong()
            }
            sum += performOperation(operator, numbers)
        }
        return sum
    }

    fun String.toFunction(): (Long, Long) -> Long {
        return when (this) {
            "+" -> { n1, n2 -> n1 + n2 }
            "*" -> { n1, n2 -> n1 * n2 }
            else -> error("Unsupported operator $this")
        }
    }

    fun String.accumulatorStart(): Long {
        return when (this) {
            "+" -> 0L
            "*" -> 1L
            else -> error("Unsupported operator $this")
        }
    }

    fun part2(input: List<String>): Long {
        val operators = input.last().replace("\\s+".toRegex(), ",").split(',').filter { it.isNotBlank() }
        var sum = 0L
        var columnIndex = 0
        var currentOperatorIndex = 0
        var accumulator = operators[0].accumulatorStart()
        while (columnIndex < input[0].length) {
            val column = input.subList(0, input.lastIndex).map { it[columnIndex] }
            if (column.all { it.isWhitespace() }) {
                sum += accumulator
                currentOperatorIndex++
                if (currentOperatorIndex >= operators.size) break
                accumulator = operators[currentOperatorIndex].accumulatorStart()
                columnIndex++
            } else {
                val number = column.joinToString("").trim().toLong()
                accumulator = operators[currentOperatorIndex]
                    .toFunction()
                    .invoke(accumulator, number)
                columnIndex++
            }
        }
        sum += accumulator
        return sum
    }

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day06_test")
    check(part1(testInput) == 4277556L)
    check(part2(testInput) == 3263827L)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day06")
    part1(input).println()
    part2(input).println()
}
