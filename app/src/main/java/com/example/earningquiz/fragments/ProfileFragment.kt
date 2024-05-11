package com.example.earningquiz.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.earningquiz.R
import com.example.earningquiz.UserModel
import com.example.earningquiz.databinding.FragmentProfileBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.database.getValue

class ProfileFragment : Fragment() {

    private val binding:FragmentProfileBinding by lazy {
        FragmentProfileBinding.inflate(layoutInflater)
    }

    var isExpand = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Personal information button Up/Down Function
        binding.pInfoBtn.setOnClickListener {
            if (isExpand){
                binding.expandableconstraintLayout.visibility = View.VISIBLE
                binding.pInfoBtn.setImageResource(R.drawable.arrow_up)
            }
            else{
                binding.expandableconstraintLayout.visibility = View.GONE
                binding.pInfoBtn.setImageResource(R.drawable.arrow_down)
            }

            isExpand = !isExpand
        }

        //Get data from Realtime database
        Firebase.database.reference.child("Users").child(Firebase.auth.currentUser!!.uid)
            .addValueEventListener(
                object : ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {

                            val user = snapshot.getValue<UserModel>()

                            binding.profileName.text = user!!.uName
                            binding.uName.text = user.uName
                            binding.uAge.text = user.uAge.toString()
                            binding.uEmail.text = user.uEmail
                            binding.uPass.text = user.uPass
                        }


                    override fun onCancelled(error: DatabaseError) {

                    }

                }
            )

        //Logout
        /*binding.logout.setOnClickListener {
            startActivity(Intent(this@ProfileFragment, HomeActivity::class.java))
        }*/

        // Inflate the layout for this fragment
        return binding.root
    }

    companion object {

    }
}