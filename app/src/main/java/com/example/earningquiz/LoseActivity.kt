package com.example.earningquiz

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.earningquiz.databinding.ActivityLoseBinding
import com.example.earningquiz.databinding.ActivityWinBinding

class LoseActivity : AppCompatActivity() {
    private val binding: ActivityLoseBinding by lazy {
        ActivityLoseBinding.inflate(layoutInflater)
    }
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
        val loseScore = intent.getIntExtra("loserScore", 0)
        binding.loseScore.text = "Your Score is: $loseScore"

    }
}