package com.materialsouk.meettvshow.network

import com.materialsouk.meettvshow.responses.TVShowDetailsResponse
import com.materialsouk.meettvshow.responses.TVShowsResponses
import retrofit2.http.GET
import retrofit2.Call;
import retrofit2.http.Query;

interface ApiService {
    @GET("most-popular")
    fun getMostPopularTVShows(@Query("page") page: Int): Call<TVShowsResponses>

    @GET("show-details")
    fun getTVShowDetails(@Query("q") tvShowId: String): Call<TVShowDetailsResponse>

    @GET("search")
    fun searchTVShow(@Query("q") query: String, @Query("page") page: Int): Call<TVShowsResponses>
}