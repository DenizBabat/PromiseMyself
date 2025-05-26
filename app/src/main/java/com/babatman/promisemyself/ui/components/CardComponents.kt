package com.babatman.promisemyself.ui.components

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.FileProvider
import com.babatman.promisemyself.data.PromiseCard
import com.babatman.promisemyself.ui.theme.CardGradient
import com.babatman.promisemyself.ui.theme.cardGradients
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import kotlin.math.abs

@Composable
fun PromiseCardItem(
    card: PromiseCard,
    modifier: Modifier = Modifier,
    rotation: Float = 0f,
    offsetX: Float = 0f,
    scale: Float = 1f,
    onClick: () -> Unit = {},
    showShareButton: Boolean = false
) {
    val gradient = cardGradients[card.gradientIndex]
    val textColor = gradient.getContrastingTextColor()
    var isFlipped by remember { mutableStateOf(false) }
    val rotationState by animateFloatAsState(
        targetValue = if (isFlipped) 180f else 0f,
        animationSpec = tween(500),
        label = "flipAnimation"
    )
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp)
            .rotate(rotation)
            .scale(scale)
            .offset(x = offsetX.dp)
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(16.dp),
                spotColor = gradient.start.copy(alpha = 0.5f)
            )
            .clickable { 
                isFlipped = !isFlipped
                onClick()
            },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            gradient.start,
                            gradient.end
                        )
                    )
                )
                .padding(16.dp)
        ) {
            // Front of card
            if (rotationState < 90f) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .graphicsLayer {
                            rotationY = rotationState
                        }
                ) {
                    // Card number
                    Text(
                        text = "#${card.cardNumber}",
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = textColor.copy(alpha = 0.8f),
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(bottom = 8.dp)
                    )
                    
                    // Share button (only for top card)
                    if (showShareButton) {
                        Box(
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(textColor.copy(alpha = 0.2f))
                                .clickable {
                                    scope.launch {
                                        // Create a bitmap of the card
                                        val bitmap = createCardBitmap(
                                            card = card,
                                            gradient = gradient
                                        )
                                        
                                        // Save the bitmap to a file
                                        val imageFile = saveBitmapToFile(context, bitmap)
                                        
                                        // Share the image
                                        shareImage(context, imageFile)
                                    }
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Share,
                                contentDescription = "Share Promise",
                                tint = textColor,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                    
                    // Promise text
                    Text(
                        text = card.text,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Medium,
                            color = textColor
                        ),
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.Center)
                            .padding(top = 16.dp)
                    )
                }
            }
            
            // Back of card
            if (rotationState >= 90f) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .graphicsLayer {
                            rotationY = rotationState - 180f
                        }
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Created on",
                            style = MaterialTheme.typography.titleMedium.copy(
                                color = textColor.copy(alpha = 0.8f)
                            )
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = card.date,
                            style = MaterialTheme.typography.headlineMedium.copy(
                                color = textColor,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                }
            }
        }
    }
}

private fun createCardBitmap(card: PromiseCard, gradient: CardGradient): Bitmap {
    // Create a bitmap with the card's dimensions
    val bitmap = Bitmap.createBitmap(800, 400, Bitmap.Config.ARGB_8888)
    val canvas = android.graphics.Canvas(bitmap)
    
    // Draw gradient background
    val paint = android.graphics.Paint().apply {
        shader = android.graphics.LinearGradient(
            0f, 0f,
            800f, 400f,
            gradient.start.toArgb(),
            gradient.end.toArgb(),
            android.graphics.Shader.TileMode.CLAMP
        )
    }
    canvas.drawRect(0f, 0f, 800f, 400f, paint)
    
    // Draw card number
    val numberPaint = android.graphics.Paint().apply {
        color = android.graphics.Color.WHITE
        alpha = 204 // 0.8 * 255
        textSize = 48f
        isFakeBoldText = true
    }
    canvas.drawText("#${card.cardNumber}", 40f, 60f, numberPaint)
    
    // Draw promise text
    val textPaint = android.graphics.Paint().apply {
        color = android.graphics.Color.WHITE
        textSize = 40f
        isFakeBoldText = true
    }
    val textBounds = android.graphics.Rect()
    textPaint.getTextBounds(card.text, 0, card.text.length, textBounds)
    val x = (800 - textBounds.width()) / 2f
    val y = (400 + textBounds.height()) / 2f
    canvas.drawText(card.text, x, y, textPaint)
    
    return bitmap
}

private fun saveBitmapToFile(context: android.content.Context, bitmap: Bitmap): File {
    val imagesDir = File(context.cacheDir, "images")
    imagesDir.mkdirs()
    val imageFile = File(imagesDir, "promise_card_${System.currentTimeMillis()}.png")
    
    FileOutputStream(imageFile).use { out ->
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
        out.flush()
    }
    
    return imageFile
}

private fun shareImage(context: android.content.Context, imageFile: File) {
    val contentUri = FileProvider.getUriForFile(
        context,
        "${context.packageName}.fileprovider",
        imageFile
    )
    
    val shareIntent = Intent().apply {
        action = Intent.ACTION_SEND
        type = "image/png"
        putExtra(Intent.EXTRA_STREAM, contentUri)
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }
    
    val chooserIntent = Intent.createChooser(shareIntent, "Share Promise Card")
    chooserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    context.startActivity(chooserIntent)
}

@Composable
fun CardDeck(
    cards: List<PromiseCard>,
    modifier: Modifier = Modifier,
    onSwipeRight: () -> Unit = {},
    onSwipeLeft: () -> Unit = {}
) {
    var offsetX by remember { mutableStateOf(0f) }
    var isDragging by remember { mutableStateOf(false) }
    
    val animatedOffsetX by animateFloatAsState(
        targetValue = if (isDragging) offsetX else 0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "offsetAnimation"
    )

    val scale by animateFloatAsState(
        targetValue = if (isDragging) 1.05f else 1f,
        animationSpec = tween(200),
        label = "scaleAnimation"
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(250.dp)
            .pointerInput(Unit) {
                detectHorizontalDragGestures(
                    onDragStart = { isDragging = true },
                    onDragEnd = {
                        isDragging = false
                        if (abs(offsetX) > size.width / 4) {
                            if (offsetX > 0) {
                                onSwipeRight()
                            } else {
                                onSwipeLeft()
                            }
                        }
                        offsetX = 0f
                    },
                    onHorizontalDrag = { _, dragAmount ->
                        offsetX += dragAmount
                    }
                )
            },
        contentAlignment = Alignment.Center
    ) {
        // Show last 3 cards
        val visibleCards = cards.takeLast(3)
        visibleCards.forEachIndexed { index, card ->
            PromiseCardItem(
                card = card,
                modifier = Modifier
                    .offset(y = (index * 8).dp)
                    .rotate((index * 2f)),
                rotation = (index * 2f),
                offsetX = if (index == 2) animatedOffsetX else 0f,
                scale = if (index == 2) scale else 1f,
                showShareButton = true  // Show share button on all visible cards
            )
        }
    }
} 