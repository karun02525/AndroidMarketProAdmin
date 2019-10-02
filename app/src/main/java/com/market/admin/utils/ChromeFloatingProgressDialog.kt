package com.market.admin.utils

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import com.market.admin.R

class ChromeFloatingProgressDialog(colors: IntArray) : Drawable(), Drawable.Callback {

    // default
    private val mAlpha = ALPHA_OPAQUE
    private var mColorFilter: ColorFilter? = null

    // points and paints
    private var mArrowPoints: Array<Point?>? = null
    private var mPaint1: Paint? = null
    private var mPaint2: Paint? = null
    private var mPaint3: Paint? = null
    private var mPaint4: Paint? = null
    private var unit: Double = 0.toDouble()
    private var width: Int = 0
    private var x_beg: Int = 0
    private var y_beg: Int = 0
    private var x_end: Int = 0
    private var y_end: Int = 0
    private var offset: Int = 0

    // speed related
    private var acceleration = ACCELERATION_LEVEL
    private var distance = 0.5 * ACCELERATION_LEVEL.toDouble() * MID_LEVEL.toDouble() * MID_LEVEL.toDouble()
    private var max_speed: Double = 0.toDouble() // set in setAcceleration(...);
    private var offsetPercentage: Double = 0.toDouble()

    // top color var
    private var colorSign: Int = 0
    private var currentProgressStates = ProgressStates.GREEN_TOP

    private enum class ProgressStates {
        GREEN_TOP,
        YELLOW_TOP,
        RED_TOP,
        BLUE_TOP
    }

    init {
        initCirclesProgress(colors)
    }

    private fun initCirclesProgress(colors: IntArray) {
        //init Paint colors
        initColors(colors)

        // init alpha and color filter
        alpha = mAlpha
        colorFilter = mColorFilter

        // offset percentage
        setAcceleration(ACCELERATION_LEVEL)
        offsetPercentage = 0.0

        // init colorSign
        colorSign = 1 // |= 1, |= 2, |= 4, |= 8 --> 0xF
    }

    private fun initColors(colors: IntArray) {
        // red circle, left up
        mPaint1 = Paint(Paint.ANTI_ALIAS_FLAG)
        mPaint1!!.color = colors[0]
        mPaint1!!.isAntiAlias = true

        // blue circle, right down
        mPaint2 = Paint(Paint.ANTI_ALIAS_FLAG)
        mPaint2!!.color = colors[1]
        mPaint2!!.isAntiAlias = true

        // yellow circle, left down
        mPaint3 = Paint(Paint.ANTI_ALIAS_FLAG)
        mPaint3!!.color = colors[2]
        mPaint3!!.isAntiAlias = true

        // green circle, right up
        mPaint4 = Paint(Paint.ANTI_ALIAS_FLAG)
        mPaint4!!.color = colors[3]
        mPaint4!!.isAntiAlias = true
    }

    override fun onBoundsChange(bounds: Rect) {
        super.onBoundsChange(bounds)
        measureCircleProgress(bounds.width(), bounds.height())
    }

