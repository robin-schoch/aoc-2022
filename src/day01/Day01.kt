package day01

import AdventOfCodeSolution
import java.util.*

fun main() {
    Day01.run()
}

object Day01 : AdventOfCodeSolution<Int, Int> {
    override val testSolution1 = 24000
    override val testSolution2 = 45000

    override fun part1(input: List<String>): Int {
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

    override fun part2(input: List<String>): Int {
        val topThree = PriorityQueue<Int>()
        var localMax = 0
        input.forEachIndexed { index, it ->
            if (it.isNotEmpty()) localMax += it.toInt()
            if (it.isEmpty() || index == input.size - 1) topThree.offer(localMax).also { localMax = 0 }
            if (topThree.size > 3) topThree.poll()
        }
        return topThree.sum()
    }
}