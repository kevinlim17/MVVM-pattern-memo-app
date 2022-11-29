package com.example.demo_memo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.demo_memo.R
import com.example.demo_memo.database.MemoInfo
import com.example.demo_memo.databinding.RecyclerViewItemBinding

class CustomMemoAdapter : ListAdapter<MemoInfo, CustomMemoAdapter.ViewHolder>(DiffUtilCallback){

    interface OnItemClickListener{
        fun onItemClick(v : View, data : MemoInfo, position: Int)
    }
    interface OnItemLongClickListener {
        fun onItemLongClick(v : View, data : MemoInfo, position: Int) : Boolean
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

        fun bind(memoInfo : MemoInfo){
            binding.previewTitle.text = memoInfo.title
            binding.previewText.text = memoInfo.text

            if (adapterPosition != RecyclerView.NO_POSITION) {
                binding.memoPreview.setOnClickListener {
                    listener?.onItemClick(it, memoInfo, adapterPosition)
                }
                binding.memoPreview.setOnLongClickListener {
                    longClickListener!!.onItemLongClick(it, memoInfo, adapterPosition)
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
        holder.bind(getItem(position))
        holder.setIsRecyclable(true)
    }

    /**SubmitList(MutableList<T> list) Method로 대체**/
    /**
    fun updateMemoData(newDataSet: Array<MemoData>, state : Int?, position: Int){
        memoDataSet.toMutableList().clear()
        memoDataSet = newDataSet

        notifyDataSetChanged()
        when (state) {
            -1 -> notifyItemRemoved(position)
            0 -> notifyItemChanged(position)
            1 -> notifyItemInserted(position)
        }
    }
    **/

}
