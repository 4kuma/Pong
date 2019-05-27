package com.example.w08a

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF

const val H_SIZE =40f
const val W_SIZE =200f

class Player(var x: Float, var y: Float) {

    var score = 0
    fun draw(canvas: Canvas, i : Float) {
        y=i
        val red = Paint()
        red.setARGB(255,255,0,0)
        canvas.drawRect(RectF(x, y, x + W_SIZE, y + H_SIZE), red)
    }
}