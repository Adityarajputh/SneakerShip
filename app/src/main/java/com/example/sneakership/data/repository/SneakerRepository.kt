package com.example.sneakership.data.repository

import android.content.Context
import android.widget.Toast
import com.example.sneakership.data.db.SneakerShipDB
import com.example.sneakership.data.model.SneakerItem
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader
import javax.inject.Inject


class SneakerRepository @Inject constructor(
    private val applicationContext: Context,
    private val sneakerShipDB : SneakerShipDB
) {

    private val cartDao by lazy {
        sneakerShipDB.getCartDao()
    }

    suspend fun fetchSneakerData(): List<SneakerItem> {
        lateinit var sneakerString : String
        withContext(Dispatchers.IO){
            sneakerString = getResponseData(applicationContext)  // TODO : Network call workFlow Should Be Triggered from here based on the policies passed to Repo Layer.
        }
        return Gson().fromJson(sneakerString, Array<SneakerItem>::class.java).toList()
    }

    suspend fun getSneakersInCart(): List<SneakerItem> = withContext(Dispatchers.IO) {
        cartDao.getSneakersInCart()
    }

    suspend fun addSneakerToCart(sneakerItem: SneakerItem) = withContext(Dispatchers.IO) {
        try {
            cartDao.addSneakerToCart(sneakerItem)
        }catch (ex :Exception){
            Toast.makeText(applicationContext,ex.message,Toast.LENGTH_SHORT).show()
        }
    }

    private fun getResponseData(context: Context): String {
        val inputStream = context.resources.assets.open("dataset.json")
        val bufferedReader = BufferedReader(InputStreamReader(inputStream))
        val stringBuilder = StringBuilder()
        var line: String? = bufferedReader.readLine()
        while (line != null) {
            stringBuilder.append(line)
            line = bufferedReader.readLine()
        }
        bufferedReader.close()
        return stringBuilder.toString()
    }

    suspend fun removeSneakerFromCart(id: String) = withContext(Dispatchers.IO) {
        cartDao.removeSneakerFromCart(id)
    }

    suspend fun checkIfSneakerInCart(id: String): Boolean {
        return cartDao.checkIfSneakerInCartTransaction(id)
    }
}