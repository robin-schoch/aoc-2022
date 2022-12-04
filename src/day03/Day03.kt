package day03

import AdventOfCodeSolution

fun main() {
    Day03.run()
}



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
    .reduce { items, sack -> items.intersect(sack) }

object Day03 : AdventOfCodeSolution<Int, Int> {
    override val testSolution1 = 157
    override val testSolution2 = 70

    override fun part1(input: List<String>) = input
        .map { splittIntoCompartments(it) }
        .map { it.first.intersect(it.second) }
        .map { set -> set.first() }
        .sumOf { calculatePriority(it) }

    override fun part2(input: List<String>) = input
        .windowed(3, 3)
        .map { findBadges(it) }
        .map { set -> set.first() }
        .sumOf { calculatePriority(it) }
}