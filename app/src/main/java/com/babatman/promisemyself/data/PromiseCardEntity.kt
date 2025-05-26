package com.babatman.promisemyself.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "promise_cards")
data class PromiseCardEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val cardNumber: Int,
    val text: String,
    val gradientIndex: Int,
    val date: String,
) 