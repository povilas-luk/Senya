package com.example.senya.ui.fragments.details

import android.net.Uri
import com.airbnb.epoxy.EpoxyController
import com.airbnb.epoxy.kotlinsample.helpers.ViewBindingKotlinModel
import com.example.senya.R
import com.example.senya.databinding.EpoxyModelHeaderBinding
import com.example.senya.databinding.ModelHeaderImageBinding
import com.example.senya.databinding.ViewHolderAttractionBinding
import com.example.senya.ui.epoxy.LoadingEpoxyModel
import com.squareup.picasso.Picasso

class HeaderEpoxyController(val image_urls: List<String>): EpoxyController() {
    /*var isLoading: Boolean = false
        set(value) {
            field = value
            if (field) {
                requestModelBuild()
            }
        }

    var image_urls = ArrayList<String>()
        set(value) {
            field = value
            isLoading = false
            requestModelBuild()
        }*/

    override fun buildModels() {
        /*if (isLoading) {
            LoadingEpoxyModel()
                .id("loading_state")
                .addTo(this)
            return
        }*/

        if (image_urls.isEmpty()) {
            // todo show empty state
            return
        }

        image_urls.forEachIndexed() { index, url ->
            ImageHeaderEpoxyModel(url)
                .id(index)
                .addTo(this)
        }
    }

    data class ImageHeaderEpoxyModel(
        val url: String,
    ): ViewBindingKotlinModel<ModelHeaderImageBinding>(R.layout.model_header_image) {

        override fun ModelHeaderImageBinding.bind() {
            Picasso.get()
                .load(Uri.parse(url))
                .error(R.drawable.ic_baseline_broken_image_24)
                .into(imageView)
        }

    }

    data class HeaderEpoxyModel(
        val headerText: String
    ): ViewBindingKotlinModel<EpoxyModelHeaderBinding>(R.layout.epoxy_model_header) {
        override fun EpoxyModelHeaderBinding.bind() {
            headerTextView.text = headerText
        }

    }
}