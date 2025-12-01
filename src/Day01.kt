fun main() {
    fun part1Rotation(currentLocation: Int, instruction: String): Int {
        val rotations = instruction.substring(1).toInt() % 100
        return when (instruction.first()) {
            'L' -> {
                val result = currentLocation - rotations
                if (result >= 0) return result
                100 + result
            }

            'R' -> (currentLocation + rotations) % 100

            else -> error("")
        }
    }

    fun part1(input: List<String>): Int {
        var currentLocation = 50
        return input.count { instruction ->
            currentLocation = part1Rotation(currentLocation, instruction)
            currentLocation == 0
        }
    }

    fun countLoops(instruction: String): Int {
        val rotations = instruction.substring(1).toInt()
        return (rotations / 100)
    }


    fun part2(input: List<String>): Int {
        var currentLocation = 50
        return input.sumOf { instruction ->
            var loops = countLoops(instruction)
            val rotations = instruction.substring(1).toInt() % 100
            currentLocation = when (instruction.first()) {
                'L' -> {
                    if (currentLocation < rotations && currentLocation != 0) {
                        loops++
                    }
                    val result = currentLocation - rotations
                    if (result >= 0) result
                    else 100 + result
                }

                'R' -> {
                    val nextLocation = currentLocation + rotations
                    if (nextLocation > 100) loops++
                    nextLocation % 100
                }

                else -> error("")
            }
            loops + (if (currentLocation == 0) 1 else 0)
        }
    }

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 3)
    check(part2(testInput) == 6)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
