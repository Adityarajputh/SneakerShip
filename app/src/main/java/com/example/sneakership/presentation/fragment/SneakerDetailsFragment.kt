package com.example.sneakership.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.sneakership.R
import com.example.sneakership.databinding.FragmentSneakerDetailsBinding
import com.example.sneakership.presentation.viewmodel.HomeViewModel

class SneakerDetailsFragment : Fragment() {

    private val mSneakerDetailsBinding by lazy {
        FragmentSneakerDetailsBinding.inflate(layoutInflater)
    }

    private val viewModel by lazy {
        ViewModelProvider(requireActivity())[HomeViewModel::class.java]
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        return mSneakerDetailsBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        setUpObservers()
        viewModel.checkIfSneakerInCart()
    }

    private fun setUpObservers() {
        viewModel.sneakerPresentInCart.observe(requireActivity()){
            if(it){
                mSneakerDetailsBinding.apply {
                    addToCartBtn.text = "Remove From Cart"
                }
            }else{
                mSneakerDetailsBinding.apply {
                    addToCartBtn.text = "Add To Cart"
                }
            }
        }
    }

    private fun initViews() {
        mSneakerDetailsBinding.apply {
            sneakerNameTv.text = viewModel.selectedSneaker.name
            sneakerPriceValueTv.text = viewModel.selectedSneaker.retailPrice.toString()
            Glide.with(root)
                .load(viewModel.selectedSneaker.media.imageUrl)
                .into(sneakerIv)

            size8Btn.setOnClickListener {
                it.setBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.md_theme_light_secondaryContainer
                    )
                )
                updateSizeBtnUI("8")
            }

            size7Btn.setOnClickListener {
                it.setBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.md_theme_light_secondaryContainer
                    )
                )
                updateSizeBtnUI("7")

            }

            size9Btn.setOnClickListener {
                it.setBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.md_theme_light_secondaryContainer
                    )
                )
                updateSizeBtnUI("9")
            }

            color1Cv.setOnClickListener {
                updateColorSelectionUI("C1")
            }

            color2Cv.setOnClickListener {
                updateColorSelectionUI("C2")
            }

            color3Cv.setOnClickListener {
                updateColorSelectionUI("C3")
            }

            addToCartBtn.setOnClickListener {
                if(addToCartBtn.text.equals("Add To Cart")){
                    viewModel.addSneakerToCart()
                    addToCartBtn.text = "Remove From Cart"
                }else{
                    viewModel.removeSneakerFromCart(viewModel.selectedSneaker.id)
                    addToCartBtn.text = "Add To Cart"
                }
            }
        }
    }

    private fun updateColorSelectionUI(s: String) {
        mSneakerDetailsBinding.apply {
            when (s) {
                "C1" -> {
                    tickImg.visibility = VISIBLE
                    tickImg2.visibility = GONE
                    tickImg3.visibility = GONE
                }
                "C2" -> {
                    tickImg.visibility = GONE
                    tickImg2.visibility = VISIBLE
                    tickImg3.visibility = GONE
                }
                "C3" -> {
                    tickImg.visibility = GONE
                    tickImg2.visibility = GONE
                    tickImg3.visibility = VISIBLE
                }
            }

        }

    }

    private fun updateSizeBtnUI(selectedBtn: String) {
        mSneakerDetailsBinding.apply {
            when (selectedBtn) {
                "7" -> {
                    size8Btn.setBackgroundColor(
                        ContextCompat.getColor(
                            requireContext(),
                            android.R.color.transparent
                        )
                    )
                    size9Btn.setBackgroundColor(
                        ContextCompat.getColor(
                            requireContext(),
                            android.R.color.transparent
                        )
                    )
                }

                "8" -> {
                    size7Btn.setBackgroundColor(
                        ContextCompat.getColor(
                            requireContext(),
                            android.R.color.transparent
                        )
                    )
                    size9Btn.setBackgroundColor(
                        ContextCompat.getColor(
                            requireContext(),
                            android.R.color.transparent
                        )
                    )
                }

                "9" -> {
                    size7Btn.setBackgroundColor(
                        ContextCompat.getColor(
                            requireContext(),
                            android.R.color.transparent
                        )
                    )
                    size8Btn.setBackgroundColor(
                        ContextCompat.getColor(
                            requireContext(),
                            android.R.color.transparent
                        )
                    )
                }
            }
        }
    }
}