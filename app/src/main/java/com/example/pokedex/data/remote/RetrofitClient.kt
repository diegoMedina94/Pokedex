package com.example.pokedex.data.remote

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/*val moshi = Moshi.Builder().build()

val gson: Gson = GsonBuilder().create()

val retrofit = Retrofit.Builder()
    .baseUrl("https://pokeapi.co/api/v2/")
    //.addConverterFactory(MoshiConverterFactory.create(moshi))
    .addConverterFactory(GsonConverterFactory.create(gson))
    .client(OkHttpClient.Builder().build())
    .build()

val service = retrofit.create(PokedexApi::class.java)*/
