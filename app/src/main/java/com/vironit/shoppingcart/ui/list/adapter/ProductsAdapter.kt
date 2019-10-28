package com.vironit.shoppingcart.ui.list.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory
import com.vironit.shoppingcart.R
import com.vironit.shoppingcart.model.Product

class ProductsAdapter : RecyclerView.Adapter<ProductsAdapter.MainHolder>() {

    lateinit var itemClickListener: ItemClickListener

    var screenWidth: Int? = null

    var items: List<Product> = arrayListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        MainHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_product,
                parent,
                false
            )
        )

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class MainHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val ivProduct = itemView.findViewById<ImageView>(R.id.ivProduct)
        private val tvProductName = itemView.findViewById<TextView>(R.id.tvProductName)
        private val tvProductPrice = itemView.findViewById<TextView>(R.id.tvProductPrice)

        fun bind(item: Product) {
            item.image?.let { ivProduct.loadImage(it) }
            tvProductName.text = item.name
            tvProductPrice.text = item.price.toString()
            itemView.setOnClickListener {
                if (::itemClickListener.isInitialized
                    && adapterPosition != RecyclerView.NO_POSITION
                ) {
                    itemClickListener.onItemClicked(adapterPosition)
                }
            }
        }
    }

    fun ImageView.loadImage(imageUrl: String) {
        val width = if (screenWidth != null) {
            screenWidth?.div(SPANS_COUNT)!!
        } else {
            DEFAULT_IMAGE_SIZE
        }
        val radius = context.resources.getDimensionPixelSize(R.dimen.imageListCornerRadius)
        val factory = DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build()
        Glide
            .with(context)
            .load(imageUrl)
            .transition(withCrossFade(factory))
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .transform(CenterCrop(), RoundedCorners(radius))
            .override(width, width)
            .error(R.drawable.ic_error_placeholder)
            .into(this)
    }

    companion object {
        const val DEFAULT_IMAGE_SIZE = 250
        const val SPANS_COUNT = 2
    }

}