package com.example.senya.ui.fragments.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.senya.R
import com.example.senya.data.Attraction
import com.example.senya.databinding.ViewHolderAttractionBinding
import com.squareup.picasso.Picasso


class HomeFragmentAdapter(
    private var attractionsArrayList: ArrayList<Attraction>,
    private val onClickedCallBack: () -> Unit
    ): RecyclerView.Adapter<HomeFragmentAdapter.AttractionViewHolder>() {

    inner class AttractionViewHolder(view: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(view.context).inflate(R.layout.view_holder_attraction, view, false)
    ) {
        private val binding = ViewHolderAttractionBinding.bind(itemView)

        fun bind(attraction: Attraction, onClicked: () -> Unit) {
            binding.titleTextView.text = attraction.title
            binding.timeToVisitTextView.text = attraction.months_to_visit
            Picasso.get()
                .load(attraction.image_url)
                .into(binding.headerImageView)
            binding.root.setOnClickListener {
                onClicked()
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): AttractionViewHolder {
        /*val inflater = LayoutInflater.from(viewGroup.context)
        val binding = ViewHolderAttractionBinding.inflate(inflater)*/
        return AttractionViewHolder(viewGroup)
    }

    override fun onBindViewHolder(viewHolder: AttractionViewHolder, position: Int) {
        viewHolder.bind(attractionsArrayList[position], onClickedCallBack)
    }

    override fun getItemCount() = attractionsArrayList.size

}
