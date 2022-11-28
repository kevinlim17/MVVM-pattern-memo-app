package com.example.demo_memo.database

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


interface MemoInfoDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(memoInfo: MemoInfo)

    @Delete
    suspend fun delete(memoInfo: MemoInfo)

    @Query("DELETE FROM memo_info")
    suspend fun deleteAll()

    @Query("SELECT * FROM memo_info ORDER BY memo_title ASC")
    fun getAll() : Flow<List<MemoInfo>>

    @Query("SELECT * FROM memo_info WHERE memo_favorite LIKE :search_det")
    fun getAllFavorite(search_det : Boolean) : Flow<List<MemoInfo>>

}