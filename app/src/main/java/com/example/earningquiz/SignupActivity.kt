package com.example.earningquiz

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.earningquiz.databinding.ActivitySignupBinding
import com.google.firebase.Firebase
import com.google.firebase.app
import com.google.firebase.auth.auth
import com.google.firebase.database.database

class SignupActivity : AppCompatActivity() {
    private val binding:ActivitySignupBinding by lazy {
        ActivitySignupBinding.inflate(layoutInflater)
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

        //click on login text if already signup
        binding.loginText.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        binding.signupBtn.setOnClickListener {
            val name = binding.name.text.toString()
            val age = binding.age.text.toString()
            val email = binding.email.text.toString()
            val pass = binding.pass.text.toString()

            //Checks if fields are empty or not
            if (binding.name.text.toString().equals("") ||
                binding.age.text.toString().equals("") ||
                binding.email.text.toString().equals("") ||
                binding.pass.text.toString().equals(""))
            {
                Toast.makeText(this, "Please Fill All the Details First", Toast.LENGTH_SHORT).show()
            }else{
                Firebase.auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener {
                    if (it.isSuccessful){
                        var user = UserModel(name, age, email, pass)

                        //Storing user data to Firebase Database
                        Firebase.database.reference.child("Users")
                                .child(Firebase.auth.currentUser!!.uid).setValue(user).addOnSuccessListener {  //Individual user in users
                                    startActivity(Intent(this, HomeActivity::class.java))
                                    finish()
                                Toast.makeText(this, "SignUp Successful", Toast.LENGTH_SHORT).show()
                            }
                    }else{
                        Toast.makeText(this, it.exception?.localizedMessage, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

    }

    override fun onStart() {
        super.onStart()
        if (Firebase.auth.currentUser!=null){
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }
    }
}