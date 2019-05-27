package com.example.w08a

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF

const val SIZE =40f

class Ball(var x: Float, var y: Float) {

    fun draw(canvas: Canvas) {
        val red = Paint()
        red.setARGB(255,255,0,0)
        canvas.drawOval(RectF(x, y, x + SIZE, y + SIZE), red)
    }
}