package com.example.earningquiz

import android.os.Bundle
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
                        binding.question.text = questionList[0].question
                        binding.opt1.text = questionList[0].opt1
                        binding.opt2.text = questionList[0].opt2
                        binding.opt3.text = questionList[0].opt3
                        binding.opt4.text = questionList[0].opt4
                    }
                }
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
}