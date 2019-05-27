package com.example.w08a

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import kotlinx.android.synthetic.main.activity_main.view.*
import java.lang.Math.abs
import android.R.id.edit
import android.content.SharedPreferences
import kotlinx.android.synthetic.main.activity_main.*


class GameView(context: Context, attributeSet: AttributeSet)
    : SurfaceView(context, attributeSet), SurfaceHolder.Callback {

    private var PREFS_NAME = "mypref"
    lateinit var sharedPref : SharedPreferences

    private val thread : GameThread

//    private var ballX = 0f
//    private var ballY = 0f

    private var dx = 15f
    private var dy = 15f
//    private val SIZE = 50f


    val player3 = Player(0f,0f)
    val player4 = Player(0f,0f)
    val ball = Ball(250f,250f)


    init {
        holder.addCallback(this)
        thread = GameThread(holder, this)
    }


    override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {

    }

    override fun surfaceDestroyed(holder: SurfaceHolder?) {
        sharedPref = context.getSharedPreferences( PREFS_NAME, Context.MODE_PRIVATE)
        val editor : SharedPreferences.Editor = sharedPref.edit()
        editor.putInt("p1", player3.score)
        editor.putInt("p2", player4.score)
        editor!!.commit()
        thread.setRunning(false)

        thread.join()
    }

    override fun surfaceCreated(holder: SurfaceHolder?) {
        sharedPref = context.getSharedPreferences( PREFS_NAME, Context.MODE_PRIVATE)
        player3.score = sharedPref.getInt("p1", 0)
        player4.score = sharedPref.getInt("p2", 0)
        thread.setRunning(true)

        thread.start()
    }

    fun update() {

        ball.x+=dx
        ball.y+=dy

        if (ball.x <= 0 || ball.x+ SIZE >= width) {
            dx = -dx
        }
        if (ball.y <= 0) {
            player4.score++
            dy = -dy
            ball.x = width/2f
            ball.y = height/2f
        }
        if ( ball.y+SIZE >= height){
            player3.score++
            dy = -dy
            ball.x = width/2f
            ball.y = height/2f
        }
        if(ball.x + SIZE/2f >= player3.x && ball.x + SIZE/2f <= player3.x + W_SIZE && ball.y <= H_SIZE +5f && ball.y > 0f){
            dy=-dy
        }
        if(ball.y >= player4.y - H_SIZE && ball.y < player4.y && ball.x + SIZE/2f >= player4.x && ball.x + SIZE/2f <= player4.x + W_SIZE ){
            dy=-dy
        }

    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)

        if (canvas == null) return

//        player1.draw(canvas,width/2f-100f,0f)
//        player2.draw(canvas,width/2f-100f,height-50f)
        player3.draw(canvas,0f)
        player4.draw(canvas,height- H_SIZE)

        ball.draw(canvas)

        val red = Paint()
        red.setARGB(255,255,0,0)
        red.textSize = 100f
        canvas.drawText("${player3.score} : ${player4.score}",width/2f - 100f,height/2f,red)


    }

    override fun onTouchEvent(event: MotionEvent): Boolean {

        for (i in 0 until event!!.pointerCount) {
            if (event.getY(i) < height / 2f) {
                player3.x = event.getX(i) - W_SIZE / 2
            } else {
                player4.x = event.getX(i) - W_SIZE / 2
            }
        }
        return true

    }
}
