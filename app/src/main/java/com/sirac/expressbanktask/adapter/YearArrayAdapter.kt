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
import com.sirac.expressbanktask.model.YearModel
import kotlinx.android.synthetic.main.category_row.view.*
import kotlinx.android.synthetic.main.year_dropdown_item.view.*

class YearArrayAdapter(var cntxt:Context, var resource:Int, var yearList:List<YearModel>, private val listener:YearArrayAdapter.Listener) :
    ArrayAdapter<YearModel?>(cntxt, resource, yearList) {

    interface Listener{
        fun onYearItemClick(year:YearModel)
    }

    override fun getView(position: Int, @Nullable convertView: View?, parent: ViewGroup): View {
        val layoutInflater = LayoutInflater.from(cntxt)
        val view = layoutInflater.inflate(resource, null)

        view.yearText.text = yearList.get(position).year
        view.setOnClickListener {
            listener.onYearItemClick(yearList.get(position))
        }

        return view
    }

    init {
        this.yearList = yearList
    }
}
