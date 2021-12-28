package nytimes.example.app.utils

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.Transformation
import androidx.core.content.res.ResourcesCompat
import nytimes.example.app.R

class HorizontalDottedProgress : View {

    //actual dot radius
    private var dotRadius = 10

    //Bounced Dot Radius
    private var bounceDotRadius = 12

    //to get identified in which position dot has to bounce
    private var dotPosition: Int = 0

    //specify how many dots you need in a progressbar
    private var dotAmount = 3
    private var activeColorRes: Int = R.color.colorPrimaryLight
    private var colorRes: Int = R.color.grey
    private var animationDuration: Int = 300 // ms
    private var dotMargin = 34

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.DotProgress, 0, 0)
        try {
            dotRadius = ta.getInteger(R.styleable.DotProgress_dot_radius, dotRadius)
            bounceDotRadius =
                ta.getInteger(R.styleable.DotProgress_bounce_dot_radius, bounceDotRadius)
            dotPosition = ta.getInteger(R.styleable.DotProgress_dot_position, dotPosition)
            dotAmount = ta.getInteger(R.styleable.DotProgress_dot_amount, dotAmount)
            dotMargin = ta.getInteger(R.styleable.DotProgress_dot_margin, dotMargin)
            animationDuration =
                ta.getInteger(R.styleable.DotProgress_dot_animation_duration, animationDuration)
            activeColorRes =
                ta.getResourceId(R.styleable.DotProgress_dot_active_color, activeColorRes)
            colorRes = ta.getResourceId(R.styleable.DotProgress_dot_color, colorRes)
        } finally {
            ta.recycle()
        }
    }

    //Method to draw your customized dot on the canvas
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val paint = Paint()
        paint.flags = Paint.ANTI_ALIAS_FLAG //set the color for the dot that you want to draw
        paint.color =
            ResourcesCompat.getColor(context.resources, colorRes, null) //function to create dot
        createDot(canvas, paint)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow() //Animation called when attaching to the window, i.e to your screen
        startAnimation()
    }

    private fun createDot(canvas: Canvas, paint: Paint) {
        val colorPaint = Paint()
        colorPaint.flags = Paint.ANTI_ALIAS_FLAG
        val colorBouncePaint = Paint()
        colorBouncePaint.flags = Paint.ANTI_ALIAS_FLAG
        colorPaint.color = ResourcesCompat.getColor(context.resources, colorRes, null)
        colorBouncePaint.color = ResourcesCompat.getColor(context.resources, activeColorRes, null)
        val dy = (height / 2).toFloat()
        val box = width / dotAmount

        for (i in 0 until dotAmount) {
            val dx = ((box / 2) + (box * i)).toFloat()
            if (i == dotPosition) {
                canvas.drawCircle(dx, dy, bounceDotRadius.toFloat(), colorBouncePaint)
            } else {
                canvas.drawCircle(dx, dy, dotRadius.toFloat(), colorPaint)
            }
        }

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec) //calculate the view width
        val calculatedWidth =
            (dotMargin * (dotAmount - 1)) + (dotRadius * 2 * (dotAmount - 1)) + (bounceDotRadius * 2)
        val height = bounceDotRadius * 2 + 10 //MUST CALL THIS
        setMeasuredDimension(calculatedWidth, height)
    }

    private fun startAnimation() {
        val bounceAnimation = BounceAnimation()
        bounceAnimation.duration = animationDuration.toLong()
        bounceAnimation.repeatCount = Animation.INFINITE
        bounceAnimation.interpolator = LinearInterpolator()
        bounceAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {

            }

            override fun onAnimationEnd(animation: Animation) {

            }

            override fun onAnimationRepeat(animation: Animation) {
                dotPosition++ //when dotPosition == dotAmount , then start again applying animation from 0th positon , i.e  dotPosition = 0;
                if (dotPosition == dotAmount) {
                    dotPosition = 0
                }
            }
        })
        startAnimation(bounceAnimation)
    }

    private inner class BounceAnimation : Animation() {

        override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
            super.applyTransformation(
                interpolatedTime, t
            ) //call invalidate to redraw your view againg.
            invalidate()
        }
    }
}
