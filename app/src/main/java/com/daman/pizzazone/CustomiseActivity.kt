package com.daman.pizzazone

import android.animation.Animator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.animation.AccelerateInterpolator
import android.widget.Button
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_customise.*
import android.animation.AnimatorSet
import android.content.Intent
import android.view.View
import androidx.core.app.ActivityOptionsCompat


class CustomiseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customise)

        animateCheeseHeight(30)

        cheeseSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                when {
                    seekBar!!.progress < 25 ->  {
                        seekBar.progress = 0
                        animateCheeseHeight(30)
                    }
                    seekBar.progress in 25..74 -> {
                        seekBar.progress = 50
                        animateCheeseHeight(50)
                    }
                    else -> {
                        seekBar.progress = 100
                        animateCheeseHeight(70)
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

        pepperoniCardView.setOnClickListener {
            pepperoniCardView.isSelected = !pepperoniCardView.isSelected
            if (pepperoniCardView.isSelected) {
                pepperoniCardView.setCardBackgroundColor(ContextCompat.getColor(this, R.color.buttonSelected))
                pepperoniTextView.setTextColor(ContextCompat.getColor(this, R.color.textColorSelected))
                firstToppingImageView.visibility = VISIBLE
                secondToppingImageView.visibility = VISIBLE
                thirdToppingImageView.visibility = VISIBLE
                animateToppings()
            } else {
                pepperoniCardView.setCardBackgroundColor(ContextCompat.getColor(this, R.color.buttonUnselected))
                pepperoniTextView.setTextColor(ContextCompat.getColor(this, R.color.textColorUnselected))
                firstToppingImageView.visibility = GONE
                secondToppingImageView.visibility = GONE
                thirdToppingImageView.visibility = GONE
            }
        }

        orderLayout.setOnClickListener {
            toppingsLayout.visibility = GONE
            pizzaImageView.visibility = VISIBLE
            pizzaImageView.alpha = 0f
            animatePizzaLayout()
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

    private fun animateToppings() {
        ObjectAnimator.ofFloat(toppingsLayout,"translationY", 0f, toppingsLayout.height.toFloat() - 12f).apply {
            interpolator = AccelerateInterpolator()
            duration = 600
            start()
        }
    }

    private fun animatePizzaLayout() {
        val fadeCheese = ObjectAnimator.ofFloat(pizzaCheeseImageView, "alpha", 1f, 0f).apply {
            duration = 400
            addListener(object : Animator.AnimatorListener {
                override fun onAnimationRepeat(animation: Animator?) {
                }

                override fun onAnimationEnd(animation: Animator?) {
                    pizzaCheeseImageView.visibility = GONE
                }

                override fun onAnimationCancel(animation: Animator?) {
                }

                override fun onAnimationStart(animation: Animator?) {
                }
            })
        }
        val fadeCrust = ObjectAnimator.ofFloat(pizzaCrustImageView, "alpha", 1f, 0f).apply {
            duration = 400
            addListener(object : Animator.AnimatorListener {
                override fun onAnimationRepeat(animation: Animator?) {
                }

                override fun onAnimationEnd(animation: Animator?) {
                    pizzaCrustImageView.visibility = GONE
                }

                override fun onAnimationCancel(animation: Animator?) {
                }

                override fun onAnimationStart(animation: Animator?) {
                }
            })
        }
        val fadeInPizza = ObjectAnimator.ofFloat(pizzaImageView, "alpha", 0f, 1f).apply {
            duration = 400
        }
        val scaleUpPizzaX = ObjectAnimator.ofFloat(pizzaImageView, "scaleX", 0f, 0.8f).apply {
            duration = 400
        }
        val scaleUpPizzaY = ObjectAnimator.ofFloat(pizzaImageView, "scaleY", 0f, 14f).apply {
            duration = 400
        }
        val translatePizzaY = ObjectAnimator.ofFloat(pizzaImageView, "translationY", 0f, -360f).apply {
            duration = 400
        }
        AnimatorSet().apply {
            play(fadeCheese).with(fadeCrust).with(fadeInPizza).with(scaleUpPizzaX).with(scaleUpPizzaY).with(translatePizzaY)
            addListener(object : Animator.AnimatorListener {
                override fun onAnimationRepeat(animation: Animator?) {
                }

                override fun onAnimationCancel(animation: Animator?) {
                }

                override fun onAnimationStart(animation: Animator?) {
                }

                override fun onAnimationEnd(animation: Animator?) {
                    val intent = Intent(this@CustomiseActivity, TrackDeliveryActivity::class.java)
                    val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this@CustomiseActivity, pizzaImageView as View, "customisedPizza")
                    startActivity(intent, options.toBundle())
                }
            })
            start()
        }
    }
}
