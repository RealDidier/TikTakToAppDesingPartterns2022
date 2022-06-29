package com.example.tictactoe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }//onCreate

    fun humanFirst(view: View){
        val intent = Intent(this, StartGame::class.java)
        intent.putExtra("player", 1)
        startActivity(intent)
    }

    fun machineFirst(view: View){
        val intent = Intent(this, StartGame::class.java)
        intent.putExtra("player", 0)
        startActivity(intent)

    }
}//MainActivity