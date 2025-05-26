package com.babatman.promisemyself.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface PromiseCardDao {
    @Query("SELECT * FROM promise_cards ORDER BY cardNumber ASC")
    fun getAllCards(): Flow<List<PromiseCardEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCard(card: PromiseCardEntity)

    @Delete
    suspend fun deleteCard(card: PromiseCardEntity)

    @Query("SELECT COUNT(*) FROM promise_cards")
    fun getCardCount(): Flow<Int>

    @Query("DELETE FROM promise_cards")
    suspend fun deleteAllCards()
} 