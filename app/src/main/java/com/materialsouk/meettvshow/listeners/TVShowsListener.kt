package com.materialsouk.meettvshow.listeners

import com.materialsouk.meettvshow.models.TVShow

interface TVShowsListener {
    fun onTVShowClicked(tvShow: TVShow)
}