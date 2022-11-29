package com.example.demo_memo.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.demo_memo.database.MemoInfo

object DiffUtilCallback : DiffUtil.ItemCallback<MemoInfo>() {

    override fun areItemsTheSame(oldItem: MemoInfo, newItem: MemoInfo): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MemoInfo, newItem: MemoInfo): Boolean {
        return (oldItem.title == newItem.title) and (oldItem.text == newItem.text)
    }

}