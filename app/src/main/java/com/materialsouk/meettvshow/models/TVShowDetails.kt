package com.materialsouk.meettvshow.models

import com.google.gson.annotations.SerializedName


class TVShowDetails {
    @SerializedName("url")
    val url: String? = null

    @SerializedName("description")
    val description: String? = null

    @SerializedName("image_path")
    val imagePath: String? = null

    @SerializedName("rating")
    val rating: String? = null

    @SerializedName("genres")
    val genres: Array<String>? = null

    @SerializedName("runtime")
    val runTime: String? = null

    @SerializedName("pictures")
    val pictures: Array<String>? = null
}
