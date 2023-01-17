package com.bignerdranch.android.shoppinglist.presentation


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.TextView


import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.shoppinglist.R
import com.bignerdranch.android.shoppinglist.domain.ShopItem


class ShopListAdapter :
    androidx.recyclerview.widget.ListAdapter<ShopItem, ShopListAdapter.ShopItemViewHolder>(
        ShopItemDiffCallback()
    ) {


    var onShopItemLongClickListener: ((ShopItem) -> Unit)? = null
    var onShopItemClickListener: ((ShopItem) -> Unit)? = null

    class ShopItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val tvName = view.findViewById<TextView>(R.id.tv_name)
        val tvCount = view.findViewById<TextView>(R.id.tv_count)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {
        val layout = when (viewType) {
            VIEW_TYPE_DISABLE -> R.layout.item_shop_disable
            VIEW_TYPE_ENABLE -> R.layout.item_shop_enable
            else -> throw java.lang.RuntimeException("UNKNOWN View type: $viewType")
        }
        val view1 = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return ShopItemViewHolder(view1)
    }

    override fun onBindViewHolder(holder: ShopItemViewHolder, position: Int) {

        val shopItem = getItem(position)

        holder.view.setOnLongClickListener {
            onShopItemLongClickListener?.invoke(shopItem)
            true
        }

        holder.view.setOnClickListener {
            onShopItemClickListener?.invoke(shopItem)
        }

        holder.tvName.text = shopItem.name
        holder.tvCount.text = shopItem.count.toString()

    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        return if (item.enabled) {
            VIEW_TYPE_ENABLE
        } else {
            VIEW_TYPE_DISABLE
        }
    }

    companion object {
        const val VIEW_TYPE_ENABLE = 100
        const val VIEW_TYPE_DISABLE = 101
        const val MAX_POOL_SIZE = 7
    }
}