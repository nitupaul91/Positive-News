package com.codingbatch.positivenews.data.remote

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitFactory {

    fun getRetrofitInstance(url: String): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(url)
            .client(getClient())
            .build()
    }

    private fun getClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .build()
    }
}