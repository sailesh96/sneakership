package com.example.sneakers.home

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import com.example.sneakers.HomeFragment
import com.example.sneakers.R
import com.example.sneakers.SneakerRepository
import com.example.sneakers.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        handleTabUI(true)
        binding.bottomCard.setBackgroundResource(R.drawable.bottom_tab_background)
        handleClickListener()
    }

    fun openDetailFragment(bundle: Bundle) {
        findNavController(R.id.navHost).navigate(
            R.id.action_homeFragment_to_sneakerDetailsFragment,
            bundle
        )
    }

    fun openHomeFragmentFromDetails() {
        findNavController(R.id.navHost).popBackStack()
    }

    fun openHomeFragmentFromCart() {
        findNavController(R.id.navHost).popBackStack()
    }

    fun openCartFromHome() {
        findNavController(R.id.navHost).navigate(
            R.id.action_homeFragment_to_cartFragment
        )
    }

    fun openCartFromDetails() {
        findNavController(R.id.navHost).navigate(
            R.id.action_sneakerDetailsFragment_to_cartFragment
        )
    }

    fun openHomeFragment() {
        findNavController(R.id.navHost).popBackStack()
    }


    fun handleClickListener() {
        with(binding) {
            ivHome.setOnClickListener(clickListener)
            ivCart.setOnClickListener(clickListener)
        }
    }

    private val clickListener = View.OnClickListener {
        when (it.id) {
            R.id.iv_home -> {
                val current = findNavController(R.id.navHost).currentDestination?.id
                if (current != R.id.homeFragment) {
                    openHomeFragment()
                }
                handleTabUI(true)
            }

            R.id.iv_cart -> {
                handleTabUI(false)
                val current = findNavController(R.id.navHost).currentDestination?.id
                if (current == R.id.sneakerDetailsFragment) {
                    openCartFromDetails()
                } else {
                    openCartFromHome()
                }

            }
        }
    }

    fun handleTabUI(isHomeVisible: Boolean) {
        if (isHomeVisible) {
            with(binding) {
                home.setBackgroundResource(R.drawable.circular_background)
                cart.background = null
                ivCart.setColorFilter(
                    ContextCompat.getColor(
                        this@HomeActivity, R.color.colorPrimary
                    ), android.graphics.PorterDuff.Mode.MULTIPLY
                );
                ivHome.setColorFilter(
                    ContextCompat.getColor(this@HomeActivity, R.color.white),
                    android.graphics.PorterDuff.Mode.MULTIPLY
                );
            }
        } else {
            with(binding) {
                home.background = null
                cart.setBackgroundResource(R.drawable.circular_background)
                ivCart.setColorFilter(
                    ContextCompat.getColor(this@HomeActivity, R.color.white),
                    android.graphics.PorterDuff.Mode.MULTIPLY
                );
                ivHome.setColorFilter(
                    ContextCompat.getColor(
                        this@HomeActivity, R.color.colorPrimary
                    ), android.graphics.PorterDuff.Mode.MULTIPLY
                );
            }
        }
    }

    override fun onResume() {
        super.onResume()
    }
}