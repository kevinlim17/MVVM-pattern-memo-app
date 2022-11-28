package com.example.demo_memo.application

import android.app.Application
import com.example.demo_memo.database.MemoInfoDB
import com.example.demo_memo.database.MemoInfoRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class MemoApplication : Application() {
    private val applicationScope = CoroutineScope(SupervisorJob())

    private val database by lazy { MemoInfoDB.getDatabase(this, applicationScope) }
    val repository by lazy { MemoInfoRepository(database!!.memoInfoDao()) }

}