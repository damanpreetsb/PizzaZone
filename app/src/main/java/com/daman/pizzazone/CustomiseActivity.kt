package com.daman.pizzazone

import android.animation.ValueAnimator
import android.os.Bundle
import android.widget.Button
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_customise.*


class CustomiseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customise)

        animateCheeseHeight(20)

        cheeseSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                when {
                    seekBar!!.progress < 25 ->  {
                        seekBar.progress = 0
                        animateCheeseHeight(20)
                    }
                    seekBar.progress in 25..74 -> {
                        seekBar.progress = 50
                        animateCheeseHeight(40)
                    }
                    else -> {
                        seekBar.progress = 100
                        animateCheeseHeight(60)
                    }
                }
            }
        })


        selectButton(normalCrustButton)
        unselectButton(thinCrustButton)
        pizzaCrustImageView.radius = 40f
        animateCrustHeight(80)

        normalCrustButton.setOnClickListener {
            selectButton(normalCrustButton)
            unselectButton(thinCrustButton)
            animateCrustHeight(80)
        }

        thinCrustButton.setOnClickListener {
            selectButton(thinCrustButton)
            unselectButton(normalCrustButton)
            animateCrustHeight(40)
        }
    }

    private fun animateCheeseHeight(height: Int) {
        val anim = ValueAnimator.ofInt(pizzaCheeseImageView.measuredHeight, height)
        anim.addUpdateListener { valueAnimator ->
            val `val` = valueAnimator.animatedValue as Int
            val layoutParams = pizzaCheeseImageView.layoutParams
            layoutParams.height = `val`
            pizzaCheeseImageView.layoutParams = layoutParams
        }
        anim.duration = 300
        anim.start()
    }

    private fun animateCrustHeight(height: Int) {
        val anim = ValueAnimator.ofInt(pizzaCrustImageView.measuredHeight, height)
        anim.addUpdateListener { valueAnimator ->
            val `val` = valueAnimator.animatedValue as Int
            val layoutParams = pizzaCrustImageView.layoutParams
            layoutParams.height = `val`
            pizzaCrustImageView.layoutParams = layoutParams
            pizzaCrustImageView.radius = `val`.div(2).toFloat()
        }
        anim.duration = 300
        anim.start()
    }

    private fun selectButton(button: Button) {
        button.backgroundTintList = ContextCompat.getColorStateList(this, R.color.buttonSelected)
        button.setTextColor(ContextCompat.getColor(this, R.color.textColorSelected))
    }

    private fun unselectButton(button: Button) {
        button.backgroundTintList = ContextCompat.getColorStateList(this, R.color.buttonUnselected)
        button.setTextColor(ContextCompat.getColor(this, R.color.textColorUnselected))
    }
}
