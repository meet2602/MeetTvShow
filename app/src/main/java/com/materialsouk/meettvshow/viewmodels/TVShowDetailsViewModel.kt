package com.materialsouk.meettvshow.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.materialsouk.meettvshow.responses.TVShowDetailsResponse
import com.materialsouk.meettvshow.repositories.TVShowDetailsRepository


class TVShowDetailsViewModel(application: Application) : AndroidViewModel(application) {


    private val tvShowDetailsRepository: TVShowDetailsRepository = TVShowDetailsRepository()

    fun getTVShowDetails(tvShowId: String): LiveData<TVShowDetailsResponse?> {
        return tvShowDetailsRepository.getTVShowDetails(tvShowId)
    }


}
