package com.example.sneakership.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sneakership.data.model.SneakerItem
import com.example.sneakership.databinding.CheckOutDetailsItemBinding
import com.example.sneakership.utils.interfaces.RemoveSneakerCallBack

class CheckOutDetailsAdapter (private val removeSneakerCallBack: RemoveSneakerCallBack):
    RecyclerView.Adapter<CheckOutDetailsAdapter.CheckOutDetailsViewHolder>() {
    private var checkOutList: List<SneakerItem> = listOf()

    inner class CheckOutDetailsViewHolder(private val binding: CheckOutDetailsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(sneakerItem: SneakerItem) {
            Glide.with(binding.root)
                .load(sneakerItem.media.imageUrl)
                .into(binding.sneakerImgView)

            binding.sneakerNameText.text = sneakerItem.name
            binding.sneakerPrice.text = "$${sneakerItem.retailPrice}"

            binding.icRemoveSneaker.setOnClickListener {
                removeSneakerCallBack.cartItemRemoved(sneakerItem.id,adapterPosition)
            }

        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): CheckOutDetailsViewHolder {
        return CheckOutDetailsViewHolder(
            CheckOutDetailsItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(
        holder: CheckOutDetailsViewHolder,
        position: Int,
    ) {
        holder.bind(checkOutList[position])
    }

    override fun getItemCount(): Int {
        return checkOutList.size
    }

    fun updateSneakerList(sneakers: List<SneakerItem>) {
        val diffResult = DiffUtil.calculateDiff(DiffCallback(checkOutList, sneakers))
        checkOutList = sneakers
        diffResult.dispatchUpdatesTo(this)
    }

    private class DiffCallback(
        private val oldList: List<SneakerItem>,
        private val newList: List<SneakerItem>,
    ) : DiffUtil.Callback() {

        override fun getOldListSize(): Int = oldList.size
        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }
}