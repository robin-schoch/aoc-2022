package day06

import AdventOfCodeSolution

fun main() {
    Day06.run()
}

object Day06 : AdventOfCodeSolution<Int, Int> {

    override val testSolution1 = 7
    override val testSolution2 = 19

    private tailrec fun findMarker(from: Int, input: String, markerSize: Int = 4): Int {
        val duplicates = input
            .subSequence(from, (from + markerSize))
            .withIndex()
            .groupBy { it.value }
            .filter { it.value.size > 1 }
            .map { it.value.sortedByDescending { indexedChar -> indexedChar.index }[1].index + 1}
        return when (duplicates.size) {
            0 -> from + markerSize
            else -> findMarker(duplicates.max() + from, input, markerSize)
        }
    }

    override fun part1(input: List<String>) = findMarker(0, input[0])

    override fun part2(input: List<String>) = findMarker(0, input[0], 14)
}