package com.example.sneakership.utils.interfaces

import com.example.sneakership.data.model.SneakerItem

interface OnSneakerSelectedCallBack {
    fun onItemSelected(sneaker : SneakerItem)
    fun onAddToCartClicked(sneaker: SneakerItem)
}