package com.example.senya.ui.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.senya.R
import com.example.senya.databinding.FragmentHomeBinding
import com.example.senya.ui.fragments.BaseFragment

class HomeFragment : BaseFragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //val attractionsArrayList = (attractionsList as? ArrayList<Attraction>) ?: ArrayList()
        val homeEpoxyController = HomeFragmentController(/*attractionsArrayList*/) { attractionId ->
            activityViewModel.onAttractionSelected(attractionId)
            //val navDirections = HomeFragmentDirections.actionHomeFragmentToAttractionDetailFragment(attractionId)
            //navController.navigate(navDirections)
            navController.navigate(R.id.action_homeFragment_to_attractionDetailFragment)
        }
        //homeAdapter.setData(attractionsArrayList)
        //binding.recyclerView.adapter = homeEpoxyController.adapter
        binding.epoxyRecyclerView.setController(homeEpoxyController)
        binding.epoxyRecyclerView.addItemDecoration(DividerItemDecoration(requireActivity(), RecyclerView.VERTICAL))

        activityViewModel.attractionListLiveData.observe(viewLifecycleOwner) { attractions ->
            homeEpoxyController.attractions = attractions
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}