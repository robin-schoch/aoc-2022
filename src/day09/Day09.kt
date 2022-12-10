package day09

import AdventOfCodeSolution
import kotlin.math.abs

fun main() {
    Day09.run()
}

sealed class MoveDirection {
    companion object {
        fun of(direction: Char) = when (direction) {
            'R' -> Right
            'U' -> Up
            'L' -> Left
            'D' -> Down
            else -> error("Unsupported move direction")
        }
    }
}

object Right : MoveDirection()
object Left : MoveDirection()
object Up : MoveDirection()
object Down : MoveDirection()

data class Coord(var y: Int, var x: Int) {

    infix fun isNotOnSameRow(c: Coord) = abs(x - c.x) > 1
    infix fun isNotOnSameColumn(c: Coord) = abs(y - c.y) > 1

    infix fun isHigher(c: Coord) = y > c.y

    infix fun isRigther(c: Coord) = x > c.x

}

data class Position(val tail: List<Coord>) {

    private val head = tail[0]
    infix fun moveHead(direction: MoveDirection) = when (direction) {
        Down -> Coord(head.y - 1, head.x)
        Left -> Coord(head.y, head.x - 1)
        Right -> Coord(head.y, head.x + 1)
        Up -> Coord(head.y + 1, head.x)
    }.let { Position(listOf(it) + tail.drop(1)) }

    @OptIn(ExperimentalStdlibApi::class)
    fun adjustTailPosition(): Position {
        for (i in 1..<tail.size) {
            val prev = tail[i - 1]
            val curr = tail[i]

            if (prev isNotOnSameRow curr && prev isRigther curr) {
                curr.x = prev.x - 1
                if (prev isHigher curr) {
                    curr.y += 1
                } else if (curr isHigher prev) {
                    curr.y -= 1
                }
            } else if (prev isNotOnSameRow curr && curr isRigther prev) {
                curr.x = prev.x + 1
                if (prev isHigher curr) {
                    curr.y += 1
                } else if (curr isHigher prev) {
                    curr.y -= 1
                }
            } else if (prev isNotOnSameColumn curr && prev isHigher curr) {
                curr.y = prev.y - 1
                if (prev isRigther curr) {
                    curr.x += 1
                } else if (curr isRigther prev) {
                    curr.x -= 1
                }

            } else if (prev isNotOnSameColumn curr && curr isHigher prev) {
                curr.y = prev.y + 1
                if (prev isRigther curr) {
                    curr.x += 1
                } else if (curr isRigther prev) {
                    curr.x -= 1
                }
            }
        }
        return Position(tail)
    }

}


object Day09 : AdventOfCodeSolution<Int, Int> {

    override val testSolution1 = 88
    override val testSolution2 = 36
    private fun buildInstructionSequenz(input: List<String>) = sequence {
        input.map {
            val direction = MoveDirection.of(it[0])
            val count = it.split(" ")[1].toInt()
            repeat(count) { yield(direction) }

        }
    }

    private fun calculateRopeEndPos(ropeLength: Int, input: List<String>): Int {
        var position = Position(sequence { repeat(ropeLength) { yield(Coord(0, 0)) } }.toList())
        val tailVisited = mutableSetOf<String>()
        for (move in buildInstructionSequenz(input)) {
            position = position moveHead move
            position = position.adjustTailPosition()
            tailVisited.add(position.tail.last().toString())
        }
        return tailVisited.size
    }

    override fun part1(input: List<String>): Int {
        return calculateRopeEndPos(2, input)
    }

    override fun part2(input: List<String>): Int {
        return calculateRopeEndPos(10, input)
    }
}