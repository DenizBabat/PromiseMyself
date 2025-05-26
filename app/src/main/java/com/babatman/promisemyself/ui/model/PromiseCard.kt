package com.babatman.promisemyself.ui.model

import com.babatman.promisemyself.ui.theme.CardGradient
import java.time.LocalDateTime

data class PromiseCard(
    val id: Long = 0,
    val cardNumber: Int,
    val text: String,
    val gradient: CardGradient,
    val date: LocalDateTime,
    val emojis: List<String> = emptyList()
) 