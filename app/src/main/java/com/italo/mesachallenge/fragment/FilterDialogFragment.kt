package com.italo.mesachallenge.fragment

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.italo.mesachallenge.R

class FilterDialogFragment(private val listener: OnDialogSelectorListener, private val index: Int) :
    DialogFragment() {

    interface OnDialogSelectorListener {
        fun onDialogSelectedOption(option: Int)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle(getString(R.string.categories))
                .setSingleChoiceItems(R.array.categories, index) { dialog, which ->
                    listener.onDialogSelectedOption(which)
                    dialog.dismiss()
                }
                .setNegativeButton(getString(R.string.cancel)) { _, _ -> }
                .create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}