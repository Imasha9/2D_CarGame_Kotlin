package com.example.game

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.content.SharedPreferences
import android.content.Context

class MainActivity : AppCompatActivity(),Game1 {
    lateinit var rootLayout: LinearLayout
    lateinit var startBtn: Button
    lateinit var mGameview: GameView
    lateinit var score: TextView
    lateinit var highScoreTextView: TextView

    private lateinit var sharedPreferences: SharedPreferences
    private var highScore: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startBtn = findViewById(R.id.StartBtn)
        rootLayout = findViewById(R.id.rootLayout)
        score = findViewById(R.id.score)
        highScoreTextView = findViewById(R.id.highScore)
        sharedPreferences = getSharedPreferences("GamePreferences", Context.MODE_PRIVATE)


        highScore = sharedPreferences.getInt("highScore", 0)
        highScoreTextView.text = "High Score: $highScore"

        mGameview = GameView(this, this)

        startBtn.setOnClickListener {

            score.text = "SCORE:0"


            mGameview = GameView(this, this)
            mGameview.time = 0
            mGameview.score = 0
            mGameview.speed = 1
            mGameview.othercars.clear()


            mGameview.setBackgroundResource(R.drawable.road)
            rootLayout.addView(mGameview)


            startBtn.visibility = View.GONE
            score.visibility = View.GONE


            mGameview.invalidate()
        }
    }

    override fun closeGame(mscore: Int) {
        score.text = "Score:$mscore"

        if (mscore > highScore) {
            highScore = mscore
            highScoreTextView.text = "High Score: $highScore"
            // Save the new high score to SharedPreferences
            sharedPreferences.edit().putInt("highScore", highScore).apply()
        }
        rootLayout.removeView(mGameview)
        startBtn.visibility = View.VISIBLE
        score.visibility = View.VISIBLE
    }}
