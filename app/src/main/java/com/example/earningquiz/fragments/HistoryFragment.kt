package com.example.earningquiz.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.earningquiz.UserModel
import com.example.earningquiz.databinding.FragmentHistoryBinding
import com.example.earningquiz.history_rcv.HistoryRvAdapter
import com.example.earningquiz.history_rcv.RvHistoryModel
import com.example.earningquiz.home_rcv.HomeRvAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.database.getValue
import java.util.Collections

class HistoryFragment : Fragment() {

    val binding:FragmentHistoryBinding by lazy {
        FragmentHistoryBinding.inflate(layoutInflater)
    }

    private var listHistory = ArrayList<RvHistoryModel>()
    lateinit var adapter: HistoryRvAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
                    }                }
            )

        //Retrieve history data from database
        Firebase.database.reference.child("CoinsHistory")
            .child(Firebase.auth.currentUser!!.uid)
            .addValueEventListener(object : ValueEventListener{
                @SuppressLint("NotifyDataSetChanged")
                override fun onDataChange(snapshot: DataSnapshot) {
                    listHistory.clear()
                    if (snapshot.exists()){
                        val listHistory1 = ArrayList<RvHistoryModel>()
                        for (dataSnapshot in snapshot.children){
                            val coinHistoryData = snapshot.getValue<RvHistoryModel>()
                            listHistory1.add(coinHistoryData!!)
                        }

                        listHistory1.reverse()
                        listHistory.addAll(listHistory1)
                        adapter.notifyDataSetChanged()
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                }
            })

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

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rcv.layoutManager = LinearLayoutManager(requireContext())
        adapter = HistoryRvAdapter(listHistory)
        binding.rcv.adapter = adapter
        binding.rcv.setHasFixedSize(true)

    }

    companion object {

    }
}