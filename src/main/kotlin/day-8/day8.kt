import java.io.File

fun main() {
    val fileName = "src/main/kotlin/day-8/input"

    val lines = File(fileName).readLines()//.also { println(it.size) }
    val grid = lines.map {
        val splitLine = it.split("")
        val numbers = splitLine.subList(1, splitLine.size - 1) // remove first and last
        numbers.map { number -> number.toInt() }
    }//.also { println(prettyPrint(it)) }

    val positions = grid.mapIndexed { rowIndex: Int, row: List<Int> ->
        List(row.size) { columnIndex ->
            Pair(rowIndex, columnIndex)
        }
    }//.also { println(prettyPrint(it)) }

    // part 1
    positions.map { pairs: List<Pair<Int, Int>> ->
        pairs.map {
            val (x, y) = it
            val borderRow = x == 0 || x == positions.size - 1
            val borderColumn = y == 0 || y == pairs.size - 1
            if (borderRow || borderColumn) {
                true // visible
            } else {
                val itHeight = grid[x][y]
                val aroundPositions = seeAround(positions, it)
                val aroundHeights = around(
                    aroundPositions.top.map { aroundPair ->
                        val (aX, aY) = aroundPair
                        grid[aX][aY]
                    },
                    aroundPositions.right.map { aroundPair ->
                        val (aX, aY) = aroundPair
                        grid[aX][aY]
                    },
                    aroundPositions.bottom.map { aroundPair ->
                        val (aX, aY) = aroundPair
                        grid[aX][aY]
                    },
                    aroundPositions.left.map { aroundPair ->
                        val (aX, aY) = aroundPair
                        grid[aX][aY]
                    }
                )
                //println("position $it -> $aroundHeights")
                val aroundVisibility = around(
                    listOf(aroundHeights.top.fold(true) { acc, height: Int ->
                        val isVisible = height < itHeight
                        acc && isVisible
                    }),
                    listOf(aroundHeights.right.fold(true) { acc, height: Int ->
                        val isVisible = height < itHeight
                        acc && isVisible
                    }),
                    listOf(aroundHeights.bottom.fold(true) { acc, height: Int ->
                        val isVisible = height < itHeight
                        acc && isVisible
                    }),
                    listOf(aroundHeights.left.fold(true) { acc, height: Int ->
                        val isVisible = height < itHeight
                        acc && isVisible
                    })
                )
                aroundVisibility.top[0] || aroundVisibility.right[0] || aroundVisibility.bottom[0] || aroundVisibility.left[0]
            }
        }
    }.let { visibleMatrix ->
        visibleMatrix.fold(0) { acc: Int, booleans: List<Boolean> ->
            acc + booleans.fold(0) { boolacc, it ->
                boolacc + if (it) 1 else 0
            }
        }.also { println("count = $it") }
    }

    // part 2
    positions.map { pairs: List<Pair<Int, Int>> ->
        pairs.map {
            val (x, y) = it
            val borderRow = x == 0 || x == positions.size - 1
            val borderColumn = y == 0 || y == pairs.size - 1
            if (borderRow || borderColumn) {
                0 // visible
            } else {
                val itHeight = grid[x][y]
                val aroundPositions = seeAround(positions, it)
                val aroundHeights = around(
                    aroundPositions.top.map { aroundPair ->
                        val (aX, aY) = aroundPair
                        grid[aX][aY]
                    },
                    aroundPositions.right.map { aroundPair ->
                        val (aX, aY) = aroundPair
                        grid[aX][aY]
                    },
                    aroundPositions.bottom.map { aroundPair ->
                        val (aX, aY) = aroundPair
                        grid[aX][aY]
                    },
                    aroundPositions.left.map { aroundPair ->
                        val (aX, aY) = aroundPair
                        grid[aX][aY]
                    }
                )
                val top = findSmallSubList(aroundHeights.top, itHeight)
                val right = findSmallSubList(aroundHeights.right, itHeight)
                val bottom = findSmallSubList(aroundHeights.bottom, itHeight)
                val left = findSmallSubList(aroundHeights.left, itHeight)
                // println("position $it $itHeight -> ${top.size} ${left.size} ${right.size} ${bottom.size} ")

                val score = listOf(top, right, bottom, left).map { one ->
                    one.size
                }.filterNot { int -> int == 0 }.fold(1) { acc: Int, i: Int -> acc * i }
                score
            }
        }
    }.let {
        it.fold(0) { acc, ints ->
            val inner = ints.fold(0) { inneracc, i ->
                maxOf(inneracc, i)
            }
            maxOf(inner, acc)
        }.also { highScore -> println("part 2 : highest score is $highScore") }
    }
}

fun prettyPrint(grid: List<List<Any>>): String {
    return grid.fold("[") { acc, ints ->
        acc + "\n " + ints.joinToString(" ")
    } + "\n" + "]"
}

fun seeAround(positions: List<List<Pair<Int, Int>>>, position: Pair<Int, Int>): around<Pair<Int, Int>> {
    /*
            columnSize
           - - - - - here
           - - - - - -
           - - - - - -
           - - - - - -
           - - - - - (x, y)
           rowSize
     */
    val (x, y) = position
    val top: List<Pair<Int, Int>> = positions.fold<List<Pair<Int, Int>>, List<Pair<Int, Int>>>(emptyList()) { acc, pairs: List<Pair<Int, Int>> ->
        acc + pairs.mapNotNull { here ->
            val (curX, curY) = here
            if(curX < x && curY == y) {
                here
            } else {
                null
            }
        }
    }.sortedByDescending { it.first }
    val right: List<Pair<Int, Int>> = positions.fold<List<Pair<Int, Int>>, List<Pair<Int, Int>>>(emptyList()) { acc, pairs: List<Pair<Int, Int>> ->
        acc + pairs.mapNotNull { here ->
            val (curX, curY) = here
            if(curX == x && curY > y) {
                here
            } else {
                null
            }
        }
    }.sortedBy { it.second }
    val bottom: List<Pair<Int, Int>> = positions.fold<List<Pair<Int, Int>>, List<Pair<Int, Int>>>(emptyList()) { acc, pairs: List<Pair<Int, Int>> ->
        acc + pairs.mapNotNull { here ->
            val (curX, curY) = here
            if(curX > x && curY == y) {
                here
            } else {
                null
            }
        }
    }.sortedBy { it.first }
    val left: List<Pair<Int, Int>> = positions.fold<List<Pair<Int, Int>>, List<Pair<Int, Int>>>(emptyList()) { acc, pairs: List<Pair<Int, Int>> ->
        acc + pairs.mapNotNull { here ->
            val (curX, curY) = here
            if(curX == x && curY < y) {
                here
            } else {
                null
            }
        }
    }.sortedByDescending { it.second }

    return around(top, right, bottom, left)
}

data class around<T>(
    val top: List<T>,
    val right: List<T>,
    val bottom: List<T>,
    val left: List<T>,
)

fun findSmallSubList(top: List<Int>, itHeight: Int): List<Int> {
    val highestTop = top.firstOrNull { height -> height >= itHeight }
    val indexOfHighestTop = top.indexOf(highestTop)
    return if (highestTop == null) top else top.subList(0, indexOfHighestTop + 1)
}