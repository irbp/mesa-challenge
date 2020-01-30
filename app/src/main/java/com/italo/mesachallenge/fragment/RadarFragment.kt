package com.italo.mesachallenge.fragment


import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.italo.domain.model.Place
import com.italo.mesachallenge.R
import com.italo.mesachallenge.viewmodel.MainViewModel
import com.italo.mesachallenge.viewmodel.PlacesTypes
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class RadarFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private lateinit var googleMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val viewModel: MainViewModel by sharedViewModel()
    private var profileBitmap: Bitmap? = null
    private var userMarker: Marker? = null
    private val TAG = RadarFragment::class.java.simpleName

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_radar, container, false)
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.map_fragment) as SupportMapFragment
        mapFragment.getMapAsync(this)

        viewModel.getCurrentLocation(requireActivity()) {
            Toast.makeText(requireContext(), getString(R.string.location_error), Toast.LENGTH_LONG)
                .show()
        }

        viewModel.retrieveUserData {
            Toast.makeText(
                requireContext(),
                getString(R.string.user_information_error),
                Toast.LENGTH_LONG
            )
                .show()
        }

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())

        viewModel.pictureUrl.observe(viewLifecycleOwner, Observer {
            Glide.with(this)
                .asBitmap()
                .load(it)
                .apply(RequestOptions.circleCropTransform())
                .into(object : CustomTarget<Bitmap>(150, 150) {
                    override fun onLoadCleared(placeholder: Drawable?) {}

                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?
                    ) {
                        profileBitmap = resource
                    }
                })
        })

        return rootView
    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult?) {
            locationResult ?: return

            val location = locationResult.lastLocation
            val latLng = LatLng(location.latitude, location.longitude)

            userMarker?.remove()
            userMarker = googleMap.addMarker(
                MarkerOptions()
                    .position(latLng)
            )
            if (profileBitmap != null) {
                userMarker!!.setIcon(BitmapDescriptorFactory.fromBitmap(profileBitmap))
            }

            val currentPlace = CameraPosition.Builder()
                .target(latLng)
                .bearing(location.bearing).zoom(20f)
                .build()
            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(currentPlace))
        }
    }

    private fun startLocationUpdates() {
        val locationRequest =
            LocationRequest.create().setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setFastestInterval(1000)
                .setInterval(1000)

        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        map.setOnMarkerClickListener(this)

        styleMap(googleMap)

        viewModel.currentNearbyPlaces.observe(this, Observer { places ->
            googleMap.clear()
            places.forEach {
                val latLng = LatLng(it.lat, it.lng)
                val marker = googleMap.addMarker(
                    MarkerOptions()
                        .position(latLng)
                        .icon(BitmapDescriptorFactory.fromBitmap(getMarkerIcon(it.types)))
                )
                marker.tag = it
            }
        })

        viewModel.liveLocation.observe(viewLifecycleOwner, Observer {
            if (it) {
                startLocationUpdates()
            } else {
                fusedLocationClient.removeLocationUpdates(locationCallback)
            }
        })
    }

    override fun onMarkerClick(marker: Marker?): Boolean {
        if (marker?.tag != null) {
            val place = marker.tag as Place
            val placeDetailsDialog = PlaceDetailsDialogFragment(place)
            placeDetailsDialog.show(
                requireParentFragment().childFragmentManager,
                DETAILS_DIALOG_TAG
            )
        } else {
            viewModel.changeLiveLocation()
        }
        return true
    }

    private fun styleMap(map: GoogleMap) {
        map.setMapStyle(MapStyleOptions.loadRawResourceStyle(requireActivity(), R.raw.maps_style))
    }

    private fun resizeIcon(iconId: Int): Bitmap {
        val bitmap = BitmapFactory.decodeResource(resources, iconId)
        return Bitmap.createScaledBitmap(bitmap, 100, 100, false)
    }

    private fun getMarkerIcon(types: List<String>): Bitmap {
        for (placeType in PlacesTypes.values()) {
            if (types.contains(placeType.type)) {
                return resizeIcon(placeType.icon)
            }
        }
        return resizeIcon(PlacesTypes.RESTAURANT.icon)
    }

    companion object {
        private const val DETAILS_DIALOG_TAG = "detailsDialog"
    }

}
