package com.codingbatch.positivenews.ui.moreoptions.bindings

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.codingbatch.positivenews.R

class TextViewBinding {

    companion object {
        @JvmStatic
        @BindingAdapter("app:bookmarkText")
        fun setBookmarkTextAndDrawable(textView: TextView, isBookmarked: Boolean) {
            if (isBookmarked)
                textView.apply {
                    text =
                        context.resources.getString(R.string.more_options_bookmark_active_button)
                    setCompoundDrawablesWithIntrinsicBounds(
                        context.resources.getDrawable(
                            R.drawable.ic_bookmarked,
                            null
                        ), null, null, null
                    )
                }
            else
                textView.apply {
                    text =
                        context.resources.getString(R.string.more_options_bookmark_inactive_button)
                    setCompoundDrawablesWithIntrinsicBounds(
                        context.resources.getDrawable(
                            R.drawable.ic_bookmark_border,
                            null
                        ), null, null, null
                    )
                }
        }
    }
}