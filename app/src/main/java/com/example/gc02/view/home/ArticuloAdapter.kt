package com.example.gc02.view.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gc02.R
import com.example.gc02.databinding.ShopItemListBinding
import com.example.gc02.model.Article


class ArticuloAdapter(
    private var shops: List<Article>,
    private val onClick: (shop: Article) -> Unit,
    private val onLongClick: (shop: Article) -> Unit,
    private val context: Context?
): RecyclerView.Adapter<ArticuloAdapter.ShopViewHolder>(){
    class ShopViewHolder(
        private val binding: ShopItemListBinding,
        private val onClick: (shop: Article) -> Unit,
        private val onLongClick: (shop: Article) -> Unit,
        private val context: Context?
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(shop: Article, size: Int) {
            with(binding) {
                shTitle.text = shop.title
                shDescription.text =  shop.description
                shPrice.text = "${shop.price} $"
                context?.let {
                    Glide.with(context)
                        .load(shop.image)
                        .placeholder(R.drawable.perfil)
                        .into(shImage)
                }
                clItem.setOnClickListener {
                    onClick(shop)
                }
                clItem.setOnLongClickListener {
                    onLongClick(shop)
                    true
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopViewHolder {
        val binding =
           ShopItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ShopViewHolder(binding, onClick, onLongClick, context)
    }

    override fun getItemCount() = shops.size

    override fun onBindViewHolder(holder: ShopViewHolder, position: Int) {
        holder.bind(shops[position], shops.size)
    }

    fun updateData(shops: List<Article>) {
        this.shops = shops
        notifyDataSetChanged()
    }
}
