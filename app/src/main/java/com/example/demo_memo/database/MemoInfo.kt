package com.example.demo_memo.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "memo_info")
data class MemoInfo (
    @PrimaryKey(autoGenerate = true) var id : Long?,
    @ColumnInfo(name = "memo_title") var title : String,
    @ColumnInfo(name = "memo_text") var text : String,
    @ColumnInfo(name = "memo_favorite") var is_favorite : Boolean)
{
    constructor(): this(null, "", "", false)
}