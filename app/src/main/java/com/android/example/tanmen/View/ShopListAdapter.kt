package com.android.example.tanmen.View

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.example.tanmen.Model.Shop
import com.android.example.tanmen.R

class ShopListAdapter(private val shopList: MutableList<Shop>)
    : RecyclerView.Adapter<ShopListAdapter.ViewHolder>() {
    private lateinit var listener: OnShopCellClickListener

    interface OnShopCellClickListener {
        fun onItemClick(shop: Shop)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image: ImageView = itemView.findViewById(R.id.image)
        var shopAddress: TextView = itemView.findViewById(R.id.detail_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_shop_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val shop = shopList[position]
        shop.image.into(holder.image)
        holder.shopAddress.text = shop.name
        holder.itemView.setOnClickListener {
            listener.onItemClick(shop)
        }
    }

    override fun getItemCount() : Int = shopList.size

    fun setOnShopCellClickListener(listener: OnShopCellClickListener) {
        this.listener = listener
    }
}