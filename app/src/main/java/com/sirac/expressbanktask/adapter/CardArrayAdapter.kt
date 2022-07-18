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
import kotlinx.android.synthetic.main.card_dropdown_item.view.*
import kotlinx.android.synthetic.main.category_row.view.*

class CardArrayAdapter(var cntxt:Context, var resource:Int, var cardList:List<CardModel>, private val listener:CardArrayAdapter.Listener) :
    ArrayAdapter<CardModel?>(cntxt, resource, cardList) {

    interface Listener{
        fun onCardItemClick(card:CardModel)
    }

    override fun getView(position: Int, @Nullable convertView: View?, parent: ViewGroup): View {
        val layoutInflater = LayoutInflater.from(cntxt)
        val view = layoutInflater.inflate(resource, null)

        view.cardIcon.setImageResource(cardList.get(position).icon)
        view.cardName.text = cardList.get(position).name
        view.cardNumber.text = cardList.get(position).number
        view.setOnClickListener {
            listener.onCardItemClick(cardList.get(position))
        }

        return view
    }

    init {
        this.cardList = cardList
    }
}
