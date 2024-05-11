package com.example.earningquiz.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

class HistoryFragment : Fragment() {

    val binding:FragmentHistoryBinding by lazy {
        FragmentHistoryBinding.inflate(layoutInflater)
    }

    private var listHistory = ArrayList<RvHistoryModel>()


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
        listHistory.add(RvHistoryModel("12:30", "+300"))
        listHistory.add(RvHistoryModel("01:40", "+700"))
        listHistory.add(RvHistoryModel("02:12", "+500"))
        listHistory.add(RvHistoryModel("04:23", "+1000"))
        binding.rcv.layoutManager = LinearLayoutManager(requireContext())
        val adapter = HistoryRvAdapter(listHistory)
        binding.rcv.adapter = adapter
        binding.rcv.setHasFixedSize(true)
    }

    companion object {

    }
}