package com.materialsouk.meettvshow.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable


class TVShow : Serializable {

    var id = 0

    @SerializedName("name")
    var name: String? = null

    @SerializedName("start_date")
    var startDate: String? = null

    @SerializedName("country")
    var country: String? = null

    @SerializedName("network")
    var network: String? = null

    @SerializedName("status")
    var status: String? = null

    @SerializedName("image_thumbnail_path")
    var thumbnail: String? = null
}
