package com.example.sneakers

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.example.sneakers.cart.CartAdapter
import com.example.sneakers.cart.CartItemClickListener
import com.example.sneakers.cart.CartViewModel
import com.example.sneakers.cart.CartViewModelFactory
import com.example.sneakers.databinding.FragmentCartBinding
import com.example.sneakers.home.HomeActivity
import com.example.sneakers.home.HomeViewModel
import com.example.sneakers.home.HomeViewModelFactory
import com.example.sneakers.home.SneakerAdapter
import com.example.sneakers.roomdatabase.Cart

class CartFragment : Fragment() {

    lateinit var binding: FragmentCartBinding
    var sneakers = ArrayList<Cart>()
    lateinit var cartAdapter: CartAdapter
    lateinit var viewModel: CartViewModel
    var subTotal: Int = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentCartBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleToolbar()
        setAdapter()
        viewModel = ViewModelProvider(
            this, CartViewModelFactory(SneakerRepository(requireContext()))
        )[CartViewModel::class.java]
        viewModel.getCart()
        getCartData()

    }

    fun getCartData() {
        viewModel.cart.observe(requireActivity()) {
            sneakers.clear()
            sneakers.addAll(it)
            for (item in sneakers) {
                subTotal += (item.retailPrice ?: 0)
            }
            cartAdapter.notifyDataSetChanged()
            updateTotal()
            updateEmptyUI()
        }
    }

    fun updateTotal(price: Int = 0) {
        subTotal -= price
        val taxes = subTotal * 5 / 100
        with(binding) {
            tvSubTotal.text = getString(R.string.subtotal_price, subTotal.toString())
            tvTaxes.text = getString(R.string.taxes_price, taxes.toString())
            tvTotalPrice.text = getString(R.string.set_price, (subTotal + taxes).toString())
        }
    }

    fun handleToolbar() {
        with(binding.toolbar) {
            titleToolbar.visibility = View.VISIBLE
            tvName.text = getString(R.string.cart)
            toolbarBackIcon.setOnClickListener(clickListener)
        }
    }

    private val clickListener = View.OnClickListener {
        when (it.id) {
            R.id.toolbar_back_icon -> {
                (activity as HomeActivity).openHomeFragmentFromCart()
            }
        }
    }

    fun setAdapter() {
        cartAdapter = CartAdapter(requireContext(), sneakers, object : CartItemClickListener {
            override fun onItemClick(item: Cart) {
                viewModel.removeFromCart(item)
                updateTotal(item.retailPrice ?: 0)
                updateEmptyUI()
            }
        })
        binding.rvCart.adapter = cartAdapter
    }

    fun updateEmptyUI() {
        if (sneakers.size == 0) {
            binding.tvEmpty.visibility = View.VISIBLE
            binding.details.visibility = View.GONE
        } else {
            binding.tvEmpty.visibility = View.GONE
            binding.details.visibility = View.VISIBLE
        }
    }
}