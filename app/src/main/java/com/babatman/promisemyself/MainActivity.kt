package com.babatman.promisemyself

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.babatman.promisemyself.ads.AdManager
import com.babatman.promisemyself.ui.MainScreen
import com.babatman.promisemyself.ui.theme.PromiseMyselfTheme
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.util.Locale

class MainActivity : ComponentActivity() {
    private lateinit var adManager: AdManager
    private var showAd by mutableStateOf(true)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Set the locale based on system settings
        val locale = resources.configuration.locales[0]
        Locale.setDefault(locale)
        resources.configuration.setLocale(locale)
        resources.updateConfiguration(resources.configuration, resources.displayMetrics)
        
        adManager = AdManager(this)

        setContent {
            PromiseMyselfTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    if (showAd) {
                        LaunchedEffect(Unit) {
                            adManager.showInterstitialAd(this@MainActivity) {
                                showAd = false
                            }
                        }
                    } else {
                        MainScreen()
                    }
                }
            }
        }
    }
}
