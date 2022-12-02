package day01

import readInput
import java.util.*
import kotlin.math.max

fun main() {
    fun part1(input: List<String>): Int {
        var globalMax = 0
        var localMax = 0
        input.forEach {
            if (it.isNotEmpty()) {
                localMax += it.toInt()
                globalMax = localMax.coerceAtLeast(globalMax)
            } else {
                localMax = 0
            }
        }
        return globalMax
    }

    fun part2(input: List<String>): Int {
        val topThree = PriorityQueue<Int>()
        var localMax = 0
        input.forEachIndexed { index, it ->
            if (it.isNotEmpty()) localMax += it.toInt()
            if (it.isEmpty() || index == input.size - 1) topThree.offer(localMax).also { localMax = 0 }
            if (topThree.size > 3) topThree.poll()
        }
        return topThree.sum()

    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01", true)
    check(part1(testInput) == 24000)
    println(part2(testInput))
    check(part2(testInput) == 45000)


    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}