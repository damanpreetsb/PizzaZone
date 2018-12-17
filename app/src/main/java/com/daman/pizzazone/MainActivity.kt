package com.daman.pizzazone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import androidx.core.app.ActivityOptionsCompat
import android.content.Intent
import android.view.View


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        customiseButton.setOnClickListener {
            val intent = Intent(this, CustomiseActivity::class.java)

            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, pizzaImageView as View, "pizza")
            startActivity(intent, options.toBundle())
        }
    }
}
