package com.example.gfgwallpaperapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class CategoryRVAdapter(
    private val categoryList: List<CategoryRVModal>,
    private val context: Context,
    private val categoryClickInterface: CategoryClickInterface
) : RecyclerView.Adapter<CategoryRVAdapter.CategoryItemViewHolder>() {

    class CategoryItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val categoryTV: TextView = itemView.findViewById(R.id.idTVCategory)
        val categoryIV: ImageView = itemView.findViewById(R.id.idIVCategory)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryItemViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.category_rv_item,
            parent, false
        )
        return CategoryItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CategoryItemViewHolder, position: Int) {
        val categoryItem = categoryList[position]
        holder.categoryTV.text = categoryItem.categoryName
        Picasso.get().load(categoryItem.categoryImg).placeholder(R.drawable.app_icon).into(
            holder.categoryIV
        )

        holder.itemView.setOnClickListener {
            categoryClickInterface.onCategoryClick(position)
        }
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

}

interface CategoryClickInterface {
    fun onCategoryClick(position: Int)
}