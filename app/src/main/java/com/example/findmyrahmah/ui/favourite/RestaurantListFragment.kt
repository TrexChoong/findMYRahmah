package com.example.findmyrahmah.ui.favourite

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.findmyrahmah.databinding.FragmentRestaurantrecycleBinding


class RestaurantListFragment: Fragment() {

    private  var _binding: FragmentRestaurantrecycleBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val restaurantViewModel: RestaurantListViewModel by activityViewModels()

    private lateinit var restaurantAdapter: RestaurantAdapter



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRestaurantrecycleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        restaurantAdapter = RestaurantAdapter(restaurantViewModel.restaurants)
        binding.restaurantRecycle.adapter = restaurantAdapter
        binding.restaurantRecycle.layoutManager = LinearLayoutManager(context)

        restaurantViewModel.restaurants.observe(viewLifecycleOwner, {restaurants ->
            restaurantAdapter.notifyDataSetChanged()
        })
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}