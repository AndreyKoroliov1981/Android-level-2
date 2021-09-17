package com.example.myapplication.customview

import android.content.Context
import android.content.res.TypedArray
import android.graphics.*
import android.support.annotation.ColorInt
import android.support.annotation.Px
import android.util.AttributeSet
import com.example.myapplication.R
import com.example.myapplication.extentions.dpToPx
import java.io.Serializable
import kotlin.concurrent.fixedRateTimer

class AnalogClock @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : android.support.v7.widget.AppCompatImageView(context, attrs, defStyleAttr) {

    class TimeState(val time:Long,val isPlayed:Boolean):Serializable {
    }

    companion object {
        private const val DEFAULT_BORDER_WIDTH = 2
        private const val DEFAULT_HOUR_HAND_WIDTH = 3
        private const val DEFAULT_MINUTE_HAND_WIDTH = 2
        private const val DEFAULT_SECOND_HAND_WIDTH = 1
        private const val DEFAULT_BORDER_COLOR = Color.RED
        private const val DEFAULT_HOUR_HAND_COLOR = Color.BLACK
        private const val DEFAULT_MINUTE_HAND_COLOR = Color.BLUE
        private const val DEFAULT_SECOND_HAND_COLOR = Color.YELLOW
        private const val DEFAULT_SIZE = 50
        private const val DEFAULT_HOUR = 0
        private const val DEFAULT_MINUTE = 0
        private const val DEFAULT_SECOND = 0
    }

    @Px
    var borderWidth: Float = context.dpToPx(DEFAULT_BORDER_WIDTH)
    var hourHandWidth: Float = context.dpToPx(DEFAULT_HOUR_HAND_WIDTH)
    var minuteHandWidth: Float = context.dpToPx(DEFAULT_MINUTE_HAND_WIDTH)
    var secondHandWidth: Float = context.dpToPx(DEFAULT_SECOND_HAND_WIDTH)

    @ColorInt
    private var borderColor = DEFAULT_BORDER_COLOR
    private var hourHandColor = DEFAULT_HOUR_HAND_COLOR
    private var minuteHandColor = DEFAULT_MINUTE_HAND_COLOR
    private var secondHandColor = DEFAULT_SECOND_HAND_COLOR

    var hour = DEFAULT_HOUR
    var minute = DEFAULT_MINUTE
    var second = DEFAULT_SECOND

    private val clockPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val viewRect = Rect()
    private lateinit var resultBm: Bitmap
    private lateinit var maskBm: Bitmap
    private lateinit var scrBm: Bitmap

    var myCurrentTime: Long = 0
    var isPlayed = false

    var myW:Int=10
    var myH:Int=10
    var timer=fixedRateTimer("timer2", true, 0L, 1000) {}

    fun start() {
       isPlayed = true
       timer =fixedRateTimer("timer2", true, 0L, 1000)  {
           myCurrentTime += 1
           if (myCurrentTime==(24*60*60).toLong()) myCurrentTime=0
           second = (myCurrentTime % 60).toInt()
           minute = (myCurrentTime / 60 % 60).toInt()
           hour = (myCurrentTime / 60 / 60 % 12).toInt()
           prepareBitmaps()
           invalidate() }
    }

    fun stop() {
        isPlayed = false
        timer.cancel()
        second = (myCurrentTime % 60).toInt()
        minute = (myCurrentTime / 60 % 60).toInt()
        hour = (myCurrentTime / 60 / 60 % 12).toInt()
        prepareBitmaps()
        invalidate()
    }

    fun reset() {
        isPlayed = false
        myCurrentTime = 0
        hour = 0
        minute = 0
        second = 0
        timer.cancel()
        timer.purge()
        prepareBitmaps()
        invalidate()
    }

    fun currentTime(): Long = myCurrentTime


