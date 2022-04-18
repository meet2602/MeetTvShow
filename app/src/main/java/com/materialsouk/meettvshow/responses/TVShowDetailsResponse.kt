package com.materialsouk.meettvshow.responses

import com.google.gson.annotations.SerializedName
import com.materialsouk.meettvshow.models.TVShowDetails


class TVShowDetailsResponse {


    @SerializedName("tvShow")
    private val tvShowDetails: TVShowDetails? = null

    fun getTvShowDetails(): TVShowDetails? {
        return tvShowDetails
    }
}