package com.materialsouk.meettvshow.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.materialsouk.meettvshow.network.ApiClient
import com.materialsouk.meettvshow.network.ApiService
import com.materialsouk.meettvshow.responses.TVShowsResponses
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MostPopularTVShowsRepository {

    companion object {
        private var apiService = ApiClient.getRetrofit().create(ApiService::class.java)

    }

    fun getMostPopularTVShows(page: Int): LiveData<TVShowsResponses?> {
        val data = MutableLiveData<TVShowsResponses?>()
        apiService.getMostPopularTVShows(page).enqueue(object : Callback<TVShowsResponses?> {
            override fun onResponse(
                call: Call<TVShowsResponses?>,
                response: Response<TVShowsResponses?>
            ) {
                data.value = response.body()
            }

            override fun onFailure(call: Call<TVShowsResponses?>, t: Throwable) {
                data.value = null
            }
        })
        return data
    }
}