package day03

import readInput


fun splittIntoCompartments(content: String) = with(content.length / 2) {
    content.take(this).toSet() to content.takeLast(this).toSet()
}

fun calculatePriority(item: Char) = when {
    item.isLowerCase() -> item - 'a' + 1
    item.isUpperCase() -> item - 'A' + 27
    else -> throw IllegalStateException("item must be letter")
}

fun findBadges(ruckack: List<String>) = ruckack
    .map { it.toSet() }
    .reduce { interset, sack -> interset.intersect(sack) }

fun main() {
    fun part1(input: List<String>) = input
        .map { splittIntoCompartments(it) }
        .map { it.first.intersect(it.second) }
        .map { set -> set.first() }
        .sumOf { calculatePriority(it) }


    fun part2(input: List<String>) = input
        .windowed(3, 3)
        .map { findBadges(it) }
        .map { set -> set.first() }
        .sumOf { calculatePriority(it) }

    val testInput = readInput("Day03", true)
    check(part1(testInput) == 157) { "wrong answser is ${part1(testInput)}" }
    check(part2(testInput) == 70)

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}