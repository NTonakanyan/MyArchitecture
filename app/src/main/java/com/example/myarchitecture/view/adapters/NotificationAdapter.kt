package com.example.myarchitecture.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myarchitecture.databinding.FeedItemBinding
import com.example.myarchitecture.databinding.NetworkItemBinding
import com.example.myarchitecture.model.notificationModels.NotificationModel
import com.example.myarchitecture.shared.data.networking.NetworkState

class NotificationAdapter : PagedListAdapter<NotificationModel, RecyclerView.ViewHolder>(NotificationModel.DIFF_CALLBACK) {

    private val TYPE_PROGRESS = 0
    private val TYPE_ITEM = 1
    private var mNetworkState: NetworkState? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return if (viewType == TYPE_ITEM) {
            val itemBinding = FeedItemBinding.inflate(layoutInflater, parent, false)
            ArticleItemViewHolder(itemBinding)
        } else {
            val itemBinding = NetworkItemBinding.inflate(layoutInflater, parent, false)
            NetworkItemViewHolder(itemBinding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == TYPE_ITEM) {
            (holder as ArticleItemViewHolder).bindTo(getItem(position))
        } else if (getItemViewType(position) == TYPE_PROGRESS) {
            (holder as NetworkItemViewHolder).bindTo(mNetworkState)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (mNetworkState == NetworkState.LOADING && position == itemCount - 1)
            TYPE_PROGRESS
        else
            TYPE_ITEM
    }

    fun setNetworkState(networkState: NetworkState) {
        mNetworkState = networkState
    }

    class ArticleItemViewHolder(private val binding: FeedItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindTo(model: NotificationModel?) {
            binding.itemFeedText.text = model?.title
        }
    }

    class NetworkItemViewHolder(private val binding: NetworkItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindTo(networkState: NetworkState?) {
            if (networkState != null && networkState.status == NetworkState.Status.RUNNING) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }


            if (networkState != null && (networkState.status == NetworkState.Status.NETWORK_ERROR ||
                            networkState.status == NetworkState.Status.SERVER_ERROR ||
                            networkState.status == NetworkState.Status.API_ERROR)) {
                binding.errorMsg.visibility = View.VISIBLE
                binding.errorMsg.text = networkState.msg
            } else {
                binding.errorMsg.visibility = View.GONE
            }
        }
    }
}