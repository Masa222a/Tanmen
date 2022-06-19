package com.android.example.tanmen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ShopListAdapter(private val shopList: MutableList<Shop>)
    : RecyclerView.Adapter<ShopListAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image: ImageView = itemView.findViewById(R.id.image)
        var shopAddress: TextView = itemView.findViewById(R.id.detail_address)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_shop_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val shop = shopList[position]
        holder.image.setImageResource(R.drawable.ramen_dining_24px)
        holder.shopAddress.text = shop.address
    }

    override fun getItemCount() : Int = shopList.size

}