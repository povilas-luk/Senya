package com.example.senya.arch

import android.content.Context
import com.example.senya.R
import com.example.senya.data.Attraction
import com.example.senya.data.AttractionsResponse
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class AttractionsRepository {

    private val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()

    fun parseAttractions(context: Context): ArrayList<Attraction> {
        val textFromJson = context.resources.openRawResource(R.raw.croatia).bufferedReader().use {it.readText()}

        val adapter: JsonAdapter<AttractionsResponse> = moshi.adapter(AttractionsResponse::class.java)
        return adapter.fromJson(textFromJson)!!.attractions as ArrayList<Attraction>
        //val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
        /*val type = Types.newParameterizedType(
            List::class.java,
            Attraction::class.java
        )*/
    }
}