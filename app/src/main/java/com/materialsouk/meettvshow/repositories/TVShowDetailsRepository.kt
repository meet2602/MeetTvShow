package com.materialsouk.meettvshow.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.materialsouk.meettvshow.network.ApiClient
import com.materialsouk.meettvshow.network.ApiService
import com.materialsouk.meettvshow.responses.TVShowDetailsResponse

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TVShowDetailsRepository {
    companion object {
        private var apiService = ApiClient.getRetrofit().create(ApiService::class.java)
    }

    fun getTVShowDetails(tvShowId: String?): LiveData<TVShowDetailsResponse?> {
        val data = MutableLiveData<TVShowDetailsResponse?>()
        apiService.getTVShowDetails(tvShowId!!)
            .enqueue(object : Callback<TVShowDetailsResponse?> {
                override fun onResponse(
                    call: Call<TVShowDetailsResponse?>,
                    response: Response<TVShowDetailsResponse?>
                ) {
                    data.value = response.body()
                }

                override fun onFailure(call: Call<TVShowDetailsResponse?>, t: Throwable) {
                    data.value = null
                }

            })
        return data
    }
}