package com.example.sneakership.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.sneakership.data.model.SneakerItem

@Dao
interface CartDao {

    @Query("SELECT COUNT(*) FROM cart")
    suspend fun getRowCount(): Int

    @Query("SELECT COUNT(*) FROM cart WHERE id = :id")
    suspend fun checkIfSneakerInCart(id : String) : Int

    @Query("SELECT * FROM cart")
    suspend fun getSneakersInCart(): List<SneakerItem>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addSneakerToCart(sneakerItem: SneakerItem)

    @Query("DELETE From cart where id = :id")
    suspend fun deleteSneakerInCart(id : String)

    @Transaction
    suspend fun removeSneakerFromCart(id: String): List<SneakerItem>{
        deleteSneakerInCart(id)
        return getSneakersInCart()
    }

    @Transaction
    suspend fun checkIfSneakerInCartTransaction(id: String) : Boolean{
        return checkIfSneakerInCart(id) > 0
    }
}
