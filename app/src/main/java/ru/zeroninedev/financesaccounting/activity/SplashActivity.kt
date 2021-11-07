package ru.zeroninedev.financesaccounting.activity

import android.animation.Animator
import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ViewAnimationUtils
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.ImageView
import android.widget.LinearLayout
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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initField()

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
        var centerPoint = IntArray(2)
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
                if(loadData && animationComplete && !activityStarted){
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


    private fun initField(){
        imageView = binding.image
        linearLayout = binding.linearLayout
    }


    companion object{
        private const val  TAG = "FinancesAccounting"
    }

}