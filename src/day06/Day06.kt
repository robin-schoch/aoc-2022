package day06

import AdventOfCodeSolution

fun main() {
    Day06.run()
}

object Day06 : AdventOfCodeSolution<Int, Int> {

    override val testSolution1 = 7
    override val testSolution2 = 19

    @OptIn(ExperimentalStdlibApi::class)
    private fun findMarker(from: Int, input: String, markerSize: Int = 4): Int {
        val duplicates = input
            .substring(from..<(from + markerSize))
            .asSequence()
            .mapIndexed { index, c -> index to c }
            .groupBy { it.second }
            .map { it.value.map { p -> p.first } }
            .filter { it.size > 1 }
            .map { it.sorted().reversed() }
            .map { it[1] + 1 }
        return when (duplicates.size) {
            0 -> from + markerSize
            else -> findMarker(duplicates.max() + from, input, markerSize)
        }
    }

    override fun part1(input: List<String>) = findMarker(0, input[0])

    override fun part2(input: List<String>) = findMarker(0, input[0], 14)
}