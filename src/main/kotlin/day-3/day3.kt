import java.io.File

fun main() {
    val fileName = "src/main/kotlin/day-3/input"

    // part 1
    File(fileName).readLines().sumOf {
        val theTwo = splitIntoTwo(it)
        val common = comparePair(theTwo)
        val value = charToAscii(common)
        // println("$common $value")
        value
    }.also { println(it) }

    // part 2
    val lines = File(fileName).readLines()
    lines.groupBy {
        lines.indexOf(it) / 3
    }.map {
        val key = it.key
        val elves = it.value
        val first = elves[0]
        val second = elves[1]
        val third = elves[2]
        val group = Triple(first, second, third)
        val common = compairTriple(group)
        val value = charToAscii(common)
        value
    }.sum().also { println(it) }
}

fun splitIntoTwo(input: String): Pair<String, String> {
    // val input = "vJrwpWtwJgWrhcsFMMfFFhFp"
    val length = input.length
    return Pair(
        input.substring(0, length/2),
        input.substring(length/2, length)
    )
}

fun comparePair(input: Pair<String, String>): Char {
    val firsts = input.first.map { it }.toSet()
    val seconds = input.second.map { it }.toSet()

    return firsts.intersect(seconds).toList().let {
        if (it.size > 1) println("BIIIIGGGGG ")
        if (it.isNotEmpty()) it[0] else '#'
    }
}

fun charToAscii(input: Char): Int {
    val alphabets = "-abcdefghijklmnopqrstuvwxyz"
    return alphabets.indexOf(input, ignoreCase = true).let { if (it < 0) 0 else it } + if(input.isUpperCase())
        26
    else
        0
}

fun compairTriple(input: Triple<String, String, String>): Char {
    val first = input.first.map { it }.toSet()
    val second = input.second.map { it }.toSet()
    val third = input.third.map { it }.toSet()

    val firstIntersect = first.intersect(second)
    val intersect = firstIntersect.intersect(third)

    return intersect.toList()[0]
}