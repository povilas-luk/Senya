package com.example.senya.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.navArgs
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