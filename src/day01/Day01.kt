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
        val topThree = PriorityQueue<Int>(Comparator.reverseOrder())
        var localMax = 0
        input.forEach {
            if (it.isNotEmpty()) {
                localMax += it.toInt()
            } else  {
                topThree.offer(localMax)
                localMax = 0
            }
        }
        return topThree.poll() + topThree.poll() + topThree.poll()

    }

    // test if implementation meets criteria from the description, like:
    //val testInput = readInput("Day01_test")
    //check(part1(testInput) == 1)

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
