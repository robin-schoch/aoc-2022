import day01.Day01
import day02.Day02
import day03.Day03
import day04.Day04
import day05.Day05
import java.io.File
import java.math.BigInteger
import java.security.MessageDigest
import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue

fun main() {
    Day01.run()
    Day02.run()
    Day03.run()
    Day04.run()
    Day05.run()
}

@OptIn(ExperimentalTime::class)
interface AdventOfCodeSolution<S1 : Any, S2 : Any> {
    val testSolution1: S1? get() = null
    val testSolution2: S2? get() = null
    private val paddedDay: String
        get() = this.javaClass.simpleName.takeLast(2)

    fun part1(input: List<String>): S1

    fun part2(input: List<String>): S2

    private fun readInput(name: String, isTest: Boolean = false): List<String> {
        val childPath = "${if (isTest) name + "_test" else name}.txt"
        return File("src/${name.lowercase()}", childPath)
            .readLines()
    }

    /**
     * Converts string to md5 hash.
     */
    fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
        .toString(16)
        .padStart(32, '0')

    fun run() {
        val testInput = readInput("Day$paddedDay", true)
        val input = readInput("Day$paddedDay")
        println("============== Day $paddedDay ==============")
        testSolution1?.let {
            println("-------------- part 1 --------------")
            part1(testInput).let { result ->
                check(result == it) { "error: $result" }
            }
            val result1 = measureTimedValue {
                part1(input)
            }
            println("Solution: ${result1.value} used ${result1.duration}")
        }
        testSolution2?.let {
            println("-------------- part 2 --------------")
            part2(testInput).let { result ->
                check(result == it) { "error: $result" }
            }
            val result2 = measureTimedValue {
                part2(input)
            }
            println("Solution: ${result2.value} used ${result2.duration}")
        }
        println("====================================\n\n")
    }
}