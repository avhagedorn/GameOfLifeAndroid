import java.util.*
import kotlin.random.Random
import kotlin.random.Random.Default.nextInt

fun main(args: Array<String>) {

    val boardMatrix = arrayOf(
        intArrayOf(0, 0, 0, 0, 0, 0, 0, 0),
        intArrayOf(0, 0, 0, 0, 0, 0, 0, 0),
        intArrayOf(0, 0, 0, 1, 0, 0, 0, 0),
        intArrayOf(0, 0, 1, 0, 0, 0, 0, 0),
        intArrayOf(1, 0, 1, 0, 0, 0, 0, 0),
        intArrayOf(0, 1, 0, 0, 0, 0, 0, 0),
        intArrayOf(0, 0, 0, 0, 0, 0, 0, 0),
        intArrayOf(0, 0, 0, 0, 0, 0, 0, 0),
    )

    val board = Board(boardMatrix)
    board.displayBoard()

    for (i in 0 until 20) {
        board.nextBoard()
        board.displayBoard()
    }

}

fun getNewBoard() : Array<IntArray> {
    val arr = Array<IntArray>(1920) {
        IntArray(1080)
    }
    for (y in arr.indices)
        for (x in arr[0].indices)
            arr[y][x] = Random.nextInt(0, 2)
    return arr
}