package com.codemitry.wearer.clothessubtypes

import android.graphics.Canvas
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.codemitry.wearer.myclothes.MyClothesItemSwipedAdapter


class RecyclerItemTouchHelper(
    dragDirs: Int,
    swipeDirs: Int,
    private val listener: RecyclerItemTouchHelperListener
) : ItemTouchHelper.SimpleCallback(dragDirs, swipeDirs) {

    interface RecyclerItemTouchHelperListener {
        fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int, position: Int)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        listener.onSwiped(viewHolder, direction, viewHolder.adapterPosition)
    }

    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        if (viewHolder != null) {
            val foregroundView =
                when (viewHolder) {
                    is ClothesSubtypeItemSwipedAdapter.ViewHolder -> viewHolder.foregroundView
                    is MyClothesItemSwipedAdapter.ViewHolder -> viewHolder.foregroundView
                    else -> return
                }
            getDefaultUIUtil().onSelected(foregroundView)
        }
    }

    override fun onChildDrawOver(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder?,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        val foregroundView =
            when (viewHolder) {
                is ClothesSubtypeItemSwipedAdapter.ViewHolder -> viewHolder.foregroundView
                is MyClothesItemSwipedAdapter.ViewHolder -> viewHolder.foregroundView
                else -> return
            }

        getDefaultUIUtil().onDrawOver(
            c,
            recyclerView,
            foregroundView,
            dX,
            dY,
            actionState,
            isCurrentlyActive
        )
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        val foregroundView =
            when (viewHolder) {
                is ClothesSubtypeItemSwipedAdapter.ViewHolder -> viewHolder.foregroundView
                is MyClothesItemSwipedAdapter.ViewHolder -> viewHolder.foregroundView
                else -> return
            }
        getDefaultUIUtil().clearView(foregroundView)
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        val foregroundView =
            when (viewHolder) {
                is ClothesSubtypeItemSwipedAdapter.ViewHolder -> viewHolder.foregroundView
                is MyClothesItemSwipedAdapter.ViewHolder -> viewHolder.foregroundView
                else -> return
            }
        getDefaultUIUtil().onDraw(
            c,
            recyclerView,
            foregroundView,
            dX,
            dY,
            actionState,
            isCurrentlyActive
        )
    }

}