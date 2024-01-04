package com.example.sneakership.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sneakership.data.repository.SneakerRepository
import com.example.sneakership.data.model.SneakerItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val sneakerRepository: SneakerRepository) :
    ViewModel() {

    val sneakerData: MutableLiveData<List<SneakerItem>> = MutableLiveData()
    val sneakersInCartLiveData: MutableLiveData<List<SneakerItem>> = MutableLiveData()
    val sneakerPresentInCart: MutableLiveData<Boolean> = MutableLiveData()
    val errorData: MutableLiveData<String> = MutableLiveData()
    private var listOfSneakers: List<SneakerItem> = listOf()
    lateinit var selectedSneaker: SneakerItem

    fun getSneakersData() {
        viewModelScope.launch {
            try {
                listOfSneakers = sneakerRepository.fetchSneakerData()
                if (listOfSneakers.isEmpty()) {
                    withContext(Dispatchers.Main) {
                        errorData.value = "ValidationFailed"
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        sneakerData.value = listOfSneakers
                    }
                }
            } catch (ex: Exception) {
                ex.stackTrace
                withContext(Dispatchers.Main) {
                    errorData.value = "Error"
                }
            }
        }
    }

    fun getSearchedSneakers(query : String){
        viewModelScope.launch {
            if(query.isNotEmpty()){
                val filteredList = listOfSneakers.filter { it.name.contains(query,true) }
                sneakerData.postValue(filteredList)
            }else{
                sneakerData.postValue(listOfSneakers)
            }
        }
    }

    fun getSneakersInCart() {
        viewModelScope.launch {
            val sneakersInCart = sneakerRepository.getSneakersInCart()
            sneakersInCartLiveData.postValue(sneakersInCart)
        }
    }

    fun addSneakerToCart() {
        viewModelScope.launch {
            sneakerRepository.addSneakerToCart(selectedSneaker)
        }
    }

    fun addSneakerToCart(sneakerItem: SneakerItem) {
        viewModelScope.launch {
            sneakerRepository.addSneakerToCart(sneakerItem)
        }
    }

    fun removeSneakerFromCart(id: String) {
        viewModelScope.launch {
            val sneakersInCart = sneakerRepository.removeSneakerFromCart(id)
            sneakersInCartLiveData.postValue(sneakersInCart)
        }
    }

    fun checkIfSneakerInCart() {
        viewModelScope.launch {
            val sneakerInCart = sneakerRepository.checkIfSneakerInCart(selectedSneaker.id)
            sneakerPresentInCart.postValue(sneakerInCart)
        }
    }
}