package com.example.roomapp.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface WordsDao {

    @Query("SELECT * FROM words LIMIT 5")
    fun getAll(): Flow<List<Word>>

    @Query("SELECT * FROM words WHERE name= :nameWord")
    suspend fun getWordByName(nameWord: String): Word

    @Insert(onConflict = OnConflictStrategy.REPLACE) //todo
    suspend fun insert(word: Word)

    @Query("DELETE FROM words")
    suspend fun clearAll()

    @Update
    suspend fun update(word: Word)
}