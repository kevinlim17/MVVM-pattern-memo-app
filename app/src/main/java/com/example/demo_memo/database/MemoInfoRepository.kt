package com.example.demo_memo.database

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class MemoInfoRepository(private val memoInfoDao: MemoInfoDAO) {

    val allMemoInfo : Flow<List<MemoInfo>> = memoInfoDao.getAll()
    val allFavoriteMemoInfo : Flow<List<MemoInfo>> = memoInfoDao.getAllFavorite(true)

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertMemo(memoInfo: MemoInfo){
        memoInfoDao.insert(memoInfo)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteMemo(memoInfo: MemoInfo){
        memoInfoDao.delete(memoInfo)
    }

}