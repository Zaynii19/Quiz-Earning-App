package com.example.earningquiz.history_rcv

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.earningquiz.databinding.HistoryItemsBinding
import com.google.firebase.Timestamp
import java.util.Date

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
        holder.binding.hScore.text = listHistory[position].historyCoins
        /*val timestamp = Timestamp(listHistory[position].timeDate.toLong())
        holder.binding.timeDate.text = Date(timestamp.time).toString()*/
        holder.binding.timeDate.text = listHistory[position].timeDate
        if (listHistory[position].isWithdrawl){
            holder.binding.earnedCointxt.text = "Withdrawl Coins"
        }else{
            holder.binding.earnedCointxt.text = "Earned Coins"
        }
    }
}