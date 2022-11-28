package com.example.demo_memo.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [MemoInfo::class], version = 1)
abstract class MemoInfoDB : RoomDatabase(){
    abstract fun memoInfoDao() : MemoInfoDAO

    private class MemoInfoDBCallback(private val scope : CoroutineScope) : Callback(){
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let {
                scope.launch {
                    var memoInfoDao = it.memoInfoDao()
                    memoInfoDao.deleteAll()
                }
            }

        }
    }

    //DB Instance를 싱글톤으로 사용하기 위해 Companion Object 사용
    companion object {
        private var INSTANCE : MemoInfoDB? = null

        //여러 Thread가 접근하지 못하도록 Synchronized로 설정
        fun getDatabase(context: Context, scope : CoroutineScope) : MemoInfoDB? {
            if (INSTANCE == null){
                synchronized(this){
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        MemoInfoDB::class.java,
                        "object-info-database"
                    ).addCallback(MemoInfoDBCallback(scope))
                        .build()
                }
            }
            return INSTANCE
        }
    }
}