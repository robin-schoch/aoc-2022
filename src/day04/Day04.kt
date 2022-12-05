package day04

import AdventOfCodeSolution

fun main() {
    Day04.run()
}

fun createRange(input: String) = with(input.split('-')) {
    this[0].toInt()..this[1].toInt()
}

infix fun IntRange.overlaps(range: IntRange) = !(last < range.first || range.last < first)
infix fun IntRange.contains(range: IntRange) = first <= range.first && range.last <= last

object Day04 : AdventOfCodeSolution<Int, Int> {
    override val testSolution1 = 2
    override val testSolution2 = 4

    override fun part1(input: List<String>): Int = input
        .map { it.split(',') }
        .map { createRange(it[0]) to createRange(it[1]) }
        .count { (section1, section2) -> section1 contains section2 || section2 contains section1 }

    override fun part2(input: List<String>): Int = input
        .map { it.split(',') }
        .map { createRange(it[0]) to createRange(it[1]) }
        .count { (section1, section2) -> section1 overlaps section2 }
}

