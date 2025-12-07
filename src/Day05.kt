import java.util.TreeSet

fun main() {
    fun part1(input: List<String>): Int {
        val freshProductsRanges = mutableListOf<LongRange>()
        val products = mutableListOf<Long>()
        var i = 0
        while (input[i].isNotBlank()) {
            val (start, end) = input[i].split('-').map { it.toLong() }
            freshProductsRanges += start..end
            i++
        }
        i++
        while (i < input.size) {
            products += input[i].toLong()
            i++
        }
        return products.map { id ->
            freshProductsRanges.any { range ->
                id in range
            }
        }.count { it }
    }

    fun part2(input: List<String>): Long {
        val freshProductsRanges = mutableListOf<LongRange>()
        var i = 0
        while (input[i].isNotBlank()) {
            val (start, end) = input[i].split('-').map { it.toLong() }
            freshProductsRanges += start..end
            i++
        }
        return freshProductsRanges
            .sortedWith(compareBy({ it.first }, { it.last }))
            .fold(mutableListOf<LongRange>()) { acc, range ->
                if (acc.isEmpty()) {
                    acc.add(range)
                } else {
                    val lastRange = acc.last()
                    if (lastRange.last < range.first) {
                        acc.add(range)
                    } else {
                        acc.removeLast()
                        acc.add(LongRange(lastRange.first, range.last))
                    }
                }
                acc
            }
            .sumOf { range ->
                range.last - range.first + 1
            }
    }

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day05_test")
    check(part1(testInput) == 3)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day05")
    part1(input).println()
    part2(input).println()
}
