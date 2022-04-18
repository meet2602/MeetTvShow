package com.materialsouk.meettvshow

import android.content.Intent
import android.os.Bundle
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.materialsouk.meettvshow.adapters.TVShowsAdapter
import com.materialsouk.meettvshow.databinding.ActivityMainBinding
import com.materialsouk.meettvshow.listeners.TVShowsListener
import com.materialsouk.meettvshow.models.TVShow
import com.materialsouk.meettvshow.viewmodels.MostPopularTVShowsViewModel


class MainActivity : AppCompatActivity(), TVShowsListener {
    private lateinit var activityMainBinding: ActivityMainBinding
    private lateinit var viewModel: MostPopularTVShowsViewModel
    private val tvShows: ArrayList<TVShow> = ArrayList()
    private lateinit var tvShowsAdapter: TVShowsAdapter
    private var currentPage = 1
    private var totalAvailablePages = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        activityMainBinding.isLoading = true
        activityMainBinding.tvShowsRV.setHasFixedSize(true)
        viewModel = ViewModelProvider(this@MainActivity).get(
            MostPopularTVShowsViewModel::class.java
        )
        tvShowsAdapter = TVShowsAdapter(tvShows, this)
        activityMainBinding.tvShowsRV.adapter = tvShowsAdapter
        activityMainBinding.tvShowsRV.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(@NonNull recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!activityMainBinding.tvShowsRV.canScrollVertically(1)) {
                    if (currentPage <= totalAvailablePages) {
                        currentPage += 1
                        getMostPopularTVShows()
                    }
                }
            }
        })


        activityMainBinding.imageSearch.setOnClickListener {
            startActivity(
                Intent(
                    applicationContext,
                    SearchActivity::class.java
                )
            )
        }
        getMostPopularTVShows()
    }

    private fun getMostPopularTVShows() {
        if (currentPage != 1) {
            activityMainBinding.isLoadingMore = true
        }
        viewModel.getMostPopularTVShows(currentPage).observe(
            this
        ) { mostpopularTVShowsResonse ->
            if (mostpopularTVShowsResonse != null) {
                totalAvailablePages = mostpopularTVShowsResonse.getTotalPages()
                if (mostpopularTVShowsResonse.getTvShows() != null) {
                    val oldCount = tvShows.size
                    tvShows.addAll(mostpopularTVShowsResonse.getTvShows()!!)
                    tvShowsAdapter.notifyItemRangeInserted(oldCount, tvShows.size)
                    activityMainBinding.isLoadingMore = false
                    activityMainBinding.isLoading = false
                }
            }
        }
    }


    override fun onTVShowClicked(tvShow: TVShow) {
        val intent = Intent(applicationContext, TVShowDetailsActivity::class.java)
        intent.putExtra("tvShow", tvShow)
        startActivity(intent)
    }

}