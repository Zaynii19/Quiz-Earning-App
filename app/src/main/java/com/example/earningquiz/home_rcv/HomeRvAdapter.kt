package com.example.earningquiz.home_rcv

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.*
import com.example.earningquiz.QuizActivity
import com.example.earningquiz.databinding.CatagoryItemBinding

class HomeRvAdapter(var catagoryList: ArrayList<RvCatagoryModel>,var requireActivity: FragmentActivity): Adapter<HomeRvAdapter.MyCatViewHolder>() {
    class MyCatViewHolder(val binding: CatagoryItemBinding):RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyCatViewHolder {
        return MyCatViewHolder(CatagoryItemBinding.inflate(LayoutInflater.from(parent.context),parent, false))
    }

    override fun getItemCount(): Int {
        return catagoryList.size
    }

    override fun onBindViewHolder(holder: MyCatViewHolder, position: Int) {
        //Set Quiz Subject Category
        val datalist = catagoryList[position]
        holder.binding.catagoryImage.setImageResource(datalist.catImage)
        holder.binding.catagoryText.text = datalist.catText

        //To Quiz Activity
        holder.binding.catagoryBtn.setOnClickListener {
            val intent = Intent(requireActivity, QuizActivity::class.java)
            intent.putExtra("categoryImg", datalist.catImage)
            intent.putExtra("quizType", datalist.catText)
            requireActivity.startActivity(intent)
        }
    }

}