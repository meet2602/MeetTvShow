package com.materialsouk.meettvshow.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.materialsouk.meettvshow.responses.TVShowsResponses
import com.materialsouk.meettvshow.repositories.SearchTVShowsRepository


class SearchViewModel : ViewModel() {
    private val searchTVShowsRepository: SearchTVShowsRepository = SearchTVShowsRepository()
    fun searchTVShow(query: String, page: Int): LiveData<TVShowsResponses?> {
        return searchTVShowsRepository.searchTVShow(query, page)
    }
}