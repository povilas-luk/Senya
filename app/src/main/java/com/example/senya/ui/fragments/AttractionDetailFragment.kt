package com.example.senya.ui.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.navArgs
import com.example.senya.R
import com.example.senya.data.Attraction
import com.example.senya.databinding.FragmentAttractionDetailBinding
import com.squareup.picasso.Picasso

class AttractionDetailFragment : BaseFragment() {

    private var _binding: FragmentAttractionDetailBinding? = null
    private val binding get() = _binding!!

    private val safeArgs: AttractionDetailFragmentArgs by navArgs()

    private val attraction: Attraction by lazy {attractionsList.find {
            it.id == safeArgs.attractionId
        }?: Attraction()
    }

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

        Picasso.get().load(attraction.image_url).into(binding.detailHeaderImageView)
        binding.detailTitleTextVIew.text = attraction.title
        binding.detailDescriptionTextView.text = attraction.description
        binding.detailTimeToVisitTextView.text = attraction.months_to_visit
        val factNumberText = "${attraction.facts.size} facts"
        binding.detailFactsTextView.text = factNumberText
        binding.detailFactsTextView.setOnClickListener {
            factsDialog()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_attraction_detail, menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.locationMenuItem -> {
                val uri = "geo:${attraction.location.latitude},${attraction.location.longitude}?z=9&q=${attraction.title}"
                val gmmIntentUri = Uri.parse(uri)
                val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                mapIntent.setPackage("com.google.android.apps.maps")
                startActivity(mapIntent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun factsDialog() {
        val builder: AlertDialog.Builder? = activity?.let {
            AlertDialog.Builder(it)
        }
        var facts: String = attraction.facts.joinToString (prefix = "  ", separator = "\n\n  ")

        builder?.setMessage(facts)?.setTitle("Facts")

        val dialog: AlertDialog? = builder?.create()
        dialog?.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}