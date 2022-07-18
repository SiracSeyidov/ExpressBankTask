package com.sirac.expressbanktask.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.annotation.Nullable
import com.sirac.expressbanktask.R
import com.sirac.expressbanktask.model.CardModel
import com.sirac.expressbanktask.model.MonthModel
import com.sirac.expressbanktask.model.YearModel
import kotlinx.android.synthetic.main.category_row.view.*
import kotlinx.android.synthetic.main.month_dropdown_item.view.*
import kotlinx.android.synthetic.main.year_dropdown_item.view.*

class MonthArrayAdapter(var cntxt:Context, var resource:Int, var monthList:List<MonthModel>, private val listener:MonthArrayAdapter.Listener) :
    ArrayAdapter<MonthModel?>(cntxt, resource, monthList) {

    interface Listener{
        fun onMonthItemClick(month:MonthModel)
    }

    override fun getView(position: Int, @Nullable convertView: View?, parent: ViewGroup): View {
        val layoutInflater = LayoutInflater.from(cntxt)
        val view = layoutInflater.inflate(resource, null)

        view.monthText.text = monthList.get(position).month
        view.setOnClickListener {
            listener.onMonthItemClick(monthList.get(position))
        }

        return view
    }

    init {
        this.monthList = monthList
    }
}