    init {
        if (attrs != null) {
            val ta: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.AnalogClock)
            borderWidth = ta.getDimension(R.styleable.AnalogClock_ac_borderWidth, context.dpToPx(DEFAULT_BORDER_WIDTH))
            hourHandWidth = ta.getDimension(
                R.styleable.AnalogClock_ac_hourHandWidth, context.dpToPx(
                    DEFAULT_HOUR_HAND_WIDTH
                )
            )
            minuteHandWidth = ta.getDimension(
                R.styleable.AnalogClock_ac_minuteHandWidth, context.dpToPx(
                    DEFAULT_MINUTE_HAND_WIDTH
                )
            )
            secondHandWidth = ta.getDimension(
                R.styleable.AnalogClock_ac_secondHandWidth, context.dpToPx(
                    DEFAULT_SECOND_HAND_WIDTH
                )
            )
            borderColor = ta.getColor(R.styleable.AnalogClock_ac_BorderColor, DEFAULT_BORDER_COLOR)
            hourHandColor = ta.getColor(R.styleable.AnalogClock_ac_BorderColor, DEFAULT_HOUR_HAND_COLOR)
            minuteHandColor = ta.getColor(
                R.styleable.AnalogClock_ac_BorderColor,
                DEFAULT_MINUTE_HAND_COLOR
            )
            secondHandColor = ta.getColor(
                R.styleable.AnalogClock_ac_BorderColor,
                DEFAULT_SECOND_HAND_COLOR
            )

            hour = ta.getInteger(R.styleable.AnalogClock_ac_hour, DEFAULT_HOUR)
            minute = ta.getInteger(R.styleable.AnalogClock_ac_minute, DEFAULT_MINUTE)
            second = ta.getInteger(R.styleable.AnalogClock_ac_second, DEFAULT_SECOND)

            ta.recycle()

            scaleType = ScaleType.CENTER_CROP

        }

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val initSize = resolveDefaultSize(widthMeasureSpec)
        setMeasuredDimension(initSize, initSize)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        if (w == 0) return
        with(viewRect) {
            left = 0
            top = 0
            right = w
            bottom = h
        }
        myW=w
        myH=h
        prepareBitmaps()
    }

    override fun onDraw(canvas: Canvas?) {
        //super.onDraw(canvas)
        canvas?.drawBitmap(resultBm, viewRect, viewRect, null)

    }


    private fun prepareBitmaps() {
        val x = myW / 2
        val y = myH / 2
        val radius = y - 5
        maskBm = Bitmap.createBitmap(myW, myH, Bitmap.Config.ARGB_8888)
        val maskCanvas = Canvas(maskBm)
        clockPaint.color = borderColor
        clockPaint.style = Paint.Style.STROKE
        clockPaint.strokeWidth = borderWidth
        maskCanvas.drawCircle(x.toFloat(), y.toFloat(), radius.toFloat(), clockPaint) //рисуем циферблат

        //рисуем стрелки
        maskCanvas.rotate((hour * 30).toFloat(), x.toFloat(), y.toFloat())
        clockPaint.color = hourHandColor
        clockPaint.style = Paint.Style.STROKE
        clockPaint.strokeWidth = hourHandWidth
        maskCanvas.drawLine(x.toFloat(), y.toFloat(), x.toFloat(), (radius - 0.4 * x).toFloat(), clockPaint)
        maskCanvas.rotate((-hour * 30.toFloat()), x.toFloat(), y.toFloat())

        maskCanvas.rotate((minute * 6).toFloat(), x.toFloat(), y.toFloat())
        clockPaint.color = minuteHandColor
        clockPaint.style = Paint.Style.STROKE
        clockPaint.strokeWidth = minuteHandWidth
        maskCanvas.drawLine(x.toFloat(), y.toFloat(), x.toFloat(), (radius - 0.5 * x).toFloat(), clockPaint)
        maskCanvas.rotate((-minute * 6).toFloat(), x.toFloat(), y.toFloat())

        maskCanvas.rotate((second * 6).toFloat(), x.toFloat(), y.toFloat())
        clockPaint.color = secondHandColor
        clockPaint.style = Paint.Style.STROKE
        clockPaint.strokeWidth = secondHandWidth
        maskCanvas.drawLine(x.toFloat(), y.toFloat(), x.toFloat(), (radius - 0.6 * x).toFloat(), clockPaint)
        maskCanvas.rotate((-second * 6).toFloat(), x.toFloat(), y.toFloat())

        // рисуем цифры
        clockPaint.color = Color.RED
        clockPaint.style = Paint.Style.FILL
        val myTS = x / 6
        clockPaint.textSize = (myTS).toFloat()
        maskCanvas.drawText("12", (x - myTS / 2).toFloat(), myTS.toFloat(), clockPaint)
        maskCanvas.drawText("6", (x - myTS / 2).toFloat(), myH - myTS.toFloat(), clockPaint)
        maskCanvas.drawText("3", (myW - myTS).toFloat(), (radius + myTS / 2).toFloat(), clockPaint)
        maskCanvas.drawText("9", (myTS).toFloat(), (radius + myTS / 2).toFloat(), clockPaint)
        resultBm = maskBm.copy(Bitmap.Config.ARGB_8888, true)
    }


    private fun resolveDefaultSize(spec: Int): Int {
        return when (MeasureSpec.getMode(spec)) {
            MeasureSpec.UNSPECIFIED -> context.dpToPx(DEFAULT_SIZE).toInt()
            MeasureSpec.AT_MOST -> MeasureSpec.getSize(spec)
            MeasureSpec.EXACTLY -> MeasureSpec.getSize(spec)
            else -> MeasureSpec.getSize(spec)
        }
    }

}