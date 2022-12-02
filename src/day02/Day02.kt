package day02

import readInput

sealed class Shape {

    abstract val points: Int

    abstract fun losses(): Shape
    abstract fun wins(): Shape
    private fun draws() = this

    infix fun plays(opponent: Shape) = when (this) {
        opponent.draws() -> DRAW
        opponent.losses() -> WON
        opponent.wins() -> LOST
        else -> throw IllegalStateException("illegal shape")
    }

    fun shapeFromResult(result: Int): Shape = when (result) {
        DRAW -> this.draws()
        WON -> this.losses()
        LOST -> this.wins()
        else -> throw IllegalStateException("illegal game outcome")
    }

    companion object {
        const val DRAW = 3
        const val WON = 6
        const val LOST = 0

        fun findShape(c: Char) = when (c) {
            'A', 'X' -> Rock
            'B', 'Y' -> Paper
            'C', 'Z' -> Scissors
            else -> throw IllegalStateException("unknown figure")
        }

        fun findResult(c: Char) = when (c) {
            'X' -> LOST
            'Y' -> DRAW
            'Z' -> WON
            else -> throw IllegalStateException("unsupported game outcome")
        }

    }
}

object Rock : Shape() {
    override val points = 1
    override fun losses() = Paper
    override fun wins() = Scissors
}

object Scissors : Shape() {
    override val points = 3
    override fun losses() = Rock
    override fun wins() = Paper
}

object Paper : Shape() {
    override val points = 2
    override fun losses() = Scissors
    override fun wins() = Rock
}


fun main() {
    fun part1(input: List<String>) = input.sumOf {
        val evilShape = Shape.findShape(it[0])
        val myShape = Shape.findShape(it[2])
        (myShape plays evilShape) + myShape.points
    }

    fun part2(input: List<String>) = input.sumOf {
        val evilShape = Shape.findShape(it[0])
        val result = Shape.findResult(it[2])
        val myShape = evilShape.shapeFromResult(result)
        myShape.points + result
    }

    val testInput = readInput("Day02", true)
    check(part1(testInput) == 15)
    check(part2(testInput) == 12)

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}