package com.example.senya.ui.fragments.details

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.dmp.senya.ui.fragment.details.ContentEpoxyController
import com.example.senya.R
import com.example.senya.databinding.FragmentAttractionDetailBinding
import com.example.senya.ui.fragments.BaseFragment

class AttractionDetailFragment : BaseFragment() {

    private var _binding: FragmentAttractionDetailBinding? = null
    private val binding get() = _binding!!

    //private val safeArgs: AttractionDetailFragmentArgs by navArgs()

    /*private val attraction: Attraction by lazy {attractionsList.find {
            it.id == safeArgs.attractionId
        }?: Attraction()
    }*/

    //private val attraction = Attraction()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAttractionDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activityViewModel.selectedAttractionLiveData.observe(viewLifecycleOwner) {attraction ->
            /*Picasso.get()
                .load(Uri.parse(attraction.image_urls[0]))
                .into(binding.detailHeaderImageView)*/
            binding.headerEpoxyRecyclerView.setControllerAndBuildModels(HeaderEpoxyController(attraction.image_urls))
            LinearSnapHelper().attachToRecyclerView(binding.headerEpoxyRecyclerView)
            binding.indicator.attachToRecyclerView(binding.headerEpoxyRecyclerView)
            binding.detailTitleTextVIew.text = attraction.title

            var isGridMode: Boolean = binding.contentEpoxyRecyclerView.layoutManager is GridLayoutManager
            val contentEpoxyController = ContentEpoxyController(attraction)
            contentEpoxyController.isGridMode = isGridMode
            contentEpoxyController.onChangeLayoutCallback = {
                if (isGridMode) {
                    binding.contentEpoxyRecyclerView.layoutManager = LinearLayoutManager(requireContext())
                } else {
                    binding.contentEpoxyRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
                }

                isGridMode = !isGridMode
                contentEpoxyController.isGridMode = isGridMode
                contentEpoxyController.requestModelBuild()

            }
            binding.contentEpoxyRecyclerView.setControllerAndBuildModels(contentEpoxyController)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_attraction_detail, menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.locationMenuItem -> {
                val attraction = activityViewModel.selectedAttractionLiveData.value ?: return true
                activityViewModel.locationSelectedLiveData.postValue(attraction)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun factsDialog(factsList: List<String>) {
        val facts: String = factsList.joinToString (prefix = "\u2022 ", separator = "\n\n\u2022 ")

        AlertDialog.Builder(requireContext(), R.style.dialog)
            .setTitle("Facts")
            .setMessage(facts)
            .setPositiveButton("Close") {dialog, _ -> dialog.dismiss()}
            .show()

        /*val builder: AlertDialog.Builder? = activity?.let {
            AlertDialog.Builder(it)
        }
        builder?.setMessage(facts)?.setTitle("Facts")?.setPositiveButton("Close") { dialog, _ -> dialog.dismiss() }
        val dialog: AlertDialog? = builder?.create()
        dialog?.show()*/
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}