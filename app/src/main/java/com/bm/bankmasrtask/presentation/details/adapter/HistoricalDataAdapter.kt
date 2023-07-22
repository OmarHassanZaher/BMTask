package com.bm.bankmasrtask.presentation.details.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bm.bankmasrtask.data.model.response.HistoricalDataResponse
import com.bm.bankmasrtask.databinding.DetailsItemBinding
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.*
import javax.inject.Inject

class HistoricalDataAdapter @Inject constructor(
    @ApplicationContext val context: Context,

) : RecyclerView.Adapter<HistoricalDataAdapter.ViewHolder>() {
    private lateinit var binding: DetailsItemBinding
    private var historicalList: ArrayList<HistoricalDataResponse> = ArrayList()

    inner class ViewHolder(private val binding: DetailsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val from: String? =null
        val to: String? = null
        fun bind(item: HistoricalDataResponse) {

            binding.apply {
                dateTv.text = item.date
                val fromRate = item.rates?.get(from)
                val toRate = item.rates?.get(to)
                if (fromRate != null && toRate != null) {
                    rateTv.text = "${toRate} $to = ${fromRate} $from"
                } else {
                    rateTv.text = "N/A"
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HistoricalDataAdapter.ViewHolder {
        binding = DetailsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoricalDataAdapter.ViewHolder, position: Int) {
        holder.bind(historicalList[position])
        holder.setIsRecyclable(false)
    }

    override fun getItemCount(): Int {
        return historicalList.size
    }

    fun setData(list: List<HistoricalDataResponse>) {
        if (this.historicalList.isNotEmpty())
            this.historicalList.clear()
        this.historicalList.addAll(list)
        notifyDataSetChanged()
    }
}