package com.example.sneakership.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sneakership.presentation.viewmodel.HomeViewModel
import com.example.sneakership.databinding.FragmentCheckOutDetailsBinding
import com.example.sneakership.presentation.adapter.CheckOutDetailsAdapter
import com.example.sneakership.utils.interfaces.RemoveSneakerCallBack


class CheckOutDetailsFragment : Fragment(), RemoveSneakerCallBack {

    private val mBinding by lazy {
        FragmentCheckOutDetailsBinding.inflate(layoutInflater)
    }

    private val checkOutDetailsAdapter by lazy {
        CheckOutDetailsAdapter(this)
    }

    private val viewModel by lazy {
        ViewModelProvider(requireActivity())[HomeViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        setUpObservers()
        viewModel.getSneakersInCart()
    }

    private fun setUpObservers() {
        viewModel.sneakersInCartLiveData.observe(requireActivity()){
            if(it.isEmpty()){
                checkOutDetailsAdapter.updateSneakerList(it)
                mBinding.orderDetailsContainer.visibility = GONE
                mBinding.recView.visibility = GONE
                mBinding.errorText.visibility = VISIBLE
            }else{
                checkOutDetailsAdapter.updateSneakerList(it)
                calculateTotalSum(it.map { item -> item.retailPrice })
                mBinding.orderDetailsContainer.visibility = VISIBLE
                mBinding.recView.visibility = VISIBLE
                mBinding.errorText.visibility = GONE

            }
        }
    }

    private fun calculateTotalSum(map: List<Int>) {
        val subTotal = map.sum()
        mBinding.subTotalValueText.text = "$ $subTotal"
        mBinding.totalAmountValueText.text = "$ ${subTotal + 40}"
    }

    private fun initView() {
        mBinding.apply {
            recView.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            recView.hasFixedSize()
            recView.adapter = checkOutDetailsAdapter

            btnCheckOut.setOnClickListener {
                Toast.makeText(requireContext(),"Do CheckOut Operations",Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun cartItemRemoved(id :String,position : Int) {
        viewModel.removeSneakerFromCart(id)
    }
}