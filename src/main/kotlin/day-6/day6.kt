import java.io.File

fun main() {
    val fileName = "src/main/kotlin/day-6/input"

    // part 1
    val lines = File(fileName).readLines()
    val split: Int = lines.foldIndexed(0) { index, acc, s ->
        if (s.isEmpty()) {
            // println(index)
            acc + index
        } else {
            acc
        }
    }

    // the initial input
    val initialConfigAsString = lines.subList(0, split - 1)//.also { println(it) }
    val stacks = lines[split-1].trim().split("\\s+".toRegex()).map { it.toInt() }
    // println(stacks.size)

    // the moves
    val moves = lines.subList(split + 1, lines.size).map {
        val regexCommand = "move (\\d+) from (\\d+) to (\\d+)".toRegex()
        val match = regexCommand.find(it)!!
        val (count, from, to) = match.destructured.toList().map { ints -> ints.toInt() }.subList(0, 3)
        Move(count, from - 1, to - 1)
    }

    part1(initialConfigAsString, stacks.size, moves)
    println()
    part2(initialConfigAsString, stacks.size, moves)
}

data class Move(
    val count: Int,
    val from: Int,
    val to: Int,
)

fun printStacks(listOfStacks: List<ArrayDeque<String>>) {
    listOfStacks.map { list ->
        printStack(list)
    }
}

fun printStack(list: ArrayDeque<String>) {
    list.joinToString(separator = "-").also { println(it) }
}

fun peekFinalStacks(listOfStacks: List<ArrayDeque<String>>) {
    listOfStacks.mapNotNull { list ->
        if (list.isNotEmpty()) {
            val last = list.last()
            last
        } else {
            null
        }
    }.joinToString(separator = "").also { println(it) }
}

fun part1(initialConfigAsString: List<String>, stackSize: Int , moves: List<Move>) {
    var listOfStacks: List<ArrayDeque<String>> = (0..stackSize).map { ArrayDeque() }
    initialConfigAsString.map {
        val row = it.split("\\s{1,4}".toRegex()).map { peek ->
            "\\[([^]]+)]".toRegex().find(peek)?.groupValues?.get(1)
        }
        //println(" ${row.size} - ${row.joinToString(prefix = "<", postfix = ">", separator = "\", \"")}")
        row }
        .reversed()
        .map {
            it.mapIndexed { index, s ->
                if(!s.isNullOrEmpty()) {
                    listOfStacks[index].addLast(s)
                } }
        }

    // println(listOfStacks)
    /*listOfStacks.map { list ->
        list.joinToString(separator = "-").also { println(it) }
    }*/

    moves.map {
        val (count, from, to) = it
        // println("move $count from #${from+1} to #${to+1}")
        (1..count).map {
            val top = listOfStacks[from].removeLastOrNull()
            if(!top.isNullOrEmpty()) {
                listOfStacks[to].addLast(top)
            }
        }
    }

    peekFinalStacks(listOfStacks)
}

fun part2(initialConfigAsString: List<String>, stackSize: Int , moves: List<Move>) {
    var listOfStacks: List<ArrayDeque<String>> = (0..stackSize).map { ArrayDeque() }
    initialConfigAsString.map {
        val row = it.split("\\s{1,4}".toRegex()).map { peek ->
            "\\[([^]]+)]".toRegex().find(peek)?.groupValues?.get(1)
        }
        //println(" ${row.size} - ${row.joinToString(prefix = "<", postfix = ">", separator = "\", \"")}")
        row }
        .reversed()
        .map {
            it.mapIndexed { index, s ->
                if(!s.isNullOrEmpty()) {
                    listOfStacks[index].addLast(s)
                } }
        }

    // printStack(listOfStacks)

    moves.map {
        val (count, from, to) = it
        // println("move $count from #${from+1} to #${to+1}")

        val crane = ArrayDeque<String>()
        (1..count).map {
            val top = listOfStacks[from].removeLastOrNull()
            if(!top.isNullOrEmpty()) {
                // listOfStacks[to].addLast(top)
                crane.addFirst(top)
            }
        }
        //printStack(crane)
        while (crane.isNotEmpty()) {
            val top = crane.removeFirstOrNull()
            if(!top.isNullOrEmpty()) {
                listOfStacks[to].addLast(top)
            }
        }
    }

    //println()
    //printStacks(listOfStacks)
    peekFinalStacks(listOfStacks)
}