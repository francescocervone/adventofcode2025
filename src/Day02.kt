fun main() {
    fun isInvalid(num: Long): Boolean {
        val sequence = num.toString()
        val firstHalf = sequence.take(sequence.length / 2)
        val secondHalf = sequence.substring(sequence.length / 2, sequence.length)
        return firstHalf == secondHalf
    }

    fun part1(input: List<String>): Long {
        // [17330-35281 9967849351-9967954114 ...]
        val ranges = input.first().split(',')
        return ranges.flatMap { range ->
            // (17330, 35281)
            val (start, end) = range.split('-').map { it.toLong() }
            (start..end).mapNotNull { num ->
                // 17330
                if (isInvalid(num)) {
                    num
                } else {
                    null
                }
            }
        }.distinct()
            .sum()
    }

    fun isInvalid2(num: Long): Boolean {
        val sequence = num.toString()
        for (repetitions in 2..sequence.length) {
            val partitionLength = (sequence.length / repetitions)
            if (partitionLength * repetitions != sequence.length) continue
            val substrings = (0 until repetitions).map { repetition ->
                val startIndex = repetition * partitionLength
                val endIndex = startIndex + partitionLength
                sequence.substring(startIndex, endIndex)
            }
            if (substrings.distinct().size == 1) return true
        }
        return false
    }

    fun part2(input: List<String>): Long {
        // [17330-35281 9967849351-9967954114 ...]
        val ranges = input.first().split(',')
        return ranges.flatMap { range ->
            // (17330, 35281)
            val (start, end) = range.split('-').map { it.toLong() }
            (start..end).mapNotNull { num ->
                // 17330
                if (isInvalid2(num)) {
                    num
                } else {
                    null
                }
            }
        }.distinct()
            .sum()
    }

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day02_test")
    val testResult = part1(testInput)
    check(testResult == 1227775554L)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}
