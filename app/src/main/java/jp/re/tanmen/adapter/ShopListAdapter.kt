package jp.re.tanmen.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import jp.re.tanmen.model.Entity.Shop
import jp.re.tanmen.R
import com.squareup.picasso.Picasso

class ShopListAdapter(var shopList: MutableList<Shop>)
    : RecyclerView.Adapter<ShopListAdapter.ViewHolder>() {
    private lateinit var listener: OnShopCellClickListener

    interface OnShopCellClickListener {
        fun onItemClick(shop: Shop)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.image)
        val shopAddress: TextView = itemView.findViewById(R.id.detail_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_shop_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val shop = shopList[position]
        Picasso.get().load(shop.image).resize(72, 72).into(holder.image)
        holder.apply {
            shopAddress.text = shop.name
            itemView.setOnClickListener {
                listener.onItemClick(shop)
            }
        }
    }

    override fun getItemCount() : Int = shopList.size

    fun setOnShopCellClickListener(listener: OnShopCellClickListener) {
        this.listener = listener
    }
}