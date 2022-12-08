import java.io.File

fun main() {
    val fileName = "src/main/kotlin/day-7/input"

    // part 1
    val stack = ArrayDeque<String>()
    var treeStore: Node? = null

    /*Node("/",
        -1,
        mutableListOf(
            Node(
                "a",
                -1,
                mutableListOf(
                    Node(
                        "e",
                        -1,
                        mutableListOf(
                            Node(
                                "i",
                                584,
                                null
                            )
                        )
                    ),
                    Node(
                        "f",
                        29116,
                        null
                    ),
                    Node(
                        "g",
                        2557,
                        null
                    ),
                    Node(
                        "h.lst",
                        62596,
                        null
                    )
                )
            ),
            Node(
                "b.txt",
                14848514,
                null
            ),
            Node(
                "c.dat",
                8504156,
                null
            ),
            Node(
                "d",
                -1,
                mutableListOf(
                    Node(
                        "j",
                        4060174,
                        null
                    ),
                    Node(
                        "d.log",
                        8033020
                    ),
                    Node(
                        "d.ext",
                        5626152
                    ),
                    Node(
                        "k",
                        7214296
                    )
                )
            )
        )
    ).prettyPrint()*/

    val lines = File(fileName).readLines()//.also { println(it.size) }
    lines.mapNotNull { line ->
        if (line.startsWith("$")) {
            val command = line.substringAfter("$").trim()
            if(command.startsWith("cd")) {
                val newFolder = command.substringAfter("cd").trim()
                if (newFolder == "..") {
                    if (stack.isNotEmpty()) {
                        stack.removeLast()
                        null
                    } else {
                        null
                    }
                } else {
                    stack.addLast(newFolder)
                    null
                }
            } else {
                null
            }
        } else {
            // we have a file/folder
            val splitLines = line.split(" ")
            val size = if (splitLines[0] == "dir") -1 else splitLines[0].toInt()
            if (treeStore == null) {
                // this is the first line which is cd /
                treeStore = Node("/", -1)
            }
            treeStore!!.addFileToTree(stack.toList().subList(1, stack.size), splitLines[1], size)
            null
        }
    }

    treeStore!!.calcSize()
    val output1 = treeStore!!.part1()
    println(output1)

    val total = 70000000
    val required = 30000000
    val remaining = total - treeStore!!.size
    val freeUp = required - remaining
    val output2 = mutableSetOf<Pair<String, Int>>()
    treeStore!!.part2(output2)
    output2.sortedBy {
        it.second
    }.filter {
        it.second >= freeUp
    }.map { println(it) }
}

class Node (
    val name: String,
    var size: Int,
    var children: MutableList<Node>? = mutableListOf()
) {
    fun prettyPrint(offset: Int = 0) {
        val offsetSpace = (0..offset).map { "" }.joinToString("   ")
        if(children == null) {
            // leaf node aka file node
            println("$offsetSpace- $name (file, size=$size) ")
        } else {
            println("$offsetSpace- $name (dir, size=$size)")
            children?.map { it.prettyPrint(offset + 1) }
        }
    }

    fun addFileToTree(stack: List<String>, name: String, size: Int) {
        //println("trying to insert into $stack -> $name ($size)")
        //if(stack.isNotEmpty()) this.prettyPrint()
        stack.fold(this) { acc: Node, s: String ->
            if(acc.children!!.isEmpty()) {
                acc.children!!.add(Node(s, -1))
            }
            acc.children!!.first { it.name == s }
        }.children?.add(
            if (size == -1) {
                // folder
                Node(name, size)
            } else {
                Node(name, size, null)
            }
        )
    }

    fun calcSize() {
        if (this.size < 0) {
            // folder with un-computed size
            this.size = if(this.children!!.isEmpty()) {
                0
            } else {
                this.children!!.sumOf {
                    it.calcSize()
                    it.size
                }
            }
        }
    }

    fun part1(): Int {
        return children?.filter { !it.children.isNullOrEmpty() }!!.sumOf {
            it.part1()
        } + if(size < 100000) size else 0
    }

    fun part2(output: MutableSet<Pair<String, Int>>) {
        children?.filter { !it.children.isNullOrEmpty() }!!.map {
            it.part2(output)
        }
        output.add(Pair(this.name, this.size))
    }
}