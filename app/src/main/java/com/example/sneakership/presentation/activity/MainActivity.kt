package com.example.sneakership.presentation.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.activity.OnBackPressedCallback
import androidx.navigation.findNavController
import com.example.sneakership.R
import com.example.sneakership.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val mainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mainBinding.root)
        initViews()

        onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if(mainBinding.cartOrangeIv.visibility == VISIBLE){
                    mainBinding.apply {
                        cartOrangeIv.visibility = GONE
                        homeWhiteCv.visibility = VISIBLE
                        cartWhiteCv.visibility = GONE
                        cartOrangeIv.visibility = VISIBLE
                    }
                }else{
                    mainBinding.apply {
                        homeOrangeIv.visibility = GONE
                        homeWhiteCv.visibility = VISIBLE
                        cartWhiteCv.visibility = GONE
                        cartOrangeIv.visibility = VISIBLE
                    }
                }
            }
        })
    }

    private fun initViews() {
        mainBinding.apply {
            cartOrangeIv.setOnClickListener{
                it.visibility = GONE
                cartWhiteCv.visibility = VISIBLE
                homeWhiteCv.visibility = GONE
                homeOrangeIv.visibility = VISIBLE
                val navController = findNavController(R.id.nav_host_fragment_container)
                navController.navigateUp() // to clear previous navigation history
                navController.navigate(R.id.checkOutDetailsFragment)
            }

            homeOrangeIv.setOnClickListener {
                it.visibility = GONE
                homeWhiteCv.visibility = VISIBLE
                cartWhiteCv.visibility = GONE
                cartOrangeIv.visibility = VISIBLE
                val navController = findNavController(R.id.nav_host_fragment_container)
                navController.navigateUp() // to clear previous navigation history
                navController.navigate(R.id.homeFragment)
            }
        }
    }
}