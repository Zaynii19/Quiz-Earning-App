package com.example.earningquiz.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.earningquiz.R
import com.example.earningquiz.databinding.FragmentWithdrawalBinding
import com.example.earningquiz.history_rcv.HistoryRvAdapter
import com.example.earningquiz.history_rcv.RvHistoryModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.database.getValue
import java.util.Date

class WithdrawalFragment : BottomSheetDialogFragment() {

    private val binding:FragmentWithdrawalBinding by lazy {
        FragmentWithdrawalBinding.inflate(layoutInflater)
    }

    var totalCoins = 0L
    var withdrawlCoins = 0L
    //private var listHistory = ArrayList<RvHistoryModel>()
    //lateinit var adapter: HistoryRvAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        //Retrieve user Coins from database
        Firebase.database.reference.child("UserCoins").child(Firebase.auth.currentUser?.uid ?: "")
            .addListenerForSingleValueEvent(
                object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.exists()){
                            totalCoins = snapshot.value as Long
                            binding.totalCoins.text = "Total Coins = ${totalCoins.toString()}"
                        } else {
                            binding.totalCoins.text = "Total Coins = 0"
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        // Handle onCancelled
                    }
                }
            )

        //Retrieve history data from database
        /*Firebase.database.reference.child("CoinsHistory")
            .child(Firebase.auth.currentUser!!.uid)
            .addValueEventListener(object : ValueEventListener{
                @SuppressLint("NotifyDataSetChanged")
                override fun onDataChange(snapshot: DataSnapshot) {
                    listHistory.clear()
                    if (snapshot.exists()){
                        for (dataSnapshot in snapshot.children){
                            val coinHistoryData = snapshot.getValue<RvHistoryModel>()
                            listHistory.add(coinHistoryData!!)
                            adapter.notifyDataSetChanged()
                        }
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                }
            })*/

        binding.withdrawBtn.setOnClickListener {
            val withdrawlAmount = binding.amount.text.toString().toLong()
            withdrawlCoins = withdrawlAmount * 100

            if (totalCoins >= 5000) {
                if (withdrawlCoins > totalCoins || withdrawlCoins > 10000) {
                    Toast.makeText(requireContext(), "You cannot withdraw Amount", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), "$withdrawlAmount Rs Withdrawl Successful", Toast.LENGTH_SHORT).show()

                    //Set updated value of Coins after Withdrawl
                    Firebase.database.reference.child("UserCoins")
                        .child(Firebase.auth.currentUser?.uid ?: "")
                        .setValue(totalCoins - withdrawlCoins)

                    //Set updated value of Coins History
                    val currentDate = Date()
                    val historyModel = RvHistoryModel(currentDate, withdrawlCoins.toInt(), true)
                    Firebase.database.reference.child("CoinsHistory")
                        .child(Firebase.auth.currentUser!!.uid)
                        .push()   //push creates new node each time not update/rewrite previous
                        .setValue(historyModel)
                }
            } else {
                Toast.makeText(requireContext(), "Out of Coins", Toast.LENGTH_SHORT).show()
            }
        }



        return binding.root
    }


    companion object {
    }
}