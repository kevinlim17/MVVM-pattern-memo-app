package com.example.demo_memo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.demo_memo.R
import com.example.demo_memo.data.MemoData
import com.example.demo_memo.databinding.RecyclerViewItemBinding

class CustomMemoAdapter(private var memoDataSet: Array<MemoData>) : RecyclerView.Adapter<CustomMemoAdapter.ViewHolder>(){

    interface OnItemClickListener{
        fun onItemClick(v : View, data : MemoData, position: Int)
    }
    interface OnItemLongClickListener {
        fun onItemLongClick(v : View, data : MemoData, position: Int) : Boolean
    }

    private var listener : OnItemClickListener? = null
    private var longClickListener : OnItemLongClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener){
        this.listener = listener
    }
    fun setOnItemLongClickListener(longClickListener: OnItemLongClickListener){
        this.longClickListener = longClickListener
    }

    inner class ViewHolder(private val binding: RecyclerViewItemBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(memoData : MemoData){
            binding.previewTitle.text = memoData.memoTitle
            binding.previewText.text = memoData.memoText

            if (adapterPosition != RecyclerView.NO_POSITION) {
                binding.memoPreview.setOnClickListener {
                    listener?.onItemClick(it, memoData, adapterPosition)
                }
                binding.memoPreview.setOnLongClickListener {
                    longClickListener!!.onItemLongClick(it, memoData, adapterPosition)
                }
                /**
                val item = memoDataSet[]
                Intent(itemView.context, MemoActivity::class.java).apply {
                    putExtra("savedTitleText", item.memoTitle)
                    putExtra("savedMemoText", item.memoText)
                }.run { itemView.context.startActivity(this) }
                 **/
            }

            //itemView.setOnLongClickListener{}
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_view_item, parent, false)
        return ViewHolder(RecyclerViewItemBinding.bind(view))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(memoDataSet[position])
        holder.setIsRecyclable(false)
    }

    override fun getItemCount() = memoDataSet.size

    fun setMemoData(newDataSet: Array<MemoData>, state : Int, position: Int){
        memoDataSet.toMutableList().clear()
        memoDataSet = newDataSet

        notifyDataSetChanged()
        /**
        when (state) {
            -1 -> notifyItemRemoved(position)
            0 -> notifyItemChanged(position)
            1 -> notifyItemInserted(position)
        }
        **/
    }

}
