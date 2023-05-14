package com.example.findmyrahmah.ui.gallery

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.example.findmyrahmah.BitmapHelper
import com.example.findmyrahmah.MarkerInfoWindowAdapter
import com.example.findmyrahmah.R
import com.example.findmyrahmah.databinding.FragmentGalleryBinding
import com.example.findmyrahmah.place.Place
import com.example.findmyrahmah.place.PlaceRenderer
import com.example.findmyrahmah.place.PlacesReader
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.Circle
import com.google.android.gms.maps.model.LatLngBounds
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.ktx.addCircle
import com.google.maps.android.ktx.addMarker
import com.google.maps.android.ktx.awaitMap
import com.google.maps.android.ktx.awaitMapLoad
import my.edu.tarc.mycontact.WebDB
import org.json.JSONObject
import java.lang.Exception

class GalleryFragment : Fragment() {

    private var _binding: FragmentGalleryBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val places: List<Place> by lazy {
        PlacesReader(requireContext()).read()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        val galleryViewModel =
//            ViewModelProvider(this).get(GalleryViewModel::class.java)

        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.map_fragment) as SupportMapFragment
        this.readAnnouncements();
        lifecycleScope.launchWhenCreated {
            // Get map
            val googleMap = mapFragment.awaitMap()

            addClusteredMarkers(googleMap)

            // Wait for map to finish loading
            googleMap.awaitMapLoad()

            // Ensure all places are visible in the map
            val bounds = LatLngBounds.builder()
            places.forEach { bounds.include(it.latLng) }
            googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds.build(), 20))
        }
        return root
    }



    private fun addClusteredMarkers(googleMap: GoogleMap) {
        // Create the ClusterManager class and set the custom renderer
        val clusterManager = ClusterManager<Place>(requireContext(), googleMap)
        clusterManager.renderer =
            PlaceRenderer(
                requireContext(),
                googleMap,
                clusterManager
            )

        // Set custom info window adapter
        clusterManager.markerCollection.setInfoWindowAdapter(MarkerInfoWindowAdapter(requireContext()))

        // Add the places to the ClusterManager
        clusterManager.addItems(places)
        clusterManager.cluster()

        // Show polygon
        clusterManager.setOnClusterItemClickListener { item ->
            addCircle(googleMap, item)
            return@setOnClusterItemClickListener false
        }

        // When the camera starts moving, change the alpha value of the marker to translucent
        googleMap.setOnCameraMoveStartedListener {
            clusterManager.markerCollection.markers.forEach { it.alpha = 0.3f }
            clusterManager.clusterMarkerCollection.markers.forEach { it.alpha = 0.3f }
        }

        googleMap.setOnCameraIdleListener {
            // When the camera stops moving, change the alpha value back to opaque
            clusterManager.markerCollection.markers.forEach { it.alpha = 1.0f }
            clusterManager.clusterMarkerCollection.markers.forEach { it.alpha = 1.0f }

            // Call clusterManager.onCameraIdle() when the camera stops moving so that re-clustering
            // can be performed when the camera stops moving
            clusterManager.onCameraIdle()
        }
    }

    private var circle: Circle? = null

    // [START maps_android_add_map_codelab_ktx_add_circle]
    /**
     * Adds a [Circle] around the provided [item]
     */
    private fun addCircle(googleMap: GoogleMap, item: Place) {
        circle?.remove()
        circle = googleMap.addCircle {
            center(item.latLng)
            radius(1000.0)
            fillColor(ContextCompat.getColor(requireContext(), R.color.colorPrimaryTranslucent))
            strokeColor(ContextCompat.getColor(requireContext(), R.color.colorPrimary))
        }
    }
    // [END maps_android_add_map_codelab_ktx_add_circle]

    private val restaurantIcon: BitmapDescriptor by lazy {
        val color = ContextCompat.getColor(requireContext(), R.color.colorPrimary)
        BitmapHelper.vectorToBitmap(requireContext(), R.drawable.baseline_restaurant_24, color)
    }

    // [START maps_android_add_map_codelab_ktx_add_markers]
    /**
     * Adds markers to the map. These markers won't be clustered.
     */
    private fun addMarkers(googleMap: GoogleMap) {
        places.forEach { place ->
            val marker = googleMap.addMarker {
                title(place.name)
                position(place.latLng)
                icon(restaurantIcon)
            }
            // Set place as the tag on the marker object so it can be referenced within
            // MarkerInfoWindowAdapter
            marker?.tag = place
        }
    }
    // [END maps_android_add_map_codelab_ktx_add_markers]

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

        private fun readAnnouncements(){
            val url = getString(R.string.url_server) + getString(R.string.url_announce_read)
            val jsonObjectRequest = JsonObjectRequest(
                Request.Method.GET, url, null,
                {response ->
                    try{
                        if(response != null){
                            val strResponse = response.toString()
                            val jsonResponse = JSONObject(strResponse)
                            val records: String = jsonResponse.get("records").toString()

                            Log.d("Second Fragment", "Response: %s".format(records))
                        }
                    }catch (e: Exception){
                        Log.d("Second Fragment", "Response: %s".format(e.message.toString()))
                    }
                },
                {
                        error ->
                    Log.d("Second Fragment", "Response : %s".format(error.message.toString()))
                }
            )
            jsonObjectRequest.retryPolicy = DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, 0, 1f)
            WebDB.getInstance(requireContext()).addToRequestQueue(jsonObjectRequest)
        }
}