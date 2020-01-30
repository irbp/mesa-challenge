package com.italo.mesachallenge.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.italo.domain.model.Place
import com.italo.mesachallenge.R
import kotlinx.android.synthetic.main.place_list_item.view.*

class PlaceAdapter(private var places: List<Place>) :
    RecyclerView.Adapter<PlaceAdapter.PlaceViewHolder>() {

    class PlaceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.place_list_item, parent, false)
        return PlaceViewHolder(view)
    }

    override fun getItemCount() = places.size

    override fun onBindViewHolder(holder: PlaceViewHolder, position: Int) {
        val place = places[position]
        holder.itemView.apply {
            txt_place_name_item.text = place.name
            txt_place_address_item.text = place.address
            txt_place_phone_item.text = place.phone
        }
    }

    fun updateList(places: List<Place>) {
        this.places = places
        notifyDataSetChanged()
    }
}