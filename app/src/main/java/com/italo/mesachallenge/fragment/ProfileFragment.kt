package com.italo.mesachallenge.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.italo.mesachallenge.R
import com.italo.mesachallenge.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_profile.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ProfileFragment : Fragment() {

    private val mainViewModel: MainViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_profile, container, false)

        mainViewModel.retrieveUserData {  }

        mainViewModel.name.observe(viewLifecycleOwner, Observer {
            txt_profile_name.text = it
        })

        mainViewModel.pictureUrl.observe(viewLifecycleOwner, Observer {
            Glide.with(this).load(it).into(img_profile)
        })

        return rootView
    }


}
