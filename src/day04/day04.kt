package day04

import readInput

fun createRange(input: String) = with(input.split('-')) {
    this[0].toInt()..this[1].toInt()
}

infix fun IntRange.overlaps(range: IntRange) = !(last < range.first || range.last < first)
infix fun IntRange.contains(range: IntRange) = first <= range.first && range.last <= last

fun main() {
    fun part1(input: List<String>): Int = input
        .map { it.split(',') }
        .map { createRange(it[0]) to createRange(it[1]) }
        .count { (section1, section2) -> section1 contains section2 || section2 contains section1 }

    fun part2(input: List<String>): Int = input
        .map { it.split(',') }
        .map { createRange(it[0]) to createRange(it[1]) }
        .count { (section1, section2) -> section1 overlaps section2 }

    val testInput = readInput("Day04", true)
    check(part1(testInput) == 2)
    check(part2(testInput) == 4) { part2(testInput) }

    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}