package com.example.demo_memo.viewmodel

import androidx.lifecycle.*
import com.example.demo_memo.database.MemoInfo
import com.example.demo_memo.database.MemoInfoRepository
import kotlinx.coroutines.launch

class MainViewModel(private val repository: MemoInfoRepository) : ViewModel() {

    private val memoInfoData : LiveData<List<MemoInfo>> = repository.allMemoInfo.asLiveData()

    fun insertMemo(updatedMemoInfo : MemoInfo) = viewModelScope.launch {
        repository.insertMemo(updatedMemoInfo)
    }

    fun deleteMemo(currentMemoInfo: MemoInfo) = viewModelScope.launch {
        repository.deleteMemo(currentMemoInfo)
    }
}

class MainViewModelFactory(private val repository: MemoInfoRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}

