package com.materialsouk.meettvshow

import android.content.Intent
import android.os.Bundle
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.materialsouk.meettvshow.adapters.TVShowsAdapter
import com.materialsouk.meettvshow.databinding.ActivitySearchBinding
import com.materialsouk.meettvshow.models.TVShow
import kotlin.collections.ArrayList
import java.util.Timer
import java.util.TimerTask
import android.os.Handler
import androidx.annotation.NonNull
import com.materialsouk.meettvshow.listeners.TVShowsListener
import com.materialsouk.meettvshow.viewmodels.SearchViewModel

class SearchActivity : AppCompatActivity(), TVShowsListener {
    private lateinit var activitySearchBinding: ActivitySearchBinding
    private lateinit var viewModel: SearchViewModel
    private lateinit var tvShows: ArrayList<TVShow>
    private lateinit var tvShowsAdapter: TVShowsAdapter
    private var currentPage = 1
    private var totalAvailablePage = 1
    private var timer: Timer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        activitySearchBinding = DataBindingUtil.setContentView(this, R.layout.activity_search)
        doInitialization()
    }

    private fun doInitialization() {
        tvShows = ArrayList()
        activitySearchBinding.imageBack.setOnClickListener { onBackPressed() }
        activitySearchBinding.tvShowsRecyclerView.setHasFixedSize(true)
        viewModel = ViewModelProvider(this@SearchActivity).get(SearchViewModel::class.java)
        tvShowsAdapter = TVShowsAdapter(tvShows, this)
        activitySearchBinding.tvShowsRecyclerView.adapter = tvShowsAdapter
        activitySearchBinding.inputSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (timer != null) {
                    timer!!.cancel()
                }
            }

            override fun afterTextChanged(s: Editable) {
                if (s.toString().trim { it <= ' ' }.isNotEmpty()) {
                    timer = Timer()
                    timer!!.schedule(object : TimerTask() {
                        override fun run() {
                            Handler(Looper.getMainLooper()).post {
                                currentPage = 1
                                totalAvailablePage = 1
                                searchTVShow(s.toString())
                            }
                        }
                    }, 500)
                } else {
                    tvShows.clear()
                    tvShowsAdapter.notifyDataSetChanged()
                }
            }
        })
        activitySearchBinding.tvShowsRecyclerView.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrolled(@NonNull recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!activitySearchBinding.tvShowsRecyclerView.canScrollVertically(1)) {
                    if (activitySearchBinding.inputSearch.text.toString().isNotEmpty()) {
                        if (currentPage < totalAvailablePage) {
                            currentPage += 1
                            searchTVShow(activitySearchBinding.inputSearch.text.toString())
                        }
                    }
                }
            }
        })
        activitySearchBinding.inputSearch.requestFocus()
    }

    private fun searchTVShow(query: String) {
        if (currentPage != 1) {
            activitySearchBinding.isLoading = false
            activitySearchBinding.isLoadingMore = true
        } else {
            activitySearchBinding.isLoading = true

        }
        viewModel.searchTVShow(query, currentPage)
            .observe(this) { tvShowsResponses ->
                if (tvShowsResponses != null) {
                    totalAvailablePage = tvShowsResponses.getTotalPages()
                    if (tvShowsResponses.getTvShows() != null) {
                        val oldCount = tvShows.size
                        tvShows.addAll(tvShowsResponses.getTvShows()!!)
                        tvShowsAdapter.notifyItemRangeInserted(oldCount, tvShows.size)
                        activitySearchBinding.isLoadingMore = false
                        activitySearchBinding.isLoading = false
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