    override fun onLevelChange(levell: Int): Boolean {
        var level = levell
        // calc one offset data is enough
        // 0.5 * a * t^2 / mCenterPoint.x = level / sideLevel
        // t from 0 to 10,000, so divided into 4 parts.
        // the ACCELERATION_LEVEL defines how many divisions in 10000 levels
        level %= MAX_LEVEL / acceleration

        val temp_level = level % (MID_LEVEL / acceleration)
        val ef_width = (unit * 3.0).toInt() // effective width
        if (level < CENT_LEVEL / acceleration) { // go
            if (level < MID_LEVEL / acceleration) {
                // set colorSign
                if (colorSign == 0xF) {
                    changeTopColor()
                    colorSign = 1
                }
                // from beg to mid
                offsetPercentage =
                    0.5 * acceleration.toDouble() * temp_level.toDouble() * temp_level.toDouble() / distance
                offset = (offsetPercentage * ef_width / 2).toInt() // x and y direction offset
            } else {
                // set colorSign
                colorSign = colorSign or 2
                // from mid to end
                offsetPercentage =
                    (max_speed * temp_level - 0.5 * acceleration.toDouble() * temp_level.toDouble() * temp_level.toDouble()) / distance + 1.0
                offset = (offsetPercentage * ef_width / 2).toInt() // x and y direction offset
            }
        } else { // back
            if (level < (CENT_LEVEL + MID_LEVEL) / acceleration) {
                // set colorSign
                if (colorSign == 0x3) {
                    changeTopColor()
                    colorSign = colorSign or 4
                }
                // from end to mid
                offsetPercentage =
                    0.5 * acceleration.toDouble() * temp_level.toDouble() * temp_level.toDouble() / distance
                offset = (ef_width - offsetPercentage * ef_width / 2).toInt() // x and y direction offset
            } else {
                // set colorSign
                colorSign = colorSign or 8
                // from mid to beg
                offsetPercentage =
                    (max_speed * temp_level - 0.5 * acceleration.toDouble() * temp_level.toDouble() * temp_level.toDouble()) / distance + 1.0
                offsetPercentage = if (offsetPercentage == 1.0) 2.0 else offsetPercentage
                offset = (ef_width - offsetPercentage * ef_width / 2).toInt() // x and y direction offset
            }
        }

        mArrowPoints!![0]!!.set(unit.toInt() + x_beg + offset, unit.toInt() + y_beg + offset) // mPaint1, left up
        mArrowPoints!![1]!!.set(
            (unit * 4.0).toInt() + x_beg - offset,
            (unit * 4.0).toInt() + y_beg - offset
        ) // mPaint2, right down
        mArrowPoints!![2]!!.set(
            unit.toInt() + x_beg + offset,
            (unit * 4.0).toInt() + y_beg - offset
        ) // mPaint3, left down
        mArrowPoints!![3]!!.set((unit * 4.0).toInt() + x_beg - offset, unit.toInt() + y_beg + offset) // mPaint4, right up

        return true
    }

    private fun changeTopColor() {
        when (currentProgressStates) {
            ChromeFloatingProgressDialog.ProgressStates.GREEN_TOP -> currentProgressStates = ProgressStates.YELLOW_TOP
            ChromeFloatingProgressDialog.ProgressStates.YELLOW_TOP -> currentProgressStates = ProgressStates.RED_TOP
            ChromeFloatingProgressDialog.ProgressStates.RED_TOP -> currentProgressStates = ProgressStates.BLUE_TOP
            ChromeFloatingProgressDialog.ProgressStates.BLUE_TOP -> currentProgressStates = ProgressStates.GREEN_TOP
        }
    }

    override fun draw(canvas: Canvas) {
        // draw circles
        if (currentProgressStates != ProgressStates.RED_TOP)
            canvas.drawCircle(mArrowPoints!![0]!!.x.toFloat(), mArrowPoints!![0]!!.y.toFloat(), unit.toFloat(), mPaint1!!)
        if (currentProgressStates != ProgressStates.BLUE_TOP)
            canvas.drawCircle(mArrowPoints!![1]!!.x.toFloat(), mArrowPoints!![1]!!.y.toFloat(), unit.toFloat(), mPaint2!!)
        if (currentProgressStates != ProgressStates.YELLOW_TOP)
            canvas.drawCircle(mArrowPoints!![2]!!.x.toFloat(), mArrowPoints!![2]!!.y.toFloat(), unit.toFloat(), mPaint3!!)
        if (currentProgressStates != ProgressStates.GREEN_TOP)
            canvas.drawCircle(mArrowPoints!![3]!!.x.toFloat(), mArrowPoints!![3]!!.y.toFloat(), unit.toFloat(), mPaint4!!)

        // draw the top one
        when (currentProgressStates) {
            ChromeFloatingProgressDialog.ProgressStates.GREEN_TOP -> canvas.drawCircle(
                mArrowPoints!![3]!!.x.toFloat(),
                mArrowPoints!![3]!!.y.toFloat(),
                unit.toFloat(),
                mPaint4!!
            )
            ChromeFloatingProgressDialog.ProgressStates.YELLOW_TOP -> canvas.drawCircle(
                mArrowPoints!![2]!!.x.toFloat(),
                mArrowPoints!![2]!!.y.toFloat(),
                unit.toFloat(),
                mPaint3!!
            )
            ChromeFloatingProgressDialog.ProgressStates.RED_TOP -> canvas.drawCircle(
                mArrowPoints!![0]!!.x.toFloat(),
                mArrowPoints!![0]!!.y.toFloat(),
                unit.toFloat(),
                mPaint1!!
            )
            ChromeFloatingProgressDialog.ProgressStates.BLUE_TOP -> canvas.drawCircle(
                mArrowPoints!![1]!!.x.toFloat(),
                mArrowPoints!![1]!!.y.toFloat(),
                unit.toFloat(),
                mPaint2!!
            )
        }
    }

