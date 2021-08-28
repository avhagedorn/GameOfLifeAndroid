package com.example.life

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.core.content.res.ResourcesCompat

class MyCanvasView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private val cellSizePx = 100f    // A float, because Canvas requires floats for positional args.
    private var numCols = 0
    private var numRows = 0

    private lateinit var myCanvas: Canvas
    private lateinit var bitmap: Bitmap
    lateinit var board: Board

    private val deadColor = ResourcesCompat.getColor(resources, R.color.black, null)
    private val aliveColor = ResourcesCompat.getColor(resources, R.color.white, null)

    private val alivePaint = Paint().apply { color = aliveColor }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        numCols = (w / cellSizePx).toInt()
        numRows = (h / cellSizePx).toInt()
        board.createBoard(numRows, numCols)

        if (::bitmap.isInitialized) bitmap.recycle()
        bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        myCanvas = Canvas(bitmap)
        myCanvas.drawColor(ResourcesCompat.getColor(resources, R.color.black, null))
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawBitmap(bitmap, 0f, 0f, null)
    }

    fun drawBoard() {
        myCanvas.drawColor(deadColor)
        board.cells.value?.let { cells ->
            for (col in 0 until numCols)
                for (row in 0 until numRows)
                    if (cells[row][col])
                        myCanvas.drawRect(
                            col*cellSizePx,         // Left
                            row*cellSizePx,         // Top
                            (col+1)*cellSizePx,    // Right
                            (row+1)*cellSizePx,  // Bottom
                            alivePaint                  // Paint
                        )
            invalidate()
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> drawPoint(event)
            MotionEvent.ACTION_MOVE -> drawPoints(event)
            MotionEvent.ACTION_UP -> drawPoints(event)
        }
        return true
    }

    private fun drawPoint(event: MotionEvent) {
        val x = (event.x / cellSizePx).toInt()
        val y = (event.y / cellSizePx).toInt()
        board.cells.value?.get(y)?.set(x, true)
        drawBoard()
    }

    private fun drawPoints(event: MotionEvent) {
        Log.i("MyCanvasView", event.pointerCount.toString())
        for (i in 0 until event.pointerCount) {
            val x = (event.getX(i) / cellSizePx).toInt()
            val y = (event.getY(i) / cellSizePx).toInt()
            if (x in 0 until numCols && y in 0 until numRows)
                board.cells.value?.get(y)?.set(x, true)
        }
        drawBoard()
    }
}