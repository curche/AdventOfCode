import java.io.File

fun main() {
    val fileName = "src/main/kotlin/day-2/input"

    // part 1
    File(fileName).readLines().map { line ->
        val str = line.split(" ")
        val plays = str.map { parsePlay(it) }
        val output = playRoundAsPlayerB(playerA = plays[0], playerB = plays[1])
        // println(" ${str[0]} v ${str[1]} (${plays[1]}) =  $output ")
        output + plays[1]
    }.sum().also {
        println(it)
    }

    // part 2
    File(fileName).readLines().map { line ->
        val str = line.split(" ")
        val playerA = parsePlayerA(str[0])
        val outcome = parseRoundEnd(str[1])
        val playerB = decidePlayerB(playerA = playerA, roundOutput = outcome)

        outcome + playerB
    }.sum().also {
        println(" Part 2 : $it ")
    }
}

fun parsePlay(response: String) : Int {
    return when (response) {
        "X", "A" -> 1 // rock
        "Y", "B" -> 2 // paper
        "Z", "C" -> 3 // scissors
        else -> 0
    }
}

fun playRoundAsPlayerB(playerA: Int, playerB: Int): Int {
    // 1, 2, 3
    // rock, paper, sci
    return if ( playerA == 1 && playerB == 1) {
        // rock and rock
        3
    } else if ( playerA == 1 && playerB == 2) {
        // rock and paper
        6
    } else if ( playerA == 1 && playerB == 3) {
        // rock and sci
        0
    } else if ( playerA == 2 && playerB == 1) {
        // paper and rock
        0
    } else if ( playerA == 2 && playerB == 2) {
        // paper and paper
        3
    } else if ( playerA == 2 && playerB == 3) {
        // paper and sci
        6
    } else if ( playerA == 3 && playerB == 1) {
        // sci and rock
        6
    } else if ( playerA == 3 && playerB == 2) {
        // sci and paper
        0
    } else if ( playerA == 3 && playerB == 3) {
        // sci and sci
        3
    } else {
        0
    }
}

// part two
fun parsePlayerA(response: String) : Int {
    return when(response) {
        "A" -> 1 // rock
        "B" -> 2 // paper
        "C" -> 3 // sci
        else -> 0
    }
}

fun parseRoundEnd(response: String) : Int {
    return when(response) {
        "X" -> 0 // lose
        "Y" -> 3 // draw
        "Z" -> 6 // win
        else -> 0
    }
}

fun decidePlayerB(playerA: Int, roundOutput: Int): Int {
    return when (roundOutput) {
        0 -> loseAgainst(playerA)
        3 -> playerA
        6 -> winAgainst(playerA)
        else -> 0
    }
}

fun loseAgainst(playerA: Int): Int {
    return when(playerA) {
        1 -> 3
        2 -> 1
        3 -> 2
        else -> 0
    }
}

fun winAgainst(playerA: Int): Int {
    return when(playerA) {
        1 -> 2
        2 -> 3
        3 -> 1
        else -> 0
    }
}
