package com.example.sneakership.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sneakership.data.model.SneakerItem
import com.example.sneakership.databinding.SneakerItemBinding
import com.example.sneakership.utils.interfaces.OnSneakerSelectedCallBack

class SneakersAdapter(private val onSneakerSelected : OnSneakerSelectedCallBack) : RecyclerView.Adapter<SneakersAdapter.SneakerViewHolder>(){
    private var sneakerList: List<SneakerItem> = listOf()
    inner class SneakerViewHolder(private val binding: SneakerItemBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(sneakerItem : SneakerItem){
            Glide.with(binding.root)
                .load(sneakerItem.media.imageUrl)
                .into(binding.sneakerImgView)

            binding.sneakerNameText.text = sneakerItem.name
            binding.sneakerPrice.text = "$${sneakerItem.retailPrice}"

            binding.addToCartIcon.setOnClickListener {
                onSneakerSelected.onAddToCartClicked(sneakerItem)
            }


            binding.root.setOnClickListener {
                run {
                    onSneakerSelected.onItemSelected(sneakerItem)
                }
            }
        }
    }

    fun updateSneakerList(sneakers : List<SneakerItem>){
        val diffResult = DiffUtil.calculateDiff(DiffCallback(sneakerList,sneakers))
        sneakerList = sneakers
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SneakerViewHolder {
        return SneakerViewHolder(
            SneakerItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SneakerViewHolder, position: Int) {
        holder.bind(sneakerList[position])
    }

    override fun getItemCount(): Int {
        return sneakerList.size
    }

    private class DiffCallback(
        private val oldList: List<SneakerItem>,
        private val newList: List<SneakerItem>
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