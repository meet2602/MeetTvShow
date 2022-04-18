package com.materialsouk.meettvshow.responses

import com.google.gson.annotations.SerializedName
import com.materialsouk.meettvshow.models.TVShow


class TVShowsResponses {
    @SerializedName("page")
    private val page = 0

    @SerializedName("pages")
    private val totalPages = 0

    @SerializedName("tv_shows")
    private val tvShows: List<TVShow>? = null

    fun getPage(): Int {
        return page
    }

    fun getTotalPages(): Int {
        return totalPages
    }

    fun getTvShows(): List<TVShow>? {
        return tvShows
    }
}