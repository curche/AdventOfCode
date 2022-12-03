import java.io.File

fun day1() {
    // println("Hello World!")

    // Try adding program arguments via Run/Debug configuration.
    // Learn more about running applications: https://www.jetbrains.com/help/idea/running-applications.html.
    // println("Program arguments: ${args.joinToString()}")

    val fileName = "src/main/kotlin/day-1/input"
    val listOfCalories = HashMap<Int, Int>()
    var i = 0
    listOfCalories[i] = 0
    File(fileName).forEachLine {
        if ("" == it) {
            i++;
            listOfCalories[i] = 0
        } else {
            listOfCalories[i] = listOfCalories.getValue(i) + Integer.parseInt(it)
        }
    }

    // println(listOfCalories)
    println(" Day 1 Part 1 " + listOfCalories.values.max())
    println(" Day 1 Part 2 " + listOfCalories.values.sorted().reversed().subList(0, 3).sum())
}