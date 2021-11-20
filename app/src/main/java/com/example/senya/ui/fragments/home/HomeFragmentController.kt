package com.example.senya.ui.fragments.home

import com.airbnb.epoxy.EpoxyController
import com.airbnb.epoxy.kotlinsample.helpers.ViewBindingKotlinModel
import com.example.senya.R
import com.example.senya.data.Attraction
import com.example.senya.databinding.ViewHolderAttractionBinding
import com.squareup.picasso.Picasso


class HomeFragmentController(
    private val onClickedCallBack: (String) -> Unit
    ): EpoxyController() {

    var isLoading: Boolean = false
        set(value) {
            field = value
            if (field) {
                requestModelBuild()
            }
        }

    var attractions = ArrayList<Attraction>()
        set(value) {
            field = value
            isLoading = false
            requestModelBuild()
        }

    override fun buildModels() {
        if (isLoading) {
            // todo show loading
            return
        }

        if (attractions.isEmpty()) {
            // todo show empty state
            return
        }

        attractions.forEach { attraction ->
            AttractionEpoxyModel(attraction, onClickedCallBack)
                .id(attraction.id)
                .addTo(this)
        }
    }

    data class AttractionEpoxyModel(
        val attraction: Attraction,
        val onClicked: (String) -> Unit
    ): ViewBindingKotlinModel<ViewHolderAttractionBinding>(R.layout.view_holder_attraction) {

        override fun ViewHolderAttractionBinding.bind() {
            titleTextView.text = attraction.title
            timeToVisitTextView.text = attraction.months_to_visit
            Picasso.get()
                .load(attraction.image_url)
                .into(headerImageView)
            root.setOnClickListener {
                onClicked(attraction.id)
            }
        }

    }


}
