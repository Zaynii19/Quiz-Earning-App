package com.example.earningquiz.fragments

import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.earningquiz.UserModel
import com.example.earningquiz.databinding.FragmentSpinBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.database.getValue
import kotlin.random.Random


class SpinFragment : Fragment() {

    private val binding: FragmentSpinBinding by lazy {
        FragmentSpinBinding.inflate(layoutInflater)
    }

    private lateinit var timer: CountDownTimer
    private val itemTitles = arrayListOf("100", "Try Again", "200", "Try Again", "500", "Try Again")
    var leftChance = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding.coin.setOnClickListener {
            val bottomSheetDialog: BottomSheetDialogFragment = WithdrawalFragment()
            bottomSheetDialog.show(requireActivity().supportFragmentManager, "Test")
            bottomSheetDialog.enterTransition
        }
        binding.score.setOnClickListener {
            val bottomSheetDialog: BottomSheetDialogFragment = WithdrawalFragment()
            bottomSheetDialog.show(requireActivity().supportFragmentManager, "Test")
            bottomSheetDialog.enterTransition
        }

        ////Retrieve user name from database
        Firebase.database.reference.child("Users").child(Firebase.auth.currentUser!!.uid)
            .addValueEventListener(
                object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {

                        val user = snapshot.getValue<UserModel>()
                        binding.uName.text = user?.uName
                    }
                    override fun onCancelled(error: DatabaseError) {
                    }
                }
            )

        //Retrieve user spin chances from database
        Firebase.database.reference.child("SpinChance").child(Firebase.auth.currentUser!!.uid)
            .addValueEventListener(
                object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.exists()){
                            leftChance = snapshot.value as Long
                            binding.chance.text = "Left Chance: ${leftChance}"
                        }else{
                            binding.chance.text = "Left Chance: 0"
                            binding.spin.isEnabled = false
                        }
                    }
                    override fun onCancelled(error: DatabaseError) {
                    }
                }
            )

        // Inflate the layout for this fragment
        return binding.root
    }

    private fun showResult(itemTitle: String) {
        Toast.makeText(requireContext(), itemTitle, Toast.LENGTH_LONG).show()
        //Set updated value of spinner chance after spin
        Firebase.database.reference.child("SpinChance")
            .child(Firebase.auth.currentUser!!.uid)
            .setValue(leftChance - 1)
        binding.spin.isEnabled = true //Enable the button spin
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.spin.setOnClickListener {
            if (leftChance>0){
                binding.spin.isEnabled = false //Disable the button while the wheel is spinning

                val spin = Random.nextInt(6) //Generate a random value between 0 to 6
                val degrees = 60f * spin //Calculate the rotation degree based on the random value

                timer = object : CountDownTimer(10000, 50) {
                    var rotation = 0f
                    override fun onTick(millisUntilFinished: Long) {
                        rotation += 5f //Rotate the wheel
                        if (rotation >= degrees) {
                            rotation = degrees
                            timer.cancel()
                            showResult(itemTitles[spin])
                        }

                        binding.wheelStoper.rotation = rotation

                    }

                    override fun onFinish() {}
                }.start()
            }else{
                binding.spin.isEnabled = false //Disable the button when chance < 0
            }

        }
    }

    companion object {
    }
}