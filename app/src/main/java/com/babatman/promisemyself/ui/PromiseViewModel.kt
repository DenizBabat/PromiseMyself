package com.babatman.promisemyself.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.babatman.promisemyself.data.AppDatabase
import com.babatman.promisemyself.data.PromiseCard
import com.babatman.promisemyself.data.PromiseRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class PromiseViewModel(application: Application) : AndroidViewModel(application){
    private val _promiseCards = MutableStateFlow<List<PromiseCard>>(emptyList())
    val promiseCards: StateFlow<List<PromiseCard>> = _promiseCards.asStateFlow()
    val database = AppDatabase.getDatabase(application)

    init {
        var repository = PromiseRepository(database.promiseCardDao())

        // Load cards from database
        CoroutineScope(Dispatchers.IO).launch {
            repository.allCards.map { entities ->
                entities.map { entity ->
                    PromiseCard(
                        cardNumber = entity.cardNumber,
                        text = entity.text,
                        gradientIndex = entity.gradientIndex,
                        date = entity.date
                    )
                }
            }.collect { cards ->
                _promiseCards.value = cards
            }
        }
    }

    fun addPromiseCard(text: String) {
        val currentCards = _promiseCards.value
        val newCard = PromiseCard(
            text = text,
            cardNumber = currentCards.size + 1
        )
        var repository = PromiseRepository(database.promiseCardDao())
        repository.insertCard(com.babatman.promisemyself.data.PromiseCardEntity(
            cardNumber = newCard.cardNumber,
            text = newCard.text,
            gradientIndex = newCard.gradientIndex,
            date = newCard.date
        ))
        _promiseCards.value = currentCards + newCard
    }

    fun getWordCount(): Int {
        return _promiseCards.value.sumOf { it.text.split("\\s+".toRegex()).size }
    }

    fun moveTopToBottom() {
        val currentCards = _promiseCards.value
        if (currentCards.isNotEmpty()) {
            val newCards = currentCards.drop(1) + currentCards.first()
            _promiseCards.value = newCards
        }
    }

    fun moveBottomToTop() {
        val currentCards = _promiseCards.value
        if (currentCards.isNotEmpty()) {
            val newCards = listOf(currentCards.last()) + currentCards.dropLast(1)
            _promiseCards.value = newCards
        }
    }
} 