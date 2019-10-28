package com.vironit.shoppingcart.ui.list

import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.vironit.shoppingcart.App
import com.vironit.shoppingcart.R
import com.vironit.shoppingcart.ui.details.ProductDetailsFragment.Companion.PRODUCT_ID
import com.vironit.shoppingcart.ui.list.adapter.ItemClickListener
import com.vironit.shoppingcart.ui.list.adapter.ProductsAdapter
import com.vironit.shoppingcart.ui.list.adapter.ProductsAdapter.Companion.SPANS_COUNT
import com.vironit.shoppingcart.utils.isInternetEnabled
import kotlinx.android.synthetic.main.fragment_products_list.*

class ProductsListFragment : Fragment() {

    private lateinit var viewModel: ProductsListViewModel

    private val adapter by lazy { ProductsAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_products_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel =
            ViewModelProviders.of(this).get(ProductsListViewModel::class.java)
        viewModel.database = App.instance.database
        fetchData()
        setupObservers()
        setupRecyclerView()
        setupRetryButton()
        setupInfoButton()
    }

    private fun fetchData() {
        if (viewModel.productsListLiveData.value.isNullOrEmpty()) {
            context?.let {
                viewModel.fetchProductsList(it.isInternetEnabled())
            }
        }
    }

    private fun setupObservers() {
        viewModel.productsListLiveData.observe(
            this,
            Observer {
                if (it.size > 0) {
                    adapter.items = it
                    tvMessage.visibility = View.GONE
                    btnRetry.visibility = View.GONE
                } else {
                    tvMessage.visibility = View.VISIBLE
                    btnRetry.visibility = View.VISIBLE
                    tvMessage.text = getString(R.string.noDataAvailable)
                }
            }
        )
        viewModel.isLoading.observe(
            this,
            Observer {
                pbLoading.isVisible = it
            }
        )
    }

    private fun setupRecyclerView() {
        val staggeredGridLayoutManager = GridLayoutManager(context, SPANS_COUNT)
        rvProducts.layoutManager = staggeredGridLayoutManager
        rvProducts.adapter = adapter
        adapter.screenWidth = Resources.getSystem().displayMetrics.widthPixels
        adapter.itemClickListener = object : ItemClickListener {
            override fun onItemClicked(position: Int) {
                val bundle =
                    bundleOf(PRODUCT_ID to viewModel.productsListLiveData.value?.get(position)?.productId)
                findNavController().navigate(R.id.navigateToProductDetailsFragment, bundle)
            }
        }
    }

    private fun setupRetryButton() {
        btnRetry.setOnClickListener {
            tvMessage.visibility = View.GONE
            btnRetry.visibility = View.GONE
            fetchData()
        }
    }

    private fun setupInfoButton() {
        toolbar.findViewById<ImageButton>(R.id.btnInfo).setOnClickListener {
            findNavController().navigate(R.id.navigateToInfoFragment)
        }
    }

}