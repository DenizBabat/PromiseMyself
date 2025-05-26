package com.babatman.promisemyself.data

import java.text.SimpleDateFormat
import java.util.*

data class PromiseCard(
    val text: String,
    val gradientIndex: Int = (System.currentTimeMillis() % 8).toInt(),
    val date: String = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(Date()),
    val cardNumber: Int = 0  // This will store the card's permanent number
) 