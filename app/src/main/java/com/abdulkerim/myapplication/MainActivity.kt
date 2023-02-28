package com.abdulkerim.myapplication

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.abdulkerim.myapplication.databinding.ActivityMainBinding
import kotlin.random.Random

var score =0
var hscore= 0
lateinit var sharedPreferences: SharedPreferences
private lateinit var binding: ActivityMainBinding
var imagelist = arrayListOf<ImageView>()
var runnable= Runnable {  }
var handler= Handler(Looper.getMainLooper())
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        val view= binding.root
        setContentView(view)
        sharedPreferences= this.getSharedPreferences("com.abdulkerim.myapplication", MODE_PRIVATE)
        hscore= sharedPreferences.getInt("highScore",-1)
        binding.highScoreText.text="High Score: "+ hscore


        imagelist.add(binding.imageView1)
        imagelist.add(binding.imageView2)
        imagelist.add(binding.imageView3)
        imagelist.add(binding.imageView4)
        imagelist.add(binding.imageView5)
        imagelist.add(binding.imageView6)
        imagelist.add(binding.imageView7)
        imagelist.add(binding.imageView8)
        imagelist.add(binding.imageView9)

        for (image in imagelist){
            image.visibility=View.INVISIBLE
        }
    }


    fun increaseScore(view: View) {
        score += 1
        binding.scoreText.text= "Score: "+ score

    }

    fun start(view: View){
        binding.startButton.visibility=View.INVISIBLE
        score=0
        binding.scoreText.text="Score: "+ score
        var x =object :CountDownTimer( 15000,1000){
            override fun onTick(p0: Long) {
                binding.timeText.text="Time: "+p0/1000
            }

            override fun onFinish() {
                if (score> hscore){
                    hscore= score
                    binding.highScoreText.text="High Score: "+ hscore
                    sharedPreferences.edit().putInt("highScore", hscore).apply()

                }

                binding.startButton.visibility=View.VISIBLE
                handler.removeCallbacks(runnable)
                for (image in imagelist){
                    image.visibility=View.INVISIBLE
                }

            }

        }.start()
        hide()

    }


    fun hide() {

        runnable = object : Runnable{
            override fun run() {
                for (image in imagelist){
                    image.visibility=View.INVISIBLE
                }

                val rndIndex= Random.nextInt(9)
                imagelist[rndIndex].visibility=View.VISIBLE
                handler.postDelayed(runnable,500)
            }

        }

       handler.post(runnable)
    }

}


