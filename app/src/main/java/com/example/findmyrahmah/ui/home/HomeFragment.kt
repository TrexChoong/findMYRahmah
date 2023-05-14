package com.example.findmyrahmah.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.findmyrahmah.R
import com.example.findmyrahmah.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private lateinit var announcementTextView: TextView
    private lateinit var announcementRecyclerView: RecyclerView
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.getStartedButton.setOnClickListener {
           // findNavController().navigate(R.id.)
        }

        binding.favPlacesButton.setOnClickListener {
           // findNavController().navigate(R.id.)
        }

        binding.suggestPlaceButton.setOnClickListener {
           /// findNavController().navigate(R.id.)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root

     //   announcementTextView = binding.announcementTextView
    //    announcementRecyclerView = binding.announcementRecyclerView

     //   announcementRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
