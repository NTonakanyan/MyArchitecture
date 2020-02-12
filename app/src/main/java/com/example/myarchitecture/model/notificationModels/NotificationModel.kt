package com.example.myarchitecture.model.notificationModels

import androidx.recyclerview.widget.DiffUtil

class NotificationModel {

    var id: Int = 0
    var title: String? = null
    var isNextPage: Boolean = true

    init {
        id = id++
    }

    companion object {
        @JvmField
        var DIFF_CALLBACK: DiffUtil.ItemCallback<NotificationModel> = object : DiffUtil.ItemCallback<NotificationModel>() {
            override fun areItemsTheSame(oldItem: NotificationModel, newItem: NotificationModel): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: NotificationModel, newItem: NotificationModel): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun equals(obj: Any?): Boolean {
        if (obj === this) return true
        val article: NotificationModel = obj as NotificationModel
        return article.id == this.id
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + (title?.hashCode() ?: 0)
        result = 31 * result + isNextPage.hashCode()
        return result
    }
}