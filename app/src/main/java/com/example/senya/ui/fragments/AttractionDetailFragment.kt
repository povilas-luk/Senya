package com.example.senya.ui.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import com.example.senya.R
import com.example.senya.databinding.FragmentAttractionDetailBinding
import com.squareup.picasso.Picasso

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
            Picasso.get()
                .load(Uri.parse(attraction.image_url))
                .into(binding.detailHeaderImageView)
            binding.detailTitleTextVIew.text = attraction.title
            binding.detailDescriptionTextView.text = attraction.description
            binding.detailTimeToVisitTextView.text = attraction.months_to_visit
            val factNumberText = "${attraction.facts.size} facts"
            binding.detailFactsTextView.text = factNumberText
            binding.detailFactsTextView.setOnClickListener {
                factsDialog(attraction.facts)
            }
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