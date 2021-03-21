package com.example.androidheadview

import android.view.View
import android.view.MotionEvent
import android.graphics.Paint
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.RectF
import android.app.Activity
import android.content.Context

val colors : Array<Int> = arrayOf(
    "#f44336",
    "#9C27B0",
    "#01579B",
    "#F57F17",
    "#4CAF50"
).map {
    Color.parseColor(it)
}.toTypedArray()
val parts : Int = 4
val strokeFactor : Float = 90f
val arcFactor : Float = 7.9f
val eyeFactor : Float = 25.6f
val lineFactor : Float = 4.5f
val delay : Long = 20
val backColor : Int = Color.parseColor("#BDBDBD")

fun Int.inverse() : Float = 1f / this
fun Float.maxScale(i : Int, n : Int) : Float = Math.max(0f, this - i * n.inverse())
fun Float.divideScale(i : Int, n : Int) : Float = Math.min(n.inverse(), maxScale(i, n)) * n
fun Float.sinify() : Float = Math.sin(this * Math.PI).toFloat()

fun Canvas.drawAndroidHead(scale : Float, i : Int,  w : Float, h : Float, paint : Paint) {
    val sf : Float = scale.sinify()
    val sf1 : Float = sf.divideScale(0, parts)
    val sf2 : Float = sf.divideScale(1, parts)
    val sf3 : Float = sf.divideScale(2, parts)
    val sf4 : Float = sf.divideScale(3, parts)
    val lineSize : Float = Math.min(w, h) / lineFactor
    val eyeSize : Float = Math.min(w, h) / eyeFactor
    val size : Float = Math.min(w, h) / arcFactor
    save()
    translate(w / 2, h / 2)
    paint.color = colors[i]
    drawArc(RectF(size, -size, size, size), 180f, 180f * sf1, true, paint)
    for (j in 0..1) {
        save()
        scale(1f - 2 * j, 1f)
        rotate(45f * sf4)
        paint.color = colors[i]
        drawLine(0f, 0f, size * sf2, -size * sf2, paint)
        paint.color = backColor
        drawCircle(size / 2, -size / 2, eyeSize * sf4, paint)
        restore()
    }
    restore()
}

fun Canvas.drawAHNode(i : Int, scale : Float, paint : Paint) {
    val w : Float = width.toFloat()
    val h : Float = height.toFloat()
    paint.color = colors[i]
    paint.strokeCap = Paint.Cap.ROUND
    paint.strokeWidth = Math.min(w, h) / strokeFactor
    drawAndroidHead(scale, i, w, h, paint)
}