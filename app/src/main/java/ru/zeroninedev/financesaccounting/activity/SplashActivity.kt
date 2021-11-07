package ru.zeroninedev.financesaccounting.activity

import android.animation.Animator
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ViewAnimationUtils
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import lecho.lib.hellocharts.model.Line
import lecho.lib.hellocharts.model.LineChartData
import lecho.lib.hellocharts.model.PointValue
import lecho.lib.hellocharts.model.ValueShape
import lecho.lib.hellocharts.view.LineChartView
import ru.zeroninedev.financesaccounting.databinding.ActivitySplashBinding
import kotlin.math.hypot

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    private var activityStarted: Boolean = false

    //Data things
    private var loadData: Boolean = false

    // Animation things
    private var animationStarted: Boolean = false
    private var animationComplete: Boolean = false

    // View things
    private lateinit var imageView: ImageView
    private lateinit var linearLayout: LinearLayout
    private lateinit var tvAppName: TextView
    private lateinit var tvLoading: TextView
    private lateinit var chartView: LineChartView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initField()
        initFunc()

    }


    // Check anim started, or not
    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)

        if(hasFocus && !animationStarted){
            startCircularReveal()
        }
    }


    private fun startCircularReveal(){

        //Center point of clipping circle
        val centerPoint = IntArray(2)
        imageView.getLocationOnScreen(centerPoint)
        val xPoint = centerPoint[0]
        val yPoint = centerPoint[1]

        //Final radius for the clipping circle
        val xMax = xPoint.coerceAtLeast(linearLayout.width - xPoint)
        val yMax = yPoint.coerceAtLeast(linearLayout.height - yPoint)
        val finalRadius: Double = hypot(xMax.toDouble(), yMax.toDouble())

        val animation: Animator = ViewAnimationUtils.createCircularReveal(linearLayout
            ,xPoint,yPoint, 0F,finalRadius.toFloat())

        animation.setInterpolator {
            AccelerateDecelerateInterpolator(this@SplashActivity,null)
                .getInterpolation(it) }
        animation.duration = 3000
        animation.start()
        animationStarted = true

        animation.addListener(object : Animator.AnimatorListener{

            override fun onAnimationStart(p0: Animator?) {
                TODO("Not yet implemented")}

            override fun onAnimationEnd(p0: Animator?) {
                Log.d(TAG, "Complete show animation")
                animationComplete = true
                //Then read about coroutines replace if to if(loadData && animationComplete && !activityStarted)
                if(animationComplete && !activityStarted){
                    activityStarted = true
                    val intent = Intent(this@SplashActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }

            override fun onAnimationCancel(p0: Animator?) {
                TODO("Not yet implemented") }

            override fun onAnimationRepeat(p0: Animator?) {
                TODO("Not yet implemented") }
        })
    }

    private fun initChart(){

        val lines = arrayListOf<Line>()

        for(i in 0 until NUMBER_OF_LINES){
            val values = arrayListOf<PointValue>()
            values.add(PointValue(0F,0F))
            values.add(PointValue(1F,15F))
            values.add(PointValue(2F,10F))
            values.add(PointValue(3F,23F))
            values.add(PointValue(3.5F,48F))
            values.add(PointValue(5F,60F))

            val line = Line(values)
            line.color = Color.WHITE
            line.shape = ValueShape.CIRCLE
            line.isCubic = false
            line.isFilled = false
            line.setHasLabels(false)
            line.setHasLabelsOnlyForSelected(false)
            line.setHasLines(true)
            line.setHasPoints(true)
            lines.add(line)
        }
        val data = LineChartData(lines)
        data.baseValue = Float.NEGATIVE_INFINITY
        chartView.lineChartData = data

    }

    private fun initFont(){
        val typeface: Typeface = Typeface.createFromAsset(assets,"fonts/MochiyPopPOne-Regular.ttf")
        tvLoading.typeface = typeface
        tvAppName.typeface = typeface
    }

    private fun initFunc(){
        initChart()
        initFont()

    }

    private fun initField(){
        imageView = binding.image
        linearLayout = binding.linearLayout
        tvAppName = binding.appName
        tvLoading = binding.loadingText
        chartView = binding.chart
    }


    companion object{
        private const val TAG: String = "FinancesAccounting"
        private const val NUMBER_OF_LINES: Int = 1
    }

    //Create coroutines then read about that
}