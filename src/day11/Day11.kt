package day11

import AdventOfCodeSolution

fun main() {
    Day11.run()
}

var supermodulo = 1L
sealed class Operation {
    abstract val value: Long?

    fun execute(item: Long): Long = item.execute(value ?: item)

    abstract fun Long.execute(change: Long): Long

    companion object {
        fun fromString(ops: String): Operation {
            ops.split(" ").let {
                val n = if (it[2] == "old") null else it[2].toLong()
                return when (it[1]) {
                    "*" -> Multiply(n)
                    "+" -> Addition(n)
                    else -> error("invalid ops")
                }
            }
        }
    }
}

class Addition(override val value: Long?) : Operation() {
    override fun Long.execute(change: Long) = this + change
}

class Multiply(override val value: Long?) : Operation() {
    override fun Long.execute(change: Long) = this * change
}

class TestCondition(private val divisbleBy: Long, private val then: Int, private val otherwise: Int) {
    fun test(value: Long) = if (value % divisbleBy == 0L) then else otherwise

    companion object {
        fun fromLines(lines: List<String>): TestCondition {
            val divisbleBy = lines[0].split(" by ")[1].toLong()
            supermodulo *= divisbleBy
            val thenMonkeyId = lines[1].split(" monkey ")[1].toInt()
            val otherwiseMonkeyId = lines[2].split(" monkey ")[1].toInt()
            return TestCondition(divisbleBy, thenMonkeyId, otherwiseMonkeyId)
        }
    }
}

class Monkey(val id: Int, val items: ArrayDeque<Long>, val ops: Operation, val test: TestCondition, val reliever: (Long) -> Long) {

    var inspectionCount = 0

    infix fun playTurn(monkeies: Array<Monkey>) {
        inspectItems()
        monkeies.throwItems()
    }

    private fun Array<Monkey>.throwItems() {
        for (i in items.indices) {
            items.removeFirst().also {
                val nextMonkey = test.test(it)
                get(nextMonkey).items.addLast(it)
            }
        }
    }

    private fun inspectItems() {
        inspectionCount += items.size
        items.forEachIndexed { index, i -> items[index] = reliever(ops.execute(i)) % supermodulo  }
    }

    override fun toString(): String {
        return "Monkey $id inspected items $inspectionCount times"
    }
    companion object {
        fun fromInput(input: List<String>, reliever: (Long) -> Long ): Monkey {
            val id = input[0].split(" ")[1].dropLast(1).toInt()
            val items = ArrayDeque(input[1].split(": ")[1].split(", ").map { it.toLong() })
            val operation = Operation.fromString(input[2].split(" = ")[1])
            val condition = TestCondition.fromLines(input.drop(3))
            return Monkey(id, items, operation, condition, reliever)
        }
    }
}

object Day11 : AdventOfCodeSolution<Long, Long> {

    override val testSolution1 = 10605L
    override val testSolution2 = 2713310158L

    private fun Array<Monkey>.playRound() = forEach { it playTurn this }
    private fun parseMonkeys(input: List<String>, reliever: (Long) -> Long = {it / 3L}) = input.chunked(7).map { Monkey.fromInput(it, reliever) }.toTypedArray()
    override fun part1(input: List<String>): Long {
        supermodulo = 1
        val monkeys = parseMonkeys(input)
        repeat(20) { monkeys.playRound() }
        return monkeys
            .toList()
            .sortedByDescending { it.inspectionCount }
            .take(2)
            .fold(1) { acc, monkey -> acc * monkey.inspectionCount }
    }

    override fun part2(input: List<String>): Long {
        supermodulo = 1
        val monkeys = parseMonkeys(input) { it}
        repeat(10000) { monkeys.playRound()}
        return monkeys
            .toList()
            .sortedByDescending { it.inspectionCount }
            .take(2)
            .fold(1) { acc, monkey -> acc * monkey.inspectionCount }
    }
}