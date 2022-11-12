package com.example.demo_memo.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.demo_memo.data.MemoData

object DiffUtilCallback : DiffUtil.ItemCallback<MemoData>() {

    override fun areItemsTheSame(oldItem: MemoData, newItem: MemoData): Boolean {
        return oldItem.memoId == newItem.memoId
    }

    override fun areContentsTheSame(oldItem: MemoData, newItem: MemoData): Boolean {
        return (oldItem.memoTitle == newItem.memoTitle) and (oldItem.memoText == newItem.memoText)
    }

}