import java.io.File
import kotlin.math.abs

fun main() {
    val fileName = "src/main/kotlin/day-10/input"

    val X = 1
    var clock = 1
    var clockValues = mutableListOf<Triple<Int, Int, Int>>()
    val lines = File(fileName).readLines()
    val output = lines.mapNotNull {
        if(it.startsWith("addx")) {
            val increment = it.substringAfter("addx").trim().toInt()
            clock += 2
            Pair(clock, increment)
        } else {
            clock += 1
            null
        }
    }

    (20..clock step 40).map {
        val value = output.filter { pair -> pair.first <= it }.fold(X) { acc, pair -> acc + pair.second }
        value * it
    }.sumOf { it }.also { println("Part 1: $it ") }

    println("Part 2: ")
    println()
    (1..clock).chunked(40).mapIndexed { index, it ->
        it.map { it - (40*index) }
    }.mapIndexed { index, it ->
        it.map { crt ->
            // (crt, clock) = Pair(it, it + (40*index))
            val crtIndex = crt - 1
            val value = output.filter { pair -> pair.first <= crt + (40*index)}.fold(X) { acc, pair -> acc + pair.second }
            // println(" $crtIndex, $value ")

            val diff = crtIndex - value
            val abs = abs(diff)
            if (abs <= 1) {
                "#"
            } else {
                "."
            }
        }
    }.also {
        it.map {
            println(it.joinToString(""))
        }
    }
}