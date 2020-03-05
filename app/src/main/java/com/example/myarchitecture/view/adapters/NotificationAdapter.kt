package com.example.myarchitecture.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myarchitecture.databinding.NetworkItemBinding
import com.example.myarchitecture.databinding.NotificationItemBinding
import com.example.myarchitecture.model.notificationModels.NotificationModel
import com.example.myarchitecture.shared.data.networking.RequestState

class NotificationAdapter : PagedListAdapter<NotificationModel, RecyclerView.ViewHolder>(NotificationModel.DIFF_CALLBACK) {

    companion object {
        private const val TYPE_PROGRESS = 0
        private const val TYPE_ITEM = 1
    }

    private var mRequestState: RequestState? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return if (viewType == TYPE_ITEM) {
            val itemBinding = NotificationItemBinding.inflate(layoutInflater, parent, false)
            NotificationItemViewHolder(itemBinding)
        } else {
            val itemBinding = NetworkItemBinding.inflate(layoutInflater, parent, false)
            NetworkItemViewHolder(itemBinding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == TYPE_ITEM) {
            (holder as NotificationItemViewHolder).bindTo(getItem(position))
        } else if (getItemViewType(position) == TYPE_PROGRESS) {
            (holder as NetworkItemViewHolder).bindTo(mRequestState)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == itemCount - 1 && mRequestState?.status != RequestState.Status.SUCCESS)
            TYPE_PROGRESS
        else
            TYPE_ITEM
    }

    fun setNetworkState(requestState: RequestState?) {
        mRequestState = requestState
    }

    class NotificationItemViewHolder(private val binding: NotificationItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindTo(model: NotificationModel?) {
            binding.itemFeedText.text = model?.title
        }
    }

    class NetworkItemViewHolder(private val binding: NetworkItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindTo(requestState: RequestState?) {
            if (requestState?.status != RequestState.Status.LOADING) {
                binding.progressBar.visibility = View.GONE
                binding.errorMsg.visibility = View.VISIBLE
                binding.errorMsg.text = requestState?.msg
            } else {
                binding.progressBar.visibility = View.VISIBLE
                binding.errorMsg.visibility = View.GONE
            }
        }
    }
}