package com.example.demo_memo.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData


class MemoDataSource {
    private val initMemoDataArray = memoDataArrayList()
    private val memoLiveData = MutableLiveData(initMemoDataArray)

    /* Adds Memo to liveData and posts value. */
    fun addMemo(newMemo : MemoData){
        val currentArray = memoLiveData.value
        if (currentArray == null){
            memoLiveData.postValue(arrayOf(newMemo))
        }
        else{
            val updatedArray = currentArray.toMutableList()
            updatedArray.add(0, newMemo)
            memoLiveData.postValue(updatedArray.toTypedArray())
        }
    }

    /* Edit a Memo, one of liveData, and posts value. */
    fun editMemo(editPos : Int, editData: MemoData){
        val currentArray = memoLiveData.value
        if (currentArray != null){
            val updatedArray = currentArray.toMutableList()
            updatedArray[editPos] = editData
            memoLiveData.postValue(updatedArray.toTypedArray())
        }

    }

    /* Removes memo from liveData and posts value. */
    fun deleteMemo(deleted : MemoData){
        val currentArray = memoLiveData.value
        if (currentArray != null){
            val updatedArray = currentArray.toMutableList()
            updatedArray.remove(deleted)
            memoLiveData.postValue(updatedArray.toTypedArray())
        }
    }

    fun findIdByPosition(pos : Int) : Int{
        val currentArray = memoLiveData.value
        if (currentArray != null){
            val observedArray = currentArray.toMutableList()
            return observedArray[pos].memoId
        }
        return 0
    }

    fun getMemoList() : LiveData<Array<MemoData>> = memoLiveData

    companion object {
        private var INSTANCE: MemoDataSource? = null

        fun getMemoDataSource() : MemoDataSource{
            return synchronized(MemoDataSource::class){
                val newInstance = INSTANCE ?: MemoDataSource()
                INSTANCE = newInstance
                newInstance
            }
        }
    }
}