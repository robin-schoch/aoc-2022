package day04

import readInput

data class CleanSection(val start: Int, val end: Int) {

    infix fun contains(section: CleanSection) = start <= section.start && section.end <= end
    infix fun overlaps(section: CleanSection) = !(end < section.start || section.end < start)

    companion object {
        fun fromString(input: String) = with(input.split('-')) {
            CleanSection(this[0].toInt(), this[1].toInt())
        }
    }
}

fun main() {
    fun part1(input: List<String>): Int = input
        .map { it.split(',') }
        .map { CleanSection.fromString(it[0]) to CleanSection.fromString(it[1]) }
        .count { (section1, section2) -> section1 contains section2 || section2 contains section1 }

    fun part2(input: List<String>): Int = input
        .map { it.split(',') }
        .map { CleanSection.fromString(it[0]) to CleanSection.fromString(it[1]) }
        .count { (section1, section2) -> section1 overlaps section2 }

    val testInput = readInput("Day04", true)
    check(part1(testInput) == 2)
    check(part2(testInput) == 4) { part2(testInput) }

    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}