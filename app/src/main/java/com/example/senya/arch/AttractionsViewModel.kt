package com.example.senya.arch

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.senya.data.Attraction

class AttractionsViewModel: ViewModel() {

    private val repository = AttractionsRepository()

    val attractionListLiveData = MutableLiveData<ArrayList<Attraction>>()

    val selectedAttractionLiveData = MutableLiveData<Attraction>()

    val locationSelectedLiveData = MutableLiveData<Attraction>()

    fun init(context: Context) {
        val attractionsList = repository.parseAttractions(context)
        attractionListLiveData.postValue(attractionsList) // main thread
        //attractionListLiveData.value = attractionsList // current thread
    }

    fun onAttractionSelected(attractionId: String) {
        val attraction = attractionListLiveData.value?.find {
            it.id == attractionId
        }?: return

        selectedAttractionLiveData.postValue(attraction)
    }
}