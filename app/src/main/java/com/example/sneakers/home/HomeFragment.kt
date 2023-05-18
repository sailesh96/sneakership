package com.example.sneakers.home

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.sneakers.ItemClickListener
import com.example.sneakers.R
import com.example.sneakers.SneakerModel
import com.example.sneakers.SneakerRepository
import com.example.sneakers.databinding.FragmentHomeBinding
import com.example.sneakers.cartdatabase.Cart

class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    var sneakers = ArrayList<SneakerModel>()
    var searchedSneakers = ArrayList<SneakerModel>()
    lateinit var viewModel: HomeViewModel
    lateinit var sneakerAdapter: SneakerAdapter
    var cart = ArrayList<Cart>()
    var cartMap = HashMap<String, Boolean>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleToolbar()
        setAdapter()
        viewModel = ViewModelProvider(
            this, HomeViewModelFactory(SneakerRepository(requireContext()))
        ).get(HomeViewModel::class.java)
        getSneakers()
    }

    fun getCartModel(item: SneakerModel): Cart {
        return Cart(
            id = item.id,
            name = item.name,
            title = item.title,
            retailPrice = item.retailPrice,
            size = item.size,
            color = item.colorway,
            image = item.media?.imageUrl
        )
    }

    fun setAdapter() {
        sneakerAdapter =
            SneakerAdapter(requireContext(), searchedSneakers, object : ItemClickListener {
                override fun onItemClick(item: SneakerModel, isCart: Boolean) {
                    if (isCart) {
                        if (item.addedToCart) {
                            viewModel.addToCart(getCartModel(item))
                        } else {
                            viewModel.removeFromCart(getCartModel(item))
                        }
                    } else {
                        val bundle = Bundle()
                        bundle.putSerializable("data", item)
                        (activity as HomeActivity).openDetailFragment(bundle)
                    }

                }
            })
        binding.rvSneakers.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvSneakers.adapter = sneakerAdapter
    }

    private fun getSneakers() {
        viewModel.sneakers.observe(requireActivity()) {
            sneakers.clear()
            searchedSneakers.clear()
            sneakers.addAll(it)
            for (i in 0 until sneakers.size) {
                if (cartMap.containsKey(sneakers[i].id)) {
                    sneakers[i].addedToCart = true
                }
            }
            searchedSneakers.addAll(sneakers)
            sneakerAdapter.notifyDataSetChanged()
        }

        viewModel.cart.observe(requireActivity()) {
            cart.clear()
            cart.addAll(it)
            for (item in it) {
                cartMap[item.id!!] = true
            }
            viewModel.getSneakers()
        }
    }

    private fun handleToolbar() {
        with(binding.toolbar) {
            mainScreen.visibility = View.VISIBLE
            etSearch.addTextChangedListener(searchWatcher)
            ivSearch.setOnClickListener(clickListener)
            ivClearAll.setOnClickListener(clickListener)
        }
    }

    private val searchWatcher = object : TextWatcher {
        override fun onTextChanged(search: CharSequence?, p1: Int, p2: Int, p3: Int) {
            searchedSneakers.clear()
            for (item in sneakers) {
                if (item.name?.contains(search ?: "", true) == true) {
                    Log.i("data_", item.name.toString())
                    searchedSneakers.add(item)
                }
            }
            if (search.isNullOrEmpty()) {
                searchedSneakers.clear()
                searchedSneakers.addAll(sneakers)
            }
            handleVisibilityText()
            sneakerAdapter.notifyDataSetChanged()
        }

        override fun afterTextChanged(p0: Editable?) {
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }
    }

    fun handleVisibilityText(){
        if (searchedSneakers.isEmpty()) {
            binding.tvEmpty.visibility = View.VISIBLE
        } else {
            binding.tvEmpty.visibility = View.GONE
        }
    }
    private val clickListener = View.OnClickListener {
        when (it.id) {
            R.id.iv_search -> {
                with(binding.toolbar) {
                    mainScreen.visibility = View.GONE
                    searchView.visibility = View.VISIBLE
                }
            }

            R.id.iv_clearAll -> {
                with(binding.toolbar) {
                    etSearch.setText("")
                    mainScreen.visibility = View.VISIBLE
                    searchView.visibility = View.GONE
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        cartMap.clear()
        viewModel.getCart()
        (activity as HomeActivity).handleTabUI(true)
        handleVisibilityText()
    }
}