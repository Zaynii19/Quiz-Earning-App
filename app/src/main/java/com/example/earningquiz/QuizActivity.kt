package com.example.earningquiz

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.earningquiz.databinding.ActivityQuizBinding
import com.example.earningquiz.fragments.WithdrawalFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.database.getValue
import com.google.firebase.firestore.firestore

class QuizActivity : AppCompatActivity() {
    private val binding: ActivityQuizBinding by lazy {
        ActivityQuizBinding.inflate(layoutInflater)
    }

    //List of Questions
    private lateinit var questionList: ArrayList<QuizModel>
    var currentQuestion = 0
    var score = 0

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

        questionList = ArrayList()

        //Set Image of every category
        val image = intent.getIntExtra("categoryImg", 0)
        binding.categoryImg.setImageResource(image)

        //Reading Questions (Category type) from Firebase database and setting as quiz questions
        val catText = intent.getStringExtra("quizType")
        // Loop through each question collection
        for (i in 1..10) {
            Firebase.firestore.collection("questions")
                .document(catText.toString())
                .collection("question$i").get().addOnSuccessListener { questionData ->
                    // Loop through each document in the collection
                    for (data in questionData.documents) {
                        val question: QuizModel? = data.toObject(QuizModel::class.java)
                        question?.let {
                            questionList.add(it)
                        }
                    }

                    if (questionList.isNotEmpty()) {
                        binding.quesCount.text = "Question $currentQuestion"

                        binding.question.text = questionList[currentQuestion].question
                        binding.opt1.text = questionList[currentQuestion].opt1
                        binding.opt2.text = questionList[currentQuestion].opt2
                        binding.opt3.text = questionList[currentQuestion].opt3
                        binding.opt4.text = questionList[currentQuestion].opt4
                    }
                }
        }

        binding.opt1.setOnClickListener {
            nextQuesAndScoreUpdate(binding.opt1.text.toString())
        }
        binding.opt2.setOnClickListener {
            nextQuesAndScoreUpdate(binding.opt2.text.toString())
        }
        binding.opt3.setOnClickListener {
            nextQuesAndScoreUpdate(binding.opt3.text.toString())
        }
        binding.opt4.setOnClickListener {
            nextQuesAndScoreUpdate(binding.opt4.text.toString())
        }

        //Adding bottom Dialog
        binding.coin.setOnClickListener {
            val bottomSheetDialog: BottomSheetDialogFragment = WithdrawalFragment()
            bottomSheetDialog.show(this@QuizActivity.supportFragmentManager, "Test")
            bottomSheetDialog.enterTransition
        }
        binding.score.setOnClickListener {
            val bottomSheetDialog: BottomSheetDialogFragment = WithdrawalFragment()
            bottomSheetDialog.show(this@QuizActivity.supportFragmentManager, "Test")
            bottomSheetDialog.enterTransition
        }

        //Adding Name, coins
        Firebase.database.reference.child("Users").child(Firebase.auth.currentUser!!.uid)
            .addValueEventListener(
                object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val user = snapshot.getValue<UserModel>()
                        binding.uName.text = user?.uName
                    }
                    override fun onCancelled(error: DatabaseError) {
                    }
                })

    }
    //Update the questions and score of user
    @SuppressLint("SetTextI18n")
    private fun nextQuesAndScoreUpdate(optChosen:String) {
        // Correct answer gets 10 points
        if (optChosen == questionList[currentQuestion].ans) {
            score += 10
            Toast.makeText(this, score.toString(), Toast.LENGTH_SHORT).show()
        }

        currentQuestion++

        if (currentQuestion >= questionList.size) {
            if (score >= 60) {
                binding.win.visibility = View.VISIBLE
                binding.winScore.text = score.toString()
            } else {
                binding.lose.visibility = View.VISIBLE
                binding.loseScore.text = score.toString()
            }
        } else {
            // Display next question
            binding.quesCount.text = "Question $currentQuestion"
            binding.question.text = questionList[currentQuestion].question
            binding.opt1.text = questionList[currentQuestion].opt1
            binding.opt2.text = questionList[currentQuestion].opt2
            binding.opt3.text = questionList[currentQuestion].opt3
            binding.opt4.text = questionList[currentQuestion].opt4
        }
    }

}