package com.example.myarchitecture.shared.customViews

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.myarchitecture.databinding.StateLayoutBinding
import com.example.myarchitecture.shared.data.networking.RequestState


class StateLayout : FrameLayout {

    private lateinit var mContent: View
    private lateinit var mBinding: StateLayoutBinding
    private lateinit var container: LinearLayout
    private lateinit var progress: ProgressBar
    private lateinit var message: TextView
    private lateinit var button: Button
    private var isInitial: Boolean = false

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle)

    constructor(context: Context, attrs: AttributeSet) : this(context, attrs, 0)

    override fun onFinishInflate() {
        super.onFinishInflate()
        if (isInitial)
            return
        isInitial = true
        if (childCount > 1) throw IllegalStateException("Layout must have one child")
        if (isInEditMode) return  // hide state views in designer
        mContent = getChildAt(0) // assume first child as content
        mBinding = StateLayoutBinding.inflate(LayoutInflater.from(context), this, true)

        container = mBinding.layoutStateContainer
        progress = mBinding.layoutStateProgress
        message = mBinding.layoutStateMessage
        button = mBinding.layoutStateButton
    }

    fun showContent(requestState: RequestState) {
        if (mContent is SwipeRefreshLayout)
            (mContent as SwipeRefreshLayout).isRefreshing = false
        if (!isMainRequest(requestState))
            return
        container.visibility = View.GONE
        mContent.visibility = View.VISIBLE
        mContent.alpha = 0f
        mContent.animate().alpha(1f).setDuration(300).start()

    }

    fun showLoading(requestState: RequestState) {
        if (!isMainRequest(requestState))
            return
        container.visibility = View.VISIBLE
        progress.visibility = View.VISIBLE

        message.visibility = View.GONE
        button.visibility = View.GONE
        mContent.visibility = View.GONE
    }

    fun showNetworkError(requestState: RequestState) {
        if (!isMainRequest(requestState))
            return
        container.visibility = View.VISIBLE
        message.visibility = View.VISIBLE

        mContent.visibility = View.GONE
        progress.visibility = View.GONE
        button.visibility = View.GONE
        mContent.visibility = View.GONE

        message.text = "Network error"
    }

    fun showServerError(requestState: RequestState) {
        if (!isMainRequest(requestState))
            return
        container.visibility = View.VISIBLE
        message.visibility = View.VISIBLE

        mContent.visibility = View.GONE
        progress.visibility = View.GONE
        button.visibility = View.GONE
        mContent.visibility = View.GONE

        message.text = requestState.msg ?: "Server error"
    }

    fun showEmpty(requestState: RequestState) {
        if (!isMainRequest(requestState))
            return
        container.visibility = View.VISIBLE
        message.visibility = View.VISIBLE

        mContent.visibility = View.GONE
        progress.visibility = View.GONE
        button.visibility = View.GONE
        mContent.visibility = View.GONE

        message.text = "Empty"
    }

    private fun isMainRequest(requestState: RequestState) = requestState.isMainRequest
}