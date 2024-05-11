package com.example.earningquiz.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.earningquiz.R
import com.example.earningquiz.UserModel
import com.example.earningquiz.databinding.FragmentHomeBinding
import com.example.earningquiz.home_rcv.HomeRvAdapter
import com.example.earningquiz.home_rcv.RvCatagoryModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.database.getValue


class HomeFragment : Fragment() {
    private val binding:FragmentHomeBinding by lazy {
        FragmentHomeBinding.inflate(layoutInflater)
    }

    private var catagoryList = ArrayList<RvCatagoryModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        catagoryList.add(RvCatagoryModel(R.drawable.english, "english") )
        catagoryList.add(RvCatagoryModel(R.drawable.sst, "geography") )
        catagoryList.add(RvCatagoryModel(R.drawable.math, "math") )
        catagoryList.add(RvCatagoryModel(R.drawable.science, "science") )

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding.coin.setOnClickListener {
            val bottomSheetDialog:BottomSheetDialogFragment = WithdrawalFragment()
            bottomSheetDialog.show(requireActivity().supportFragmentManager, "Test")
            bottomSheetDialog.enterTransition
        }
        binding.score.setOnClickListener {
            val bottomSheetDialog:BottomSheetDialogFragment = WithdrawalFragment()
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

        return binding.root
    }

    //add data(category) in home fragment
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rcv.layoutManager = GridLayoutManager(requireContext(), 2)
        val adapter = HomeRvAdapter(catagoryList, requireActivity())
        binding.rcv.adapter = adapter
        binding.rcv.setHasFixedSize(true)
    }

    companion object {

    }
}