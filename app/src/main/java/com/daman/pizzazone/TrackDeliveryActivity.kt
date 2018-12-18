package com.daman.pizzazone

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import kotlinx.android.synthetic.main.activity_track_delivery.*

class TrackDeliveryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_track_delivery)

        animateFrontCover()
    }

    private fun animateFrontCover() {
        frontCoverImageView
            .animate()
            .translationY(790f)
            .setDuration(300)
            .setListener(object : Animator.AnimatorListener {
                override fun onAnimationRepeat(animation: Animator?) {
                }

                override fun onAnimationEnd(animation: Animator?) {
                    backCoverImageView.visibility = VISIBLE
                    backCoverImageView.alpha = 1f
                    pizzaImageView.visibility = GONE
                    frontCoverImageView.visibility = GONE
                    rotateBoxAnimation()
                }

                override fun onAnimationCancel(animation: Animator?) {
                }

                override fun onAnimationStart(animation: Animator?) {
                }
            })
            .startDelay = 500L
    }

    private fun rotateBoxAnimation() {
        val scaleDownX = ObjectAnimator.ofFloat(backCoverImageView, "scaleX", 1f, 0.6f).apply {
            duration = 300
        }
        val scaleDownY = ObjectAnimator.ofFloat(backCoverImageView, "scaleY", 1f, 0.6f).apply {
            duration = 300
        }
        val rotateBox = ObjectAnimator.ofFloat(backCoverImageView, "rotation", 0f, -45f).apply {
            duration = 300
        }
        val translateX = ObjectAnimator.ofFloat(backCoverImageView, "translationX", 0f, 1000f).apply {
            duration = 300
        }

        AnimatorSet().apply {
            play(scaleDownX).with(scaleDownY).with(rotateBox).before(translateX)
            addListener(object : Animator.AnimatorListener {
                override fun onAnimationRepeat(animation: Animator?) {
                }

                override fun onAnimationEnd(animation: Animator?) {
                    rootLayout.visibility = GONE
                    titleTextView.visibility = VISIBLE
                    trackButton.visibility = VISIBLE
                }

                override fun onAnimationCancel(animation: Animator?) {
                }

                override fun onAnimationStart(animation: Animator?) {
                }
            })
            start()
        }
    }

    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}
