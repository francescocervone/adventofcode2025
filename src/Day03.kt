fun main() {
    fun part1(input: List<String>): Int {
        return input.sumOf { bank ->
            var firstDigit = -1
            var firstDigitIndex = -1
            bank.dropLast(1).map { it.digitToInt() }.forEachIndexed { index, n ->
                if (n > firstDigit) {
                    firstDigit = n
                    firstDigitIndex = index
                }
            }
            val secondDigit = bank.substring(firstDigitIndex + 1).map { it.digitToInt() }.max()
            "$firstDigit$secondDigit".toInt()
        }
    }

    fun findMaxSequence(bank: List<Int>, size: Int): String {
        if (size < 1) return ""
        var max = -1
        var maxIndex = -1
        bank.dropLast(size - 1).forEachIndexed { index, n ->
            if (n > max) {
                max = n
                maxIndex = index
            }
        }
        return "$max${findMaxSequence(bank.subList(maxIndex + 1, bank.size), size - 1)}"
    }

    fun part2(input: List<String>) = input
        .map { bank -> bank.map { it.digitToInt() } }
        .sumOf { bank -> findMaxSequence(bank, 12).toLong() }

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 357)
    check(part2(testInput) == 3121910778619L)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}
