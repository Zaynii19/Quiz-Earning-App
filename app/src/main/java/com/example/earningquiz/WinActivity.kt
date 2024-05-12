package com.example.earningquiz

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.earningquiz.databinding.ActivityWinBinding
import com.example.earningquiz.fragments.SpinFragment
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.database

class WinActivity : AppCompatActivity() {
    private val binding:ActivityWinBinding by lazy {
        ActivityWinBinding.inflate(layoutInflater)
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //Show User Score
        val winScore = intent.getIntExtra("WinnerScore", 0)
        binding.winScore.text = "Your Score is: $winScore"
    }
}