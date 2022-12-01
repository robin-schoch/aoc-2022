package day01

import readInput
import java.util.PriorityQueue

fun main() {
    fun part1(input: List<String>): Int {
        var globalMax = 0
        var localMax = 0
        input.forEach {
            if (it.isNotEmpty()) {
                localMax += it.toInt()
                globalMax = Math.max(localMax, globalMax)
            } else  {
                localMax = 0
            }
        }
        return globalMax
    }

    fun part2(input: List<String>): Int {
        val topThree = PriorityQueue<Int>()
        var localMax = 0
        input.forEach {
            if (it.isNotEmpty()) {
                localMax += it.toInt()
            } else  {
                topThree.offer(localMax)
                localMax = 0
            }
            if (topThree.size > 3) {
                topThree.poll()
            }
        }
        return topThree.sum()

    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01", true)
    check(part1(testInput) == 24000)

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
