package com.italo.mesachallenge.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.italo.mesachallenge.R
import com.italo.mesachallenge.adapter.PlaceAdapter
import com.italo.mesachallenge.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_list.view.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ListFragment : Fragment() {

    private lateinit var viewAdapter: PlaceAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager
    private val mainViewModel: MainViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_list, container, false)

        viewManager = LinearLayoutManager(requireActivity())
        viewAdapter = PlaceAdapter(listOf())

        rootView.places_recyclerview.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

        mainViewModel.currentNearbyPlaces.observe(viewLifecycleOwner, Observer {
            viewAdapter.updateList(it)
        })

        return rootView
    }
}
