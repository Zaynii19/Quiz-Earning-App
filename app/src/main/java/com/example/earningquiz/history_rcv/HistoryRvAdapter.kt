package com.example.earningquiz.history_rcv

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.earningquiz.databinding.HistoryItemsBinding

class HistoryRvAdapter(var listHistory:ArrayList<RvHistoryModel>):RecyclerView.Adapter<HistoryRvAdapter.HistoryViewHolder>() {
    class HistoryViewHolder(val binding: HistoryItemsBinding):RecyclerView.ViewHolder(binding.root)  {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        return HistoryViewHolder(HistoryItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return listHistory.size
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.binding.hScore.text = listHistory[position].historyCoins.toString()
        holder.binding.timeDate.text = listHistory[position].timeDate.toString()
        holder.binding.earnedCointxt.text =
            if (listHistory[position].isWithdrawl) {
                "Withdrawl Coins"
            }else{
                 "Earned Coins"
            }
    }
}