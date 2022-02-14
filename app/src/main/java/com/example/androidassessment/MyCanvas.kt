package com.example.androidassessment

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import androidx.core.content.res.ResourcesCompat
import kotlin.math.abs

private const val stroke_width = 6f
var canvasState = "canvasState"
const val PENCIL = "canvasState"
const val ARROW = "arrow"
const val COLOR = "color"


class MyCanvas @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    // back color
    private val backgroundColor = ResourcesCompat.getColor(resources, R.color.white, null)
    private val touchTolerance = ViewConfiguration.get(context).scaledEdgeSlop
    private lateinit var canvas1: Canvas
    private lateinit var bitmap1: Bitmap
    private var radius = 8f

    // pen color
    private val penColor = ResourcesCompat.getColor(resources, R.color.black, null)

    private val paint = Paint().apply {
        color = penColor
        isAntiAlias = true
        isDither = true
        style = Paint.Style.STROKE
        strokeWidth = stroke_width
        strokeCap = Paint.Cap.ROUND
        strokeJoin = Paint.Join.ROUND
    }

    private var path = Path()
    private var motionX = 0f
    private var motionY = 0f
    private var currentX = 0f
    private var currentY = 0f

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        // Recycle function to prevent memory leak
        if (::bitmap1.isInitialized) bitmap1.recycle()

        bitmap1 = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        canvas1 = Canvas(bitmap1)
        canvas1.drawColor(backgroundColor)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        when(canvasState) {
            PENCIL -> {
                invalidate()
                canvas?.drawBitmap(bitmap1, 0f, 0f, null)
            }
            ARROW -> {
                invalidate()
                canvas?.drawBitmap(bitmap1, 0f, 0f, null)
            }

            COLOR -> {
                invalidate()
            }
        }
    }

    fun setColor(color: Int){
        paint.color = color
        invalidate()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {

        motionX = event!!.x
        motionY = event.y

        when (event.action) {
            MotionEvent.ACTION_DOWN -> touchStart()
            MotionEvent.ACTION_UP -> touchUp()
            MotionEvent.ACTION_MOVE -> touchMove()
        }
        return true
    }

    private fun touchMove() {
        val dx = abs(motionX - currentX)
        val dy = abs(motionY - currentY)
        if (dx >= touchTolerance || dy >= touchTolerance) {
            path.quadTo(currentX, currentY, (motionX + currentX) / 2, (motionY + currentY) / 2)
            currentX = motionX
            currentY = motionY
            canvas1.drawPath(path, paint)
        }
        invalidate()
    }

    private fun touchUp() {
        path.reset()
    }

    private fun touchStart() {
        path.reset()
        path.moveTo(motionX, motionY)
        currentX = motionX
        currentY = motionY
    }
}