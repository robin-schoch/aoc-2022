package day07

import AdventOfCodeSolution

fun main() {
    Day07.run()
}

interface CalucalteSize {
    val size: Long
}

data class File(val name: String, override val size: Long) : CalucalteSize
data class Directory(val parent: Directory?, val childDirectories: MutableMap<String, Directory>, val files: MutableList<File>) : CalucalteSize {

    override val size: Long
        get() = childDirectories.values.sumOf { it.size } + files.sumOf { it.size }

    fun countAllByMaxSizeOf(maxSize: Int = 100000): Long {
        return when {
            size > maxSize -> 0L
            else -> size
        } + childDirectories.values.sumOf { it.countAllByMaxSizeOf() }
    }

    fun findSmallestDirBiggerThan(minSize: Long): Long? {
        val smallest = childDirectories.values.mapNotNull { it.findSmallestDirBiggerThan(minSize) }.minOrNull()
        return if (size >= minSize) smallest ?: size else smallest

    }

    companion object {
        fun mkdir(parent: Directory): Directory = Directory(parent, mutableMapOf(), mutableListOf())
        fun root() = Directory(null, mutableMapOf(), mutableListOf())
    }
}

object Day07 : AdventOfCodeSolution<Long, Long> {

    private const val fileSystemSpace = 70000000L
    private const val minSystemSpaceRequired = 30000000L
    override val testSolution1 = 95437L
    override val testSolution2 = 24933642L

    private fun buildDirectoryTree(root: Directory, dir: Directory, terminalOuput: List<String>): Directory {
        if (terminalOuput.isEmpty()) return root
        return when (terminalOuput[0]) {
            "$ ls" -> buildDirectoryTree(root, dir, dir.readlines(terminalOuput.drop(1)))
            else -> buildDirectoryTree(root, dir.move(root, terminalOuput[0]), terminalOuput.drop(1))
        }
    }

    private fun Directory.move(root: Directory, instruction: String): Directory = with(instruction.split(" ")[2]) {
        when (this) {
            ".." -> parent ?: error("no parent dir found $this")
            "/" -> root
            else -> childDirectories[this] ?: error("child not found $this")
        }
    }

    private fun Directory.readlines(terminalOuput: List<String>): List<String> {
        terminalOuput.takeWhile { it[0] != '$' }.forEach {
            with(it.split(" ")) {
                when (this[0]) {
                    "dir" -> childDirectories[this[1]] = Directory.mkdir(this@readlines)
                    else -> files.add(File(this[1], this[0].toLong()))
                }
            }
        }
        return terminalOuput.dropWhile { it[0] != '$' }
    }

    override fun part1(input: List<String>): Long {
        val root = Directory.root()
        buildDirectoryTree(root, root, input)
        return root.countAllByMaxSizeOf()
    }

    override fun part2(input: List<String>): Long {
        val root = Directory.root()
        buildDirectoryTree(root, root, input)
        return root.findSmallestDirBiggerThan(minSystemSpaceRequired - (fileSystemSpace - root.size))
            ?: error("not enough space")
    }
}