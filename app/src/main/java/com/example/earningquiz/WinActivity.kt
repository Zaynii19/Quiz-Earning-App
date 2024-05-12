package com.example.earningquiz

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.earningquiz.databinding.ActivityWinBinding
import com.example.earningquiz.fragments.SpinFragment

class WinActivity : AppCompatActivity() {
    private val binding:ActivityWinBinding by lazy {
        ActivityWinBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_win)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //Show User Score
        val winScore = intent.getStringExtra("winnerScore")
        binding.winScore.text = "Your Score is: ${winScore.toString()}"

        binding.toSpin.setOnClickListener{
            val intent = Intent(this, SpinFragment::class.java)
            startActivity(intent)
        }
    }
}