package com.example.demo_memo.adapter

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class VerticalItemDecorator (private val divHeight : Int) : RecyclerView.ItemDecoration(){
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        //outRect.top = divHeight
        if (parent.getChildAdapterPosition(view) != parent.adapter?.itemCount?.minus(1)){
            outRect.bottom = divHeight
        }
    }
}