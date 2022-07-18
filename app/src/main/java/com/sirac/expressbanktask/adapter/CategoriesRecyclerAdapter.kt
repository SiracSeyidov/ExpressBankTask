package com.sirac.expressbanktask.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sirac.expressbanktask.R
import com.sirac.expressbanktask.model.CategoryModel
import kotlinx.android.synthetic.main.category_row.view.*

class CategoriesRecyclerAdapter(val categoryList:ArrayList<CategoryModel>, private val listener:CategoriesRecyclerAdapter.Listener) : RecyclerView.Adapter<CategoriesRecyclerAdapter.ItemHolder>() {

    interface Listener{
        fun onCategoryItemClick(category:CategoryModel)
    }

    class ItemHolder(view:View) : RecyclerView.ViewHolder(view){

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoriesRecyclerAdapter.ItemHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.category_row, parent, false)
        return ItemHolder(view)
    }

    override fun onBindViewHolder(holder: CategoriesRecyclerAdapter.ItemHolder, position: Int) {
        holder.itemView.setOnClickListener {
            listener.onCategoryItemClick(categoryList.get(position))
        }
        holder.itemView.categoryIcon.setImageResource(categoryList.get(position).icon)
        holder.itemView.categoryName.text = categoryList.get(position).name
        holder.itemView.categoryPercent.text = categoryList.get(position).percent
        holder.itemView.categoryCost.text = categoryList.get(position).cost
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }
}
