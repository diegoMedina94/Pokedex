package com.example.pokedex.di

import com.example.pokedex.data.remote.PokedexApi
import com.example.pokedex.data.remote.PokedexRepositoryImpl
import com.example.pokedex.domain.PokedexRepository
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providerPokedexApi(): PokedexApi {

        val gson: Gson = GsonBuilder().create()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(OkHttpClient.Builder().build())
            .build()

        return retrofit.create(PokedexApi::class.java)
    }
}


@Module
@InstallIn(SingletonComponent::class)
abstract class Binds {

    @Binds
    abstract fun bindPokedexRepository(
        pokexPokedexRepository: PokedexRepositoryImpl,
    ): PokedexRepository


}