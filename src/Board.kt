import java.lang.Integer.min
import kotlin.math.max

/*
* Births: Each dead cell adjacent to exactly three live neighbors will become live in the next generation.
* Death by isolation: Each live cell with one or fewer live neighbors will die in the next generation.
* Death by overcrowding: Each live cell with four or more live neighbors will die in the next generation.
*/

class Board(private var board: Array<IntArray>) {

    companion object {
        private const val DEAD_SPACE = 0
        private const val ALIVE_SPACE = 1

        private const val ISOLATED = 1
        private const val OVERCROWDED = 4
        private const val SPAWN_NEW = 3
    }

    private var generation = 0

    fun displayBoard() {
        println("-$generation-----")
        board.forEach { subArr ->
            println(subArr.contentToString())
        }
    }

    fun nextBoard() {
        val updated = Array<IntArray>(board.size) { IntArray(board[0].size) }

        generation++
        for (y in board.indices) {
            for (x in board[0].indices) {
                val neighbors = getNeighbors(x, y)
                updated[y][x] = when (neighbors) {
                    2 -> if (board[y][x] == ALIVE_SPACE) ALIVE_SPACE else DEAD_SPACE
                    3 -> ALIVE_SPACE
                    else -> DEAD_SPACE
                }
            }
        }
        board = updated
    }

    private fun getNeighbors(x: Int, y: Int) : Int {
        var sum = 0
        for (iY in max(0, y-1)..min(y+1, board.size-1))
            for (iX in max(0, x-1)..min(x+1, board[0].size-1))
                sum += board[iY][iX]
        return sum - board[y][x]
    }

}