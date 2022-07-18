package com.sirac.expressbanktask.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sirac.expressbanktask.R
import com.sirac.expressbanktask.model.CategoryDtModel
import com.sirac.expressbanktask.model.CategoryModel
import kotlinx.android.synthetic.main.category_dt_row.view.*
import kotlinx.android.synthetic.main.category_row.view.*

class CategoryDtRecyclerAdapter(val categoryDtList:ArrayList<CategoryDtModel>, private val listener:CategoryDtRecyclerAdapter.Listener) : RecyclerView.Adapter<CategoryDtRecyclerAdapter.ItemHolder>() {

    interface Listener{
        fun onCategoryDtItemClick(categoryDt:CategoryDtModel)
    }

    class ItemHolder(view:View) : RecyclerView.ViewHolder(view){

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoryDtRecyclerAdapter.ItemHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.category_dt_row, parent, false)
        return ItemHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryDtRecyclerAdapter.ItemHolder, position: Int) {
        holder.itemView.setOnClickListener {
            listener.onCategoryDtItemClick(categoryDtList.get(position))
        }
        holder.itemView.categoryDtIcon.setImageResource(categoryDtList.get(position).icon)
        holder.itemView.categoryDtName.text = categoryDtList.get(position).name
        holder.itemView.categoryDtTime.text = categoryDtList.get(position).time
        holder.itemView.categoryDtCost.text = categoryDtList.get(position).cost
    }

    override fun getItemCount(): Int {
        return categoryDtList.size
    }
}
