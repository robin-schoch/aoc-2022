package day05

import AdventOfCodeSolution

fun main() {
    Day05.run()
}

object Day05 : AdventOfCodeSolution<String, String> {

    override val testSolution1 = "CMZ"
    override val testSolution2 = "MCD"
    private val stacks = mutableMapOf<Int, ArrayDeque<Char>>()

    private fun constructStacks(intput: List<String>) {
        stacks.clear()
        intput
            .takeWhile { !it.startsWith(" 1") }
            .flatMap {
                it.windowed(3, 4)
                    .mapIndexed { index, s -> index + 1 to s }
                    .filter { (_, container) -> container.isNotBlank() }
            }.forEach { (stack, container) ->
                stacks.computeIfPresent(stack) { _, tower -> tower.addLast(container[1]).let { tower } }
                stacks.putIfAbsent(stack, ArrayDeque(listOf(container[1])))
            }
    }

    private fun readSolution() = stacks
        .map { it.key to it.value.first() }
        .sortedBy { it.first }
        .joinToString(separator = "") { it.second.uppercase() }

    private fun readInstructions(input: List<String>) = input.filter { it.startsWith('m') }
        .map { it.split(" ") }
        .map { Triple(it[1].toInt(), it[3].toInt(), it[5].toInt()) }

    override fun part1(input: List<String>): String {
        constructStacks(input)
        readInstructions(input)
            .forEach { (count, fromIndex, toIndex) ->
                repeat(count) { stacks[toIndex]!!.addFirst(stacks[fromIndex]!!.removeFirst()) }
            }
        return readSolution()
    }

    override fun part2(input: List<String>): String {
        constructStacks(input)
        readInstructions(input)
            .forEach { (count, fromIndex, toIndex) ->
                val crane = mutableListOf<Char>()
                repeat(count) { crane.add(stacks[fromIndex]!!.removeFirst()) }
                crane.reversed().forEach { stacks[toIndex]!!.addFirst(it) }
            }
        return readSolution()
    }
}
