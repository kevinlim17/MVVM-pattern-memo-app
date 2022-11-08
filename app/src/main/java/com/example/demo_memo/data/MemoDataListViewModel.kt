package com.example.demo_memo.data

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MemoDataListViewModel(private val dataSource: MemoDataSource) : ViewModel() {

    val memoLiveData = dataSource.getMemoList()

    fun insertMemo(memoId : Int?, memoTitle : String?, memoDescription: String?){
        if (memoTitle != null && memoDescription != null && memoId != null){
            val newMemo = MemoData(memoId, memoTitle, memoDescription)
            dataSource.addMemo(newMemo)
        }
    }

    fun editMemo(position : Int, memoTitle: String?, memoDescription: String?){
        if (memoTitle != null && memoDescription != null){
            val currentId = dataSource.findIdByPosition(position)
            val newMemo = MemoData(currentId, memoTitle, memoDescription)
            dataSource.editMemo(position, newMemo)
        }
    }

    fun deleteMemo(memoData : MemoData){
        dataSource.deleteMemo(memoData)
    }

}

class MemoDataListViewModelFactory(private val context: Context) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MemoDataListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MemoDataListViewModel (
                dataSource = MemoDataSource.getMemoDataSource()
                    ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}