    private fun measureCircleProgress(width: Int, height: Int) {
        // get min edge as width
        if (width > height) {
            // use height
            this.width = height - 1 // minus 1 to avoid "3/2=1"
            x_beg = (width - height) / 2 + 1
            y_beg = 1
            x_end = x_beg + this.width
            y_end = this.width
        } else {
            //use width
            this.width = width - 1
            x_beg = 1
            y_beg = (height - width) / 2 + 1
            x_end = this.width
            y_end = y_beg + this.width
        }
        unit = this.width.toDouble() / 5.0

        // init the original position, and then set position by offsets
        mArrowPoints = arrayOfNulls(4)
        mArrowPoints!![0] = Point(unit.toInt() + x_beg, unit.toInt() + y_beg) // mPaint1, left up
        mArrowPoints!![1] = Point((unit * 4.0).toInt() + x_beg, (unit * 4.0).toInt() + y_beg) // mPaint2, right down
        mArrowPoints!![2] = Point(unit.toInt() + x_beg, (unit * 4.0).toInt() + y_beg) // mPaint3, left down
        mArrowPoints!![3] = Point((unit * 4.0).toInt() + x_beg, unit.toInt() + y_beg) // mPaint4, right up
    }

    fun setAcceleration(acceleration: Int) {
        this.acceleration = acceleration
        distance =
            0.5 * acceleration.toDouble() * (MID_LEVEL / acceleration).toDouble() * (MID_LEVEL / acceleration).toDouble()
        max_speed = (acceleration * (MID_LEVEL / acceleration)).toDouble()
    }

    override fun setAlpha(alpha: Int) {
        mPaint1!!.alpha = alpha
        mPaint2!!.alpha = alpha
        mPaint3!!.alpha = alpha
        mPaint4!!.alpha = alpha
    }

    override fun setColorFilter(cf: ColorFilter?) {
        mColorFilter = cf
        mPaint1!!.colorFilter = cf
        mPaint2!!.colorFilter = cf
        mPaint3!!.colorFilter = cf
        mPaint4!!.colorFilter = cf
    }

    override fun getOpacity(): Int {
        return PixelFormat.TRANSLUCENT
    }

    override fun invalidateDrawable(who: Drawable) {
        val callback = callback
        callback?.invalidateDrawable(this)
    }

    override fun scheduleDrawable(who: Drawable, what: Runnable, `when`: Long) {
        val callback = callback
        callback?.scheduleDrawable(this, what, `when`)
    }

    override fun unscheduleDrawable(who: Drawable, what: Runnable) {
        val callback = callback
        callback?.unscheduleDrawable(this, what)
    }

    class Builder(context: Context) {
        private var mColors: IntArray? = null

        init {
            initDefaults(context)
        }

        private fun initDefaults(context: Context) {
            mColors = context.resources.getIntArray(R.array.google_colors)
        }

        fun colors(colors: IntArray?): Builder {
            if (colors == null || colors.size == 0) {
                throw IllegalArgumentException("Your color array must contains at least 4 values")
            }

            mColors = colors
            return this
        }

        fun build(): Drawable {
            return ChromeFloatingProgressDialog(mColors!!)
        }
    }

    companion object {

        // constants
        private val MAX_LEVEL = 10000
        private val CENT_LEVEL = MAX_LEVEL / 2
        private val MID_LEVEL = CENT_LEVEL / 2
        private val ALPHA_OPAQUE = 255
        private val ACCELERATION_LEVEL = 2
    }
}