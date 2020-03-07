package com.example.myarchitecture.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.myarchitecture.databinding.AnnouncementItemBinding
import com.example.myarchitecture.databinding.NetworkItemBinding
import com.example.myarchitecture.model.announcementModels.AnnouncementModel
import com.example.myarchitecture.shared.data.networking.RequestState

class AnnouncementAdapter : PagedListAdapter<AnnouncementModel, RecyclerView.ViewHolder>(AnnouncementModel.DIFF_CALLBACK) {

    companion object {
        private const val TYPE_PROGRESS = 0
        private const val TYPE_ITEM = 1
    }

    private var mRequestState: RequestState? = null
    private var mListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return if (viewType == TYPE_ITEM) {
            val itemBinding = AnnouncementItemBinding.inflate(layoutInflater, parent, false)
            AnnouncementItemViewHolder(itemBinding)
        } else {
            val itemBinding = NetworkItemBinding.inflate(layoutInflater, parent, false)
            NetworkItemViewHolder(itemBinding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == TYPE_ITEM) {
            getItem(position)?.let { (holder as AnnouncementItemViewHolder).bindTo(it, mListener) }
        } else if (getItemViewType(position) == TYPE_PROGRESS) {
            (holder as NetworkItemViewHolder).bindTo(mRequestState)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == itemCount - 1 && mRequestState?.status != null && mRequestState?.status != RequestState.Status.SUCCESS)
            TYPE_PROGRESS
        else
            TYPE_ITEM
    }

    fun setNetworkState(requestState: RequestState?) {
        mRequestState = requestState
    }

    class AnnouncementItemViewHolder(private val binding: AnnouncementItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindTo(model: AnnouncementModel, listener: OnItemClickListener?) {
            binding.itemAnnouncementTitle.text = model.title
            binding.itemAnnouncementDescription.text = model.description
            Glide.with(binding.itemAnnouncementImage.context)
                .load(model.photo?.photo)
                .apply(RequestOptions().centerCrop())
                .into(binding.itemAnnouncementImage)
            binding.root.setOnClickListener { listener?.onClick(model.id) }
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

    interface OnItemClickListener {
        fun onClick(id: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        mListener = listener
    }
}