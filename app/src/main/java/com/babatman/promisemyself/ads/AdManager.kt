package com.babatman.promisemyself.ads

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

class AdManager(private val context: Context) {
    private var interstitialAd: InterstitialAd? = null
    private var onAdClosed: (() -> Unit)? = null

    init {
        MobileAds.initialize(context)
        loadInterstitialAd()
    }

    private fun loadInterstitialAd() {
        val adRequest = AdRequest.Builder().build()

        try {
            val applicationInfo = context.packageManager.getApplicationInfo(
                context.packageName,
                PackageManager.GET_META_DATA
            )
            val admobAppId = applicationInfo.metaData.getString("com.google.android.gms.ads.APPLICATION_ID") ?: "ca-app-pub-3940256099942544/1033173712"
            // admobAppId'yi kullanabilirsiniz

            InterstitialAd.load(
                context,
                admobAppId, //"ca-app-pub-3940256099942544/1033173712", // Test ad unit ID

                adRequest,
                object : InterstitialAdLoadCallback() {
                    override fun onAdLoaded(ad: InterstitialAd) {
                        interstitialAd = ad
                        setupFullScreenCallback()
                    }

                    override fun onAdFailedToLoad(error: LoadAdError) {
                        interstitialAd = null
                    }
                }
            )
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }


    }

    private fun setupFullScreenCallback() {
        interstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdDismissedFullScreenContent() {
                interstitialAd = null
                onAdClosed?.invoke()
                loadInterstitialAd() // Load the next ad
            }

            override fun onAdFailedToShowFullScreenContent(error: AdError) {
                interstitialAd = null
                onAdClosed?.invoke()
                loadInterstitialAd()
            }
        }
        interstitialAd?.show(context as Activity)
    }

    fun showInterstitialAd(activity: Activity, onAdClosed: () -> Unit) {
        this.onAdClosed = onAdClosed
        
        if (interstitialAd != null) {
            interstitialAd?.show(activity)
        } else {
            onAdClosed()
            loadInterstitialAd()
        }
    }
} 