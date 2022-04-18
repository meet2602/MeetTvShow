package com.materialsouk.meettvshow.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ApiClient {
    companion object {

        fun getRetrofit(): Retrofit {
            return Retrofit.Builder()
                .baseUrl("https://www.episodate.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}