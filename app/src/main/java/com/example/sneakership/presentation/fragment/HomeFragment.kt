package com.example.sneakership.presentation.fragment

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.example.sneakership.presentation.viewmodel.HomeViewModel
import com.example.sneakership.R
import com.example.sneakership.presentation.adapter.SneakersAdapter
import com.example.sneakership.data.model.SneakerItem
import com.example.sneakership.databinding.FragmentHomeBinding
import com.example.sneakership.utils.interfaces.OnSneakerSelectedCallBack
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(), OnSneakerSelectedCallBack {

    private val mHomeBinding : FragmentHomeBinding by lazy {
        FragmentHomeBinding.inflate(layoutInflater)
    }

    private val sneakersAdapter : SneakersAdapter by lazy {
        SneakersAdapter(this)
    }

    private val viewModel : HomeViewModel by lazy {
        ViewModelProvider(requireActivity())[HomeViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return mHomeBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        setUpObservers()
        viewModel.getSneakersData()
    }

    private fun setUpObservers() {
        viewModel.sneakerData.observe(requireActivity()) {
            sneakersAdapter.updateSneakerList(it)
        }

        viewModel.errorData.observe(requireActivity()) {
            Toast.makeText(requireContext(),"No Data Found",Toast.LENGTH_SHORT).show()
        }
    }

    private fun initView() {
        mHomeBinding.apply {
            recView.layoutManager =
                GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
            recView.hasFixedSize()
            recView.adapter = sneakersAdapter

            searchButton.setOnClickListener {
                hideSearchBar(false)
                searchBar.requestFocus()
                showKeyboard()
            }

            btnRemvText.setOnClickListener {
                mHomeBinding.searchBar.setText("")
                hideKeyboard()
                hideSearchBar(true)
                viewModel.getSneakersData()
            }

            searchBar.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    val query = p0.toString()
                    viewModel.getSearchedSneakers(query)
                }

                override fun afterTextChanged(p0: Editable?) {
                }
            })

        }
    }

    private fun hideSearchBar(hideSearchBar: Boolean){
        if (hideSearchBar){
            mHomeBinding.searchBar.visibility = GONE
            mHomeBinding.btnRemvText.visibility = GONE
            mHomeBinding.titleText.visibility = VISIBLE
            mHomeBinding.searchButton.visibility = VISIBLE
        }else{
            // showing the search bar
            mHomeBinding.searchBar.visibility = VISIBLE
            mHomeBinding.btnRemvText.visibility = VISIBLE
            mHomeBinding.titleText.visibility = GONE
            mHomeBinding.searchButton.visibility = GONE
        }
    }

    override fun onItemSelected(sneaker: SneakerItem) {
        viewModel.selectedSneaker = sneaker
        Navigation.findNavController(mHomeBinding.root).navigate(R.id.action_homeFragment_to_sneakerDetailsFragment)
    }

    override fun onAddToCartClicked(sneaker: SneakerItem) {
        viewModel.addSneakerToCart(sneaker)
        Toast.makeText(requireContext(),"Sneaker Added To Cart",Toast.LENGTH_SHORT).show()
    }

    private fun showKeyboard() {
        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(mHomeBinding.searchBar, InputMethodManager.SHOW_IMPLICIT)
    }

    private fun hideKeyboard() {
        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }

}