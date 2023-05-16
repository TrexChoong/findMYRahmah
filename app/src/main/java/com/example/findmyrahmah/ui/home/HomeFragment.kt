package com.example.findmyrahmah.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.findmyrahmah.AnnouncementAdapter
import com.example.findmyrahmah.R
import com.example.findmyrahmah.databinding.FragmentHomeBinding
import org.json.JSONArray

class HomeFragment : Fragment() {
    private lateinit var announcementTextView: TextView
    private lateinit var announcementRecyclerView: RecyclerView
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val announcementList = mutableListOf<String>()
        val announcementAdapter = AnnouncementAdapter(announcementList)

        binding.announcementRecyclerView.adapter = announcementAdapter


        val url = "https://testapitrexworkshop.000webhostapp.com/api/announcements/read.php"
        val requestQueue = Volley.newRequestQueue(requireContext())
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                announcementList.clear()
                val jsonArray = JSONArray(response)
                for (i in 0 until jsonArray.length()) {
                    val jsonObject = jsonArray.getJSONObject(i)
                    val announcement = jsonObject.getString("title")
                    announcementList.add(announcement)
                }
                announcementAdapter.notifyDataSetChanged()
            },
            { error ->
                Toast.makeText(requireContext(), error.toString(), Toast.LENGTH_LONG).show()
            }
        )
        requestQueue.add(stringRequest)

        binding.getStartedButton.setOnClickListener {
             findNavController().navigate(R.id.nav_gallery)
        }

        binding.favPlacesButton.setOnClickListener {
             findNavController().navigate(R.id.nav_favourite)
        }

        binding.suggestPlaceButton.setOnClickListener {
             //findNavController().navigate(R.id.)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root

       announcementTextView = binding.announcementTextView
        announcementRecyclerView = binding.announcementRecyclerView

       announcementRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
