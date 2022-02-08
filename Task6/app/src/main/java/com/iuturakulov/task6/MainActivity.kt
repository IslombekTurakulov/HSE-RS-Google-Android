package com.iuturakulov.task6

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button1: Button = findViewById(R.id.firstButton)
        button1.setOnClickListener { it ->
            didTapButton(it)
        }
        val button2: Button = findViewById(R.id.secondButton)
        //Animate the alpha value to 1f and set duration as 1.5 secs
        button2.setOnClickListener {
            it.alpha = 0f
            it.animate().alpha(1f).duration = 1500
        }

        val button3: Button = findViewById(R.id.thirdButton)
        button3.setOnClickListener {
            it.rotation = 0f;
            it.animate().rotationYBy(360f).duration = 3000
        }
    }


    private fun didTapButton(view: View?) {
        val button: Button = findViewById<View>(R.id.firstButton) as Button
        val myAnim: Animation = AnimationUtils.loadAnimation(this, R.anim.bubble)
        button.startAnimation(myAnim)
    }
}