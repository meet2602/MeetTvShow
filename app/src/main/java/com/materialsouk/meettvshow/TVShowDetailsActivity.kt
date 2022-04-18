package com.materialsouk.meettvshow

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.materialsouk.meettvshow.adapters.ImageSliderAdapter
import com.materialsouk.meettvshow.databinding.ActivityTvshowDetailsBinding
import com.materialsouk.meettvshow.models.TVShow
import com.materialsouk.meettvshow.viewmodels.TVShowDetailsViewModel
import java.util.*


class TVShowDetailsActivity : AppCompatActivity() {
    private lateinit var activityTVShowDetailsBinding: ActivityTvshowDetailsBinding
    private lateinit var tvShowDetailsViewModel: TVShowDetailsViewModel
    private var tvShow: TVShow? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityTVShowDetailsBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_tvshow_details)
        doInitialization()
    }

    private fun doInitialization() {
        tvShowDetailsViewModel = ViewModelProvider(this).get(TVShowDetailsViewModel::class.java)
        activityTVShowDetailsBinding.imageBack.setOnClickListener { onBackPressed() }
        tvShow = intent.getSerializableExtra("tvShow") as TVShow?

        getTVShowDetails()
    }

    private fun getTVShowDetails() {
        activityTVShowDetailsBinding.isLoading = true
        val tvShowId = java.lang.String.valueOf(tvShow!!.id)
        tvShowDetailsViewModel.getTVShowDetails(tvShowId).observe(
            this
        ) { tvShowDetailsResponse ->
            activityTVShowDetailsBinding.isLoading = false
            if (tvShowDetailsResponse!!.getTvShowDetails()!!.pictures != null) {
                loadImageSlider(tvShowDetailsResponse.getTvShowDetails()!!.pictures!!)
            }
            activityTVShowDetailsBinding.tvShowImageURL =
                tvShowDetailsResponse.getTvShowDetails()!!.imagePath
            activityTVShowDetailsBinding.imageTvShow.visibility = View.VISIBLE
            activityTVShowDetailsBinding.description = HtmlCompat.fromHtml(
                tvShowDetailsResponse.getTvShowDetails()!!.description!!,
                HtmlCompat.FROM_HTML_MODE_LEGACY
            ).toString()
            activityTVShowDetailsBinding.textDescription.visibility = View.VISIBLE
            activityTVShowDetailsBinding.textReadMore.visibility = View.VISIBLE
            activityTVShowDetailsBinding.textReadMore.setOnClickListener {
                if (activityTVShowDetailsBinding.textReadMore.text.toString() == "Read More"
                ) {
                    activityTVShowDetailsBinding.textDescription.maxLines = Int.MAX_VALUE
                    activityTVShowDetailsBinding.textDescription.ellipsize = null
                    activityTVShowDetailsBinding.textReadMore.setText(R.string.read_less)
                } else {
                    activityTVShowDetailsBinding.textDescription.maxLines = 4
                    activityTVShowDetailsBinding.textDescription.ellipsize =
                        TextUtils.TruncateAt.END
                    activityTVShowDetailsBinding.textReadMore.setText(R.string.read_more)
                }
            }
            activityTVShowDetailsBinding.rating = java.lang.String.format(
                Locale.getDefault(),
                "%.2f", tvShowDetailsResponse.getTvShowDetails()!!.rating!!.toDouble()
            )
            if (tvShowDetailsResponse.getTvShowDetails()!!.genres != null) {
                activityTVShowDetailsBinding.genre =
                    tvShowDetailsResponse.getTvShowDetails()!!.genres!![0]
            } else {
                activityTVShowDetailsBinding.genre = "N/A"
            }
            activityTVShowDetailsBinding.runtime =
                tvShowDetailsResponse.getTvShowDetails()!!.runTime.toString() + " Min"
            activityTVShowDetailsBinding.viewDivider1.visibility = View.VISIBLE
            activityTVShowDetailsBinding.layoutMisc.visibility = View.VISIBLE
            activityTVShowDetailsBinding.viewDivider2.visibility = View.VISIBLE
            activityTVShowDetailsBinding.buttonWebSite.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(tvShowDetailsResponse.getTvShowDetails()!!.url)
                startActivity(intent)
            }
            activityTVShowDetailsBinding.buttonWebSite.visibility = View.VISIBLE

            loadBasicTVShowDetails()
        }
    }

    private fun loadImageSlider(sliderImages: Array<String>) {
        activityTVShowDetailsBinding.sliderViewPager.offscreenPageLimit = 1
        activityTVShowDetailsBinding.sliderViewPager.adapter = ImageSliderAdapter(sliderImages)
        activityTVShowDetailsBinding.sliderViewPager.visibility = View.VISIBLE
        activityTVShowDetailsBinding.viewFadingEdge.visibility = View.VISIBLE
        setupSliderIndicator(sliderImages.size)
        activityTVShowDetailsBinding.sliderViewPager.registerOnPageChangeCallback(object :
            OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentSliderIndicator(position)
            }
        })
    }

    private fun setupSliderIndicator(count: Int) {
        val indicators: Array<ImageView?> = arrayOfNulls(count)
        val layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        )
        layoutParams.setMargins(8, 0, 8, 0)
        for (i in indicators.indices) {
            indicators[i] = ImageView(applicationContext)
            indicators[i]!!.setImageDrawable(
                ContextCompat.getDrawable(
                    applicationContext,
                    R.drawable.background_slider_indicator_inactive
                )
            )
            indicators[i]!!.layoutParams = layoutParams
            activityTVShowDetailsBinding.layoutSliderIndicators.addView(indicators[i])
        }
        activityTVShowDetailsBinding.layoutSliderIndicators.visibility = View.VISIBLE
        setCurrentSliderIndicator(0)
    }

    private fun setCurrentSliderIndicator(position: Int) {
        val childCount = activityTVShowDetailsBinding.layoutSliderIndicators.childCount
        for (i in 0 until childCount) {
            val imageView: ImageView =
                activityTVShowDetailsBinding.layoutSliderIndicators.getChildAt(i) as ImageView
            if (i == position) {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.background_slider_indicator_active
                    )
                )
            } else {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.background_slider_indicator_inactive
                    )
                )
            }
        }
    }

    private fun loadBasicTVShowDetails() {
        activityTVShowDetailsBinding.tvShowName = tvShow!!.name
        activityTVShowDetailsBinding.networkCountry = tvShow!!.network + " (" +
                tvShow!!.country + ")"
        activityTVShowDetailsBinding.status = tvShow!!.status
        activityTVShowDetailsBinding.startedDate = tvShow!!.startDate
        activityTVShowDetailsBinding.textName.visibility = View.VISIBLE
        activityTVShowDetailsBinding.textNetworkCountry.visibility = View.VISIBLE
        activityTVShowDetailsBinding.textStatus.visibility = View.VISIBLE
        activityTVShowDetailsBinding.textStarted.visibility = View.VISIBLE
    }
}