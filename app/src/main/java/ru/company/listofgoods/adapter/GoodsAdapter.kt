package ru.mypackage.myweatherapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.company.listofgoods.BuildConfig
import ru.company.listofgoods.databinding.CardGoodsBinding
import ru.company.listofgoods.dto.DataModel

class GoodsAdapter :
    ListAdapter<DataModel, GoodsViewHolder>(GoodsDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoodsViewHolder {
        val binding = CardGoodsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GoodsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GoodsViewHolder, position: Int) {
        val goods = getItem(position)
        holder.bind(goods)
    }
}

class GoodsViewHolder(
    private val binding: CardGoodsBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(dataModel: DataModel) {
        Glide.with(binding.ivGoods)
            .load("${BuildConfig.BASE_URL}${dataModel.DETAIL_PICTURE}")
            .into(binding.ivGoods)
        binding.tvPrice.text = dataModel.EXTENDED_PRICE[0].PRICE.toString() + " \u20BD"
        binding.tvName.text = dataModel.NAME
    }
}

class GoodsDiffCallback : DiffUtil.ItemCallback<DataModel>() {
    override fun areItemsTheSame(oldItem: DataModel, newItem: DataModel): Boolean {
        return oldItem.ID == newItem.ID
    }

    override fun areContentsTheSame(oldItem: DataModel, newItem: DataModel): Boolean {
        return oldItem == newItem
    }
}