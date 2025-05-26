package com.babatman.promisemyself.data

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class PromiseRepository(private val promiseCardDao: PromiseCardDao) {
    val allCards: Flow<List<PromiseCardEntity>> = promiseCardDao.getAllCards()
    val cardCount: Flow<Int> = promiseCardDao.getCardCount()

    fun insertCard(card: PromiseCardEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            promiseCardDao.insertCard(card)
        }
    }

    suspend fun deleteCard(card: PromiseCardEntity) {
        promiseCardDao.deleteCard(card)
    }

    suspend fun deleteAllCards() {
        promiseCardDao.deleteAllCards()
    }
} 