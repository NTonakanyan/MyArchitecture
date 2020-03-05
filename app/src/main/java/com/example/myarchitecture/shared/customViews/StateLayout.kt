package com.example.myarchitecture.shared.customViews

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import com.example.myarchitecture.databinding.StateLayoutBinding


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

    fun showContent() {
        container.visibility = View.GONE
        mContent.visibility = View.VISIBLE
        mContent.alpha = 0f
        mContent.animate().alpha(1f).setDuration(300).start()
    }

    fun showLoading() {
        container.visibility = View.VISIBLE
        progress.visibility = View.VISIBLE

        message.visibility = View.GONE
        button.visibility = View.GONE
        mContent.visibility = View.GONE
    }

    fun showNetworkError() {
        container.visibility = View.VISIBLE
        message.visibility = View.VISIBLE

        mContent.visibility = View.GONE
        progress.visibility = View.GONE
        button.visibility = View.GONE
        mContent.visibility = View.GONE

        message.text = "Network error"
    }

    fun showServerError() {
        container.visibility = View.VISIBLE
        message.visibility = View.VISIBLE

        mContent.visibility = View.GONE
        progress.visibility = View.GONE
        button.visibility = View.GONE
        mContent.visibility = View.GONE

        message.text = "Server error"
    }

    fun showServerError(msg: String?) {
        container.visibility = View.VISIBLE
        message.visibility = View.VISIBLE

        mContent.visibility = View.GONE
        progress.visibility = View.GONE
        button.visibility = View.GONE
        mContent.visibility = View.GONE

        message.text = msg
    }

    fun showEmpty() {
        container.visibility = View.VISIBLE
        message.visibility = View.VISIBLE

        mContent.visibility = View.GONE
        progress.visibility = View.GONE
        button.visibility = View.GONE
        mContent.visibility = View.GONE

        message.text = "Empty"
    }
}