package com.example.life

import androidx.lifecycle.MutableLiveData
import kotlin.random.Random

class Board() {

    val cells = MutableLiveData<List<MutableList<Boolean>>>()
    var generation = 1
    var numCells = 0

    var rows = -1
    var cols = -1
    var isInitialized = false
    var boardIsStatic = false

    private lateinit var mBoard : List<MutableList<Boolean>>

    fun createBoard(rows: Int, cols: Int) {
        if (rows == -1 || cols == -1)
            throw Exception("Invalid cell configuration!")

        this.rows = rows
        this.cols = cols
        mBoard = List(rows) { MutableList(cols) { false } }

        generation = 1
        cells.postValue(mBoard)
        isInitialized = true
    }

    fun nextTurn() {
        numCells = 0
        if (isInitialized) {
            val res = List(rows) { MutableList(cols) { false } }

            for (row in 0 until rows) {
                for (col in 0 until cols) {
                    when (getNeighbors(row, col)) {
                        2 -> if (mBoard[row][col]) {
                            res[row][col] = true
                            numCells++
                        }
                        3 -> {
                            res[row][col] = true
                            numCells++
                        }
                    }
                }
            }

            mBoard = res
            cells.postValue(res)
            generation++
        }
    }

    private fun getNeighbors(row: Int, col: Int) : Int {
        var count = 0

        for (r in 0.coerceAtLeast(row - 1) until rows.coerceAtMost(row + 2))
            for (c in 0.coerceAtLeast(col - 1) until cols.coerceAtMost(col + 2)) {
                if (mBoard[r][c])
                    count++
            }

        // Do not count self as a neighbor
        if (mBoard[row][col])
            count--

        return count
    }

}