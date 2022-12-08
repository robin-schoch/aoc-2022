package day08

import AdventOfCodeSolution

fun main() {
    Day08.run()
}


@OptIn(ExperimentalStdlibApi::class)
class Forest(val trees: Array<IntArray>) {

    fun searchFromLeft() = searchForVisibleTrees(trees.indices, trees[0].indices) { x, y -> x to y }

    fun searchFromRight() = searchForVisibleTrees(trees.indices, trees[0].lastIndex downTo 0) { x, y -> x to y }

    fun searchFromTop() = searchForVisibleTrees(trees[0].lastIndex downTo 0, trees.indices) { x, y -> y to x }
    fun searchFromBottom() = searchForVisibleTrees(trees[0].indices, trees.lastIndex downTo 0) { x, y -> y to x }

    private fun searchForVisibleTrees(iRange: IntProgression, jRange: IntProgression, dir: (x: Int, y: Int) -> Pair<Int, Int>): MutableSet<Pair<Int, Int>> {
        val visible = mutableSetOf<Pair<Int, Int>>()
        iRange.forEach { i ->
            var heightToBeat = -1
            
            jRange.forEach { j ->
                dir(i, j).let {
                    if (trees[it.first][it.second] > heightToBeat) {
                        heightToBeat = trees[it.first][it.second]
                        visible.add(it)
                    }
                }
            }
        }
        return visible
    }

    fun calculateScenicScore() = trees.flatMapIndexed { index, treeRow ->
        treeRow.mapIndexed { jndex, _ ->
            (index to jndex).let { lookDown(it) * lookUp(it) * lookLeft(it) * lookRight(it) }
        }
    }.max()

    private fun look(coordinate: Pair<Int, Int>, bounded: (Pair<Int, Int>) -> Boolean, bounds: Int, generator: (Pair<Int, Int>) -> Pair<Int, Int>?): Int {
        return (generateSequence(coordinate, generator)
            .takeWhile(bounded)
            .map { trees[it.first][it.second] }
            .zipWithNext()
            .takeWhile { it.second < trees[coordinate.first][coordinate.second] }
            .count() + 1).coerceAtMost(bounds)
    }

    private fun lookUp(coordinate: Pair<Int, Int>) = look(coordinate, { it.first >= 0 }, coordinate.first) { it.first - 1 to it.second }

    private fun lookDown(coordinate: Pair<Int, Int>) = look(coordinate, { it.first < trees.size }, trees.size - coordinate.first - 1) { it.first + 1 to it.second }

    private fun lookLeft(coordinate: Pair<Int, Int>) = look(coordinate, { it.second >= 0 }, coordinate.second) { it.first to it.second - 1 }

    private fun lookRight(coordinate: Pair<Int, Int>) = look(coordinate, { it.second < trees[0].size }, trees[0].size - coordinate.second - 1) { it.first to it.second + 1 }

    companion object {
        fun seed(matrix: List<String>): Forest {
            return matrix
                .map { it.chunked(1).map { char -> char.toInt() }.toIntArray() }
                .toTypedArray()
                .let { Forest(it) }
        }
    }
}

object Day08 : AdventOfCodeSolution<Int, Int> {

    override val testSolution1 = 21
    override val testSolution2 = 8
    override fun part1(input: List<String>) = with(Forest.seed(input).searchFromLeft()) {
        addAll(Forest.seed(input).searchFromRight())
        addAll(Forest.seed(input).searchFromTop())
        addAll(Forest.seed(input).searchFromBottom())
        size
    }

    override fun part2(input: List<String>) = Forest.seed(input).calculateScenicScore()


}