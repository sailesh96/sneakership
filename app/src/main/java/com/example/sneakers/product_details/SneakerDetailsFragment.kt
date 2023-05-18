package com.example.sneakers.product_details

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.sneakers.Constants
import com.example.sneakers.R
import com.example.sneakers.SneakerModel
import com.example.sneakers.SneakerRepository
import com.example.sneakers.databinding.FragmentSneakerDetailsBinding
import com.example.sneakers.home.HomeActivity
import com.example.sneakers.cartdatabase.Cart

class SneakerDetailsFragment : Fragment() {

    lateinit var binding: FragmentSneakerDetailsBinding
    var addedToCart: Boolean = false
    lateinit var sneakerModel: SneakerModel
    lateinit var viewModel: SneakerDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentSneakerDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.titleToolbar.visibility = View.VISIBLE
        handleToolbar()
        viewModel = ViewModelProvider(
            this, SneakerDetailsViewModelFactory(SneakerRepository(requireContext()))
        )[SneakerDetailsViewModel::class.java]
        binding.sneakerDetailsCard.setBackgroundResource(R.drawable.bottom_tab_background)
        sneakerModel = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getSerializable("data", SneakerModel::class.java)!!
        } else {
            arguments?.getSerializable("data") as SneakerModel
        }
        addedToCart = sneakerModel.addedToCart
        setSizeAdapter(sneakerModel.size ?: 0)
        setColorAdapter(sneakerModel.colorway ?: "")
        handleClickListener()
        handleCartButtonUI()
        setData()
    }

    fun handleToolbar() {
        with(binding.toolbar) {
            tvName.text = getString(R.string.product_details)
            toolbarBackIcon.setOnClickListener(clickListener)
        }
    }

    fun setData() {
        binding.tvSneakerName.text = sneakerModel.name
        binding.tvTitle.text = sneakerModel.title
        binding.tvPrice.text =
            getString(R.string.set_price, (sneakerModel.retailPrice ?: 0).toString())
    }

    fun handleClickListener() {
        binding.tvAddToCart.setOnClickListener(clickListener)
    }

    fun setSizeAdapter(size: Int) {
        binding.rvSize.adapter = SneakerSizeAdapter(requireContext(), Constants.SneakerSize(), size)
    }

    fun setColorAdapter(color: String) {
        binding.rvColor.adapter =
            SneakerColorAdapter(requireContext(), Constants.SneakerColors(), color)
    }

    private val clickListener = View.OnClickListener {

        when (it.id) {
            R.id.tv_addToCart -> {
                addedToCart = !addedToCart
                if (!addedToCart) {
                    viewModel.removeFromCart(getCartModel(sneakerModel))
                } else {
                    viewModel.addToCart(getCartModel(sneakerModel))
                }
                handleCartButtonUI()
            }

            R.id.toolbar_back_icon -> {
                (activity as HomeActivity).openHomeFragmentFromDetails()
            }
        }
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

    fun handleCartButtonUI() {
        binding.tvAddToCart.isSelected = !addedToCart
        if (!addedToCart) {
            binding.tvAddToCart.text = getString(R.string.add_to_cart)
        } else {
            binding.tvAddToCart.text = getString(R.string.remove_cart)
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as HomeActivity).handleTabUI(true)
    }


}