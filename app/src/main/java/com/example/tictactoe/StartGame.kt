package com.example.tictactoe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

class StartGame : AppCompatActivity() {

    private lateinit var playAgain : Button
    private lateinit var textView: TextView
    private lateinit var iv0 : ImageView
    private lateinit var iv1 : ImageView
    private lateinit var iv2 : ImageView
    private lateinit var iv3 : ImageView
    private lateinit var iv4 : ImageView
    private lateinit var iv5 : ImageView
    private lateinit var iv6 : ImageView
    private lateinit var iv7 : ImageView
    private lateinit var iv8 : ImageView
    private var oGPlayerKeeper = -1
    private var player = -1
    private val machine = 0
    private val human = 1


    var gameFinished = false
    var gameState = arrayOf("","","","","","","","","")
    private var winningPositions = arrayOf(
        arrayOf(3, 4, 5), arrayOf(6, 7, 8),
        arrayOf(0, 3, 6), arrayOf(1, 4, 7), arrayOf(2, 5, 8),
        arrayOf(0, 4, 8), arrayOf(2, 4, 6), arrayOf(0, 1, 2),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_game)
        player = intent.getIntExtra("player", -1)
        oGPlayerKeeper = player
        textView = findViewById(R.id.topOfButton)
        playAgain = findViewById(R.id.playAgain)
        iv0 = findViewById(R.id.iv0)
        iv1 = findViewById(R.id.iv1)
        iv2 = findViewById(R.id.iv2)
        iv3 = findViewById(R.id.iv3)
        iv4 = findViewById(R.id.iv4)
        iv5 = findViewById(R.id.iv5)
        iv6 = findViewById(R.id.iv6)
        iv7 = findViewById(R.id.iv7)
        iv8 = findViewById(R.id.iv8)
        textView.visibility = View.GONE
        playAgain.visibility = View.GONE
        if ( player == machine){
            machinePlays()
        }
    }//onCreate

    private fun machinePlays(){
        if (gameFinished || player == human ) return
        val IM = arrayOf(iv0, iv1, iv2, iv3, iv4, iv5, iv6, iv7, iv8)
        val position = makeTheBestPlay()
        gameState[position] = "m"
        val playHere = IM[position]
        Handler(Looper.getMainLooper()).postDelayed(
            {
                playHere.translationY = -1000f
                playHere.setImageResource(R.drawable.red)
                playHere.animate().translationYBy(1000f).rotation(720f).duration = 1000
            },
            1000,
        )
        player = human
        status()
    }//machinePlays

    fun drop(view: View){

        if (gameFinished || player == machine) return
        val counter = view as ImageView
        val tag = counter.tag.toString().toInt()
        if (gameState[tag]  != "") {return}
        gameState[tag] = "h"
        counter.translationY = -1000f
        counter.setImageResource(R.drawable.yellow)
        counter.animate().translationYBy(1000f).rotation(720f).duration = 1000
        player = machine
        status()
        machinePlays()
    }//drop

    fun playAgain(view : View) {
        player = oGPlayerKeeper
        val IM = arrayOf(iv0, iv1, iv2, iv3, iv4, iv5, iv6, iv7, iv8)
        for (i in 0..8) {
            gameState[i] = ""
            IM[i].setImageResource(0)
        }
        textView.visibility = View.GONE
        playAgain.visibility = View.GONE
        gameFinished = false
        if(oGPlayerKeeper == machine) machinePlays()
    }//playAgain

    private fun  status() {

        for (win in winningPositions ){
            val sum = gameState[win[0]] +gameState[win[1]]  + gameState[win[2]]
            if (sum == "mmm" ) {
                Toast.makeText(applicationContext, "The Machine Won (Red)", Toast.LENGTH_LONG).show()
                gameFinished = true
                textView.visibility = View.VISIBLE
                playAgain.visibility = View.VISIBLE
                return
            }
            if (sum == "hhh") {
                Toast.makeText(applicationContext, "The Human Won (Yellow)", Toast.LENGTH_LONG).show()
                gameFinished = true
                textView.visibility = View.VISIBLE
                playAgain.visibility = View.VISIBLE
                return
            }

        }//win
        for (i in 0..8) {
            if (gameState[i] == "") return
        }
        Toast.makeText(applicationContext, "Tie Game", Toast.LENGTH_LONG).show()
        gameFinished = true

        if (gameFinished) {
            textView.visibility = View.VISIBLE
            playAgain.visibility = View.VISIBLE
        }
    }//status

     private fun makeTheBestPlay(): Int {
         var priority = "none"
        var position = 4
        while (gameState[position] != "") position = (0..8).random()
        for (win in winningPositions) {
            val a = gameState[win[0]]
            val b = gameState[win[1]]
            val c = gameState[win[2]]
            val temp = arrayOf(a, b, c)
            val sum = a + b + c
            when (sum) {
                "mm" -> { for (pos in 0..2) {
                                if (temp[pos] == "")
                                {
                                    if (pos == 0){
                                        position = win[0]
                                    }

                                    if (pos == 1){
                                        position = win[1]
                                    }

                                    if (pos == 2){
                                        position = win[2]
                                    }
                                    priority = "high"
                                }

                            }

                        }

                "hh" -> { for (pos in 0..2) {
                            if (temp[pos] == "")
                                {
                                   if (pos == 0){
                                       position = win[0]
                                   }

                                    if (pos == 1){
                                        position = win[1]
                                    }

                                    if (pos == 2){
                                        position = win[2]
                                    }
                                    priority = "mid"
                                }
                            }
                        }

                "m" -> { if (priority != "mid"){
                                for (pos in 0..2) {
                                    if (temp[pos] == "m")
                                    {
                                        if (pos == 0){
                                            position = listOf(win[1],win[2]).random()
                                        }

                                        if (pos == 1){
                                            position = listOf(win[0],win[2]).random()
                                        }

                                        if (pos == 2){
                                            position = listOf(win[1],win[0]).random()
                                        }
                                    }
                                }

                            }
                }
            }
            if (priority == "high") { break }
        }
        return position
    }
}//StartGame