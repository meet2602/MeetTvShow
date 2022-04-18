package com.materialsouk.meettvshow.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.materialsouk.meettvshow.R
import com.materialsouk.meettvshow.models.TVShow
import com.materialsouk.meettvshow.databinding.ItemContainerTvShowBinding
import com.materialsouk.meettvshow.listeners.TVShowsListener


class TVShowsAdapter(
    private val tvShows: List<TVShow>,
    private val tvShowsListener: TVShowsListener
) :
    RecyclerView.Adapter<TVShowsAdapter.BindingViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder {

        val tvShowBinding: ItemContainerTvShowBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.item_container_tv_show, parent, false
        )
        return BindingViewHolder(tvShowBinding)
    }

    override fun onBindViewHolder(holder: BindingViewHolder, position: Int) {
        holder.itemBinding.tvShow = tvShows[position]
        holder.itemBinding.executePendingBindings()
        holder.itemBinding.root.setOnClickListener {
            tvShowsListener.onTVShowClicked(
                tvShows[position]
            )
        }

    }

    override fun getItemCount(): Int {
        return tvShows.size
    }

    class BindingViewHolder(val itemBinding: ItemContainerTvShowBinding) :
        RecyclerView.ViewHolder(itemBinding.root)


}