package com.example.myarchitecture.model.announcementModels

import androidx.recyclerview.widget.DiffUtil

data class AnnouncementModel(val id: Int = 0,
                             val title: String? = null,
                             val description: String? = null,
                             val isNextPage: Boolean = true,
                             val photo: PhotoOptimizerModel? = null) {

    companion object {
        @JvmField
        var DIFF_CALLBACK = object : DiffUtil.ItemCallback<AnnouncementModel>() {
            override fun areItemsTheSame(oldItem: AnnouncementModel, newItem: AnnouncementModel): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: AnnouncementModel, newItem: AnnouncementModel): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun equals(other: Any?): Boolean {
        if (other === this) return true
        val article: AnnouncementModel = other as AnnouncementModel
        return article.id == this.id
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + (title?.hashCode() ?: 0)
        result = 31 * result + isNextPage.hashCode()
        return result
    }
}