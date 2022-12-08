import java.io.File
import java.util.Deque

fun main() {
    val fileName = "src/main/kotlin/day-6/input"

    // part 1
    val lines = File(fileName).readLines().map {
        val chars = it.map { char -> char }
        var indexOut = -1
        chars.foldIndexed(emptyList<Char>()) { index, acc, c ->
            val newAcc = if (acc.size == 4) {
                acc.subList(1, 4) + c
            } else {
                acc + c
            }
            // println(" $index - ${newAcc} ")
            if (newAcc.size == 4 && indexOut == -1 && newAcc.toSet().size == newAcc.size) {
                // println(index)
                indexOut = index + 1
            }
            newAcc
        }
        // println(indexOut)
    }

    // part 2
    File(fileName).readLines().map {
        val chars = it.map { char -> char }
        var indexOut = -1
        val size = 14
        chars.foldIndexed(emptyList<Char>()) { index, acc, c ->
            val newAcc = if (acc.size == 14) {
                acc.subList(1, 14) + c
            } else {
                acc + c
            }
            // println(" $index - ${newAcc} ")
            if (newAcc.size == 14 && indexOut == -1 && newAcc.toSet().size == newAcc.size) {
                // println(index)
                indexOut = index + 1
            }
            newAcc
        }
        println(indexOut)
    }
}
