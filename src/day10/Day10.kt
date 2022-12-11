package day10

import AdventOfCodeSolution

fun main() {
    Day10.run()
}


object Day10 : AdventOfCodeSolution<Int, Unit> {
    override val testSolution1 = 13140
    override val testSolution2 = Unit

    private operator fun String.component1() = split(" ")[0]
    private operator fun String.component2() = split(" ").let { if (it.size == 2) it[1].toInt() else 0 }

    private infix fun Pair<Int, Int>.execute(instruction: String): List<Pair<Int, Int>> {
        val (instructionType, value) = instruction
        return when (instructionType) {
            "noop" -> listOf(first + 1 to second)
            "addx" -> listOf(first + 1 to second, first + 2 to second + value)
            else -> error("illegal instruction")
        }
    }

    private fun buildInstructionSequen(input: List<String>) = sequence {
        var lastInstruction = Pair(1, 1).also { yield(it) }
        for (instruction in input) (lastInstruction execute instruction).let {
            lastInstruction = it.last()
            it.forEach { state -> yield(state) }
        }
    }

    override fun part1(input: List<String>): Int {
        return buildInstructionSequen(input)
            .filter { it.first % 40 == 20 }
            .sumOf { it.first * it.second }
    }

    @OptIn(ExperimentalStdlibApi::class)
    override fun part2(input: List<String>) {
        buildInstructionSequen(input)
            .take(240)
            .chunked(40) {
                val line = IntArray(40)
                for (i in 0..<40) {
                    when (i) {in it[i].second - 1..it[i].second + 1 -> line[i] = 1 }
                }
                line.joinToString(separator = "") { lit -> if (lit == 0) "." else "#" }
            }.forEach { println(it) }

        repeat(40) { print("-") }
        println()
    }
}
