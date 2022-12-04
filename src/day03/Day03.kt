package day03

import AdventOfCodeSolution

fun main() {
    Day03.run()
}


fun splittIntoCompartments(content: String) = with(content.length / 2) {
    content.take(this).toSet() to content.takeLast(this).toSet()
}

fun Char.calculatePriority() = when {
    isLowerCase() -> this - 'a' + 1
    isUpperCase() -> this - 'A' + 27
    else -> throw IllegalStateException("item must be letter")
}

fun findBadges(rucksack: List<String>) = rucksack
    .map { it.toSet() }
    .reduce { items, sack -> items intersect sack }

object Day03 : AdventOfCodeSolution<Int, Int> {
    override val testSolution1 = 157
    override val testSolution2 = 70

    override fun part1(input: List<String>) = input
        .map { splittIntoCompartments(it) }
        .flatMap { (sack1, sack2) -> sack1 intersect sack2 }
        .sumOf { it.calculatePriority() }

    override fun part2(input: List<String>) = input
        .windowed(3, 3)
        .flatMap { findBadges(it) }
        .sumOf { it.calculatePriority() }
}