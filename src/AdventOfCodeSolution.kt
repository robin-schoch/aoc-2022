import day01.Day01
import day02.Day02
import day03.Day03
import day04.Day04

fun main() {
    Day01.run()
    Day02.run()
    Day03.run()
    Day04.run()
}

annotation class AdventBenchmark

@AdventBenchmark
interface AdventOfCodeSolution<S1 : Any, S2 : Any> {
    val testSolution1: S1? get() = null
    val testSolution2: S2? get() = null
    private val paddedDay: String
        get() = this.javaClass.simpleName.takeLast(2)

    fun part1(input: List<String>): S1

    fun part2(input: List<String>): S2

    fun run() {
        val testInput = readInput("Day$paddedDay", true)
        val input = readInput("Day$paddedDay")
        println("============ $paddedDay ============")
        testSolution1?.let {
            println("-------------- part 1 --------------")
            part1(testInput).let { result ->
                check(result == it) { "error: $result" }
            }

            println("solution: ${part2(input)}")
        }
        testSolution2?.let {
            println("-------------- part 2 --------------")
            part2(testInput).let { result ->
                check(result == it) { "error: $result" }
            }
            println("solution: ${part2(input)}")
        }
        println()
    }
}