package com.example.earningquiz

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.earningquiz.databinding.ActivityLoseBinding
import com.example.earningquiz.databinding.ActivityQuizBinding
import com.example.earningquiz.databinding.ActivityWinBinding
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
    var currentChance = 0L

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
            .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val user = snapshot.getValue<UserModel>()
                        binding.uName.text = user?.uName
                    }
                    override fun onCancelled(error: DatabaseError) {
                    }

            })

        //Get already existing spin Chance Value from database
        Firebase.database.reference.child("SpinChance")
            .child(Firebase.auth.currentUser!!.uid)
            .addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot : DataSnapshot) {
                    if (snapshot.exists()){
                        currentChance = snapshot.value as Long
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                }

            })

        //Retrieve user Coins from database
        Firebase.database.reference.child("UserCoins").child(Firebase.auth.currentUser!!.uid)
            .addValueEventListener(
                object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.exists()){
                            val currentCoins = snapshot.value as Long
                            binding.score.text = currentCoins.toString()
                        }else{
                            binding.score.text = "0"
                        }
                    }
                    override fun onCancelled(error: DatabaseError) {
                    }
                }
            )

    }

    //Update the questions and score of user
    @SuppressLint("SetTextI18n")
    private fun nextQuesAndScoreUpdate(optChosen:String) {
        // Correct answer gets 10 points
        if (optChosen == questionList[currentQuestion].ans) {
            score += 10
        }

        currentQuestion++

        if (currentQuestion >= questionList.size) {
            if (score >= 60) {
                //Update User chances for spin to database
                Firebase.database.reference.child("SpinChance")
                    .child(Firebase.auth.currentUser!!.uid)
                    .setValue(currentChance + 1)

                val intent = Intent(this, WinActivity::class.java)
                intent.putExtra("WinnerScore", score)
                startActivity(intent)

            } else {
                val intent = Intent(this, LoseActivity::class.java)
                intent.putExtra("loserScore", score)
                startActivity(intent)
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