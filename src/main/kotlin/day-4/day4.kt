import java.io.File

fun main() {
    val fileName = "src/main/kotlin/day-4/input"

    // part 1
    File(fileName).readLines().filter { line ->
        val elves = line.split(",")
        val ranges = elves.map {
            val pair = it.split("-").map { pairing -> pairing.toInt() }
            (pair[0]..pair[1]).toSet()
        }
        val intersect = ranges.reduce { acc, ints -> acc.intersect(ints) }
        ranges.any {
            it == intersect
        }
    }.size.also { println(it) }

    // part 2
    File(fileName).readLines().filter { line ->
        val elves = line.split(",")
        val ranges = elves.map {
            val pair = it.split("-").map { pairing -> pairing.toInt() }
            (pair[0]..pair[1]).toSet()
        }
        val intersect = ranges.reduce { acc, ints -> acc.intersect(ints) }
        intersect.isNotEmpty()
    }.size.also { println(it) }

}