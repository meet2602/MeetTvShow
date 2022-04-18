package com.materialsouk.meettvshow.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.materialsouk.meettvshow.repositories.MostPopularTVShowsRepository
import com.materialsouk.meettvshow.responses.TVShowsResponses


class MostPopularTVShowsViewModel : ViewModel() {
    private val mostPopularTVShowsRepository: MostPopularTVShowsRepository =
        MostPopularTVShowsRepository()

    fun getMostPopularTVShows(page: Int): LiveData<TVShowsResponses?> {
        return mostPopularTVShowsRepository.getMostPopularTVShows(page)
    }

}