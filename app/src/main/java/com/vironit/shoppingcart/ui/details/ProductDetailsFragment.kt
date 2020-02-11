package com.vironit.shoppingcart.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory
import com.vironit.shoppingcart.App
import com.vironit.shoppingcart.R
import com.vironit.shoppingcart.data.model.Product
import com.vironit.shoppingcart.utils.isInternetEnabled
import kotlinx.android.synthetic.main.fragment_product_details.*
import kotlinx.android.synthetic.main.fragment_products_list.pbLoading
import kotlinx.android.synthetic.main.item_product.ivProduct
import kotlinx.android.synthetic.main.item_product.tvProductName
import kotlinx.android.synthetic.main.item_product.tvProductPrice

class ProductDetailsFragment : Fragment() {

    private lateinit var viewModel: ProductDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_product_details, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ProductDetailsViewModel::class.java)
        viewModel.database = App.instance.database
        setupActionBar()
        fetchProductDetails()
        setupObservers()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            findNavController().navigate(R.id.navigateToProductsListFragment)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupActionBar() {
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        with((activity as AppCompatActivity).supportActionBar) {
            this?.setDisplayHomeAsUpEnabled(true)
            this?.setDisplayShowHomeEnabled(true)
            this?.title = ""
        }
    }

    private fun setupObservers() {
        viewModel.productLiveData.observe(
            this,
            Observer {
                bindData(it)
            }
        )
        viewModel.isLoading.observe(
            this,
            Observer {
                pbLoading.isVisible = it
            }
        )
    }

    private fun fetchProductDetails() {
        context?.let { context ->
            arguments?.getString(PRODUCT_ID)?.let {
                viewModel.fetchProduct(it, context.isInternetEnabled())
            }
        }
    }

    private fun bindData(product: Product) {
        val factory = DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build()
        Glide
            .with(ivProduct.context)
            .load(product.image)
            .transition(DrawableTransitionOptions.withCrossFade(factory))
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .fitCenter()
            .error(R.drawable.ic_error_placeholder)
            .into(ivProduct)
        tvProductName.text = product.name
        tvProductPrice.text = product.price.toString()
        tvProductDescription.text = product.description
    }

    companion object {
        const val PRODUCT_ID = "PRODUCT_ID"
    }

}