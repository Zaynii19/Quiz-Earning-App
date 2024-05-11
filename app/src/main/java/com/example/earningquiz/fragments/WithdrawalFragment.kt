package com.example.earningquiz.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.earningquiz.R
import com.example.earningquiz.databinding.FragmentWithdrawalBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class WithdrawalFragment : BottomSheetDialogFragment() {

    private val binding:FragmentWithdrawalBinding by lazy {
        FragmentWithdrawalBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return binding.root
    }

    companion object {
    }
}