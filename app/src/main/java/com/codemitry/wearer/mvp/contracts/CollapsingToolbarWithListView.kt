package com.codemitry.wearer.mvp.contracts

import androidx.recyclerview.widget.RecyclerView
import com.codemitry.wearer.mvp.contracts.base.BaseContract
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout

interface CollapsingToolbarWithListView : BaseContract.BaseView {

    val toolbarLayout: CollapsingToolbarLayout
    val list: RecyclerView

    // to disable scrolling when all items are visible on a screen, and scroll is not needed

    fun setToolbarScrollingEnabled(enabled: Boolean) {

        toolbarLayout.let { toolbar ->

            toolbar.layoutParams = (toolbar.layoutParams as AppBarLayout.LayoutParams).apply {
                scrollFlags = if (enabled)
                    AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL or AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED
                else
                    AppBarLayout.LayoutParams.SCROLL_FLAG_NO_SCROLL
            }
        }
    }

    fun updateToolbarBehaviour() {
        list.addOnLayoutChangeListener { view, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom ->
            if (bottom != oldBottom) {
                setToolbarScrollingEnabled(view.canScrollVertically(1) || view.canScrollVertically(-1))
            }
        }
    }
}