package com.example.bitfitapp

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface DiaryDao {
    @Query("SELECT * FROM diary_table")
    fun getAll(): Flow<List<DiaryEntity>>

    @Insert
    fun insertAll(diaries: List<DiaryEntity>)

    @Insert
    fun insert(diary: DiaryEntity)

    @Query("DELETE FROM diary_table")
    fun deleteAll()
}