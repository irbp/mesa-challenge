package com.italo.mesachallenge.fragment

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.italo.domain.model.Place
import com.italo.mesachallenge.R

class PlaceDetailsDialogFragment(private val place: Place) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater
            val view = inflater.inflate(R.layout.dialog_place_details, null)
            val nameText = view.findViewById<TextView>(R.id.txt_place_name)
            val addressText = view.findViewById<TextView>(R.id.txt_place_address)

            nameText.text = place.name
            addressText.text = place.address

            builder.setView(view)
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}