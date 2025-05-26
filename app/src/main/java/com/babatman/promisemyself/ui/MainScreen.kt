package com.babatman.promisemyself.ui

import android.app.Application
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.babatman.promisemyself.ui.components.CardDeck
import com.babatman.promisemyself.ui.components.PageBackground
import com.babatman.promisemyself.ui.theme.PrimaryLight
import com.babatman.promisemyself.ui.theme.SecondaryLight
import com.babatman.promisemyself.ui.theme.AccentLight
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.AndroidViewModel
import com.babatman.promisemyself.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    viewModel: PromiseViewModel = viewModel()
) {
    val cards by viewModel.promiseCards.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    var newPromiseText by remember { mutableStateOf("") }

    // Counter section animations
    val infiniteTransition = rememberInfiniteTransition(label = "counterAnimation")
    
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(20000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotationAnimation"
    )

    val shimmerOffset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "shimmerAnimation"
    )

    Box(modifier = Modifier.fillMaxSize()) {
        // Animated page background
        PageBackground(
            modifier = Modifier.fillMaxSize()
        )

        // Main content
        Scaffold(
            containerColor = Color.Transparent,
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { showDialog = true },
                    containerColor = PrimaryLight,
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                    elevation = FloatingActionButtonDefaults.elevation(
                        defaultElevation = 6.dp,
                        pressedElevation = 12.dp
                    )
                ) {
                    Icon(Icons.Default.Add, contentDescription = stringResource(R.string.add_promise))
                }
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Enhanced counter section
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp)
                        .clip(RoundedCornerShape(24.dp))
                        .drawBehind {
                            // Draw rotating gradient background
                            rotate(rotation) {
                                val gradient = Brush.sweepGradient(
                                    colors = listOf(
                                        PrimaryLight.copy(alpha = 0.7f),
                                        SecondaryLight.copy(alpha = 0.7f),
                                        AccentLight.copy(alpha = 0.7f),
                                        PrimaryLight.copy(alpha = 0.7f)
                                    )
                                )
                                drawRect(brush = gradient)
                            }

                            // Draw shimmer effect
                            val shimmerWidth = size.width * 0.3f
                            val shimmerX = size.width * shimmerOffset
                            drawRect(
                                brush = Brush.horizontalGradient(
                                    colors = listOf(
                                        Color.White.copy(alpha = 0f),
                                        Color.White.copy(alpha = 0.2f),
                                        Color.White.copy(alpha = 0f)
                                    ),
                                    startX = shimmerX - shimmerWidth,
                                    endX = shimmerX + shimmerWidth
                                )
                            )
                        }
                        .padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = stringResource(R.string.promises_made),
                            style = MaterialTheme.typography.titleLarge.copy(
                                color = Color.White,
                                fontWeight = FontWeight.Medium
                            )
                        )
                        
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        Text(
                            text = cards.size.toString(),
                            style = MaterialTheme.typography.displayLarge.copy(
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 48.sp
                            )
                        )
                        
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        Text(
                            text = when {
                                cards.isEmpty() -> stringResource(R.string.celebrate1)
                                cards.size < 5 -> stringResource(R.string.celebrate2)
                                cards.size < 10 -> stringResource(R.string.celebrate3)
                                cards.size < 20 -> stringResource(R.string.celebrate4)
                                else -> stringResource(R.string.celebrate5)
                            },
                            style = MaterialTheme.typography.bodyLarge.copy(
                                color = Color.White.copy(alpha = 0.9f),
                                fontWeight = FontWeight.Medium
                            )
                        )
                    }
                }

                // Middle section - Card deck
                CardDeck(
                    cards = cards,
                    modifier = Modifier.weight(1f),
                    onSwipeRight = { viewModel.moveTopToBottom() },
                    onSwipeLeft = { viewModel.moveBottomToTop() }
                )

                // Bottom section - Emojis
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp)
                        .clip(RoundedCornerShape(24.dp))
                        .background(MaterialTheme.colorScheme.secondary.copy(alpha = 0.1f))
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    val emojiCount = (cards.size / 2).coerceAtMost(5)
                    repeat(emojiCount) {
                        Text(
                            text = when (it) {
                                0 -> "🌟"
                                1 -> "✨"
                                2 -> "💫"
                                3 -> "🎯"
                                else -> "🎉"
                            },
                            style = MaterialTheme.typography.headlineLarge,
                            modifier = Modifier.padding(horizontal = 8.dp)
                        )
                    }
                }
            }
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = {
                Text(
                    stringResource(R.string.add_promise),
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
            },
            text = {
                OutlinedTextField(
                    value = newPromiseText,
                    onValueChange = { newPromiseText = it },
                    label = { Text(stringResource(R.string.promise_hint)) },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        focusedLabelColor = MaterialTheme.colorScheme.primary
                    )
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        if (newPromiseText.isNotBlank()) {
                            viewModel.addPromiseCard(newPromiseText)
                            newPromiseText = ""
                            showDialog = false
                        }
                    }
                ) {
                    Text(
                        stringResource(R.string.save),
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text(
                        stringResource(R.string.cancel),
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }
        )
    }
} 