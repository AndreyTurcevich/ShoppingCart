package com.vironit.shoppingcart.ui.info

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.vironit.shoppingcart.R
import kotlinx.android.synthetic.main.fragment_info.*
import kotlinx.android.synthetic.main.fragment_product_details.toolbar

class InfoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_info, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupActionBar()
        setupAboutText()
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

    private fun setupAboutText() {
        val aboutText = getString(R.string.aboutProjectText)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            tvAbout.text = Html.fromHtml(
                aboutText,
                Html.FROM_HTML_MODE_COMPACT
            )
        } else {
            tvAbout.text = Html.fromHtml(aboutText)
        }
    }
}