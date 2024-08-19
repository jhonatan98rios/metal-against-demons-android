package com.teixeirarios.mad.lib.admob

import android.content.Context
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
import com.google.android.gms.ads.identifier.AdvertisingIdClient
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.teixeirarios.mad.BuildConfig.ENVIRONMENT
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object AdmobService {

    private var initialized = false
    private lateinit var testDevices: List<String>
    private lateinit var bannerId: String
    private lateinit var interstitialId: String
    private lateinit var context: AppCompatActivity

    var mInterstitialAd: InterstitialAd? = null

    fun init(context: AppCompatActivity) {
        if (this.initialized) return

        this.initialized = true
        this.context = context

        if (ENVIRONMENT == "dev") {
            this.bannerId = "ca-app-pub-3940256099942544/9214589741" // Dev
            this.interstitialId = "ca-app-pub-3940256099942544/1033173712" // Dev
        } else {
            this.bannerId = "ca-app-pub-1739197497968733/5627124674" // Prod
            this.interstitialId = "ca-app-pub-1739197497968733/8533006530" // Prod
        }

        // Set your test devices!
        this.testDevices = listOf(
            "96a77442-4baf-46e7-89d2-a7dccbbcc285",
            "129181be-cd60-4b97-bd7f-9ced39acde56",
            "a181c863-68da-4894-a418-48607b90b7c8",
        )

        // Initialize the Google Mobile Ads SDK.
        MobileAds.initialize(context) {}
        MobileAds.setRequestConfiguration(
            RequestConfiguration
                .Builder()
                .setTestDeviceIds(testDevices)
                .build()
        )
    }

    @Composable
    fun Banner(modifier: Modifier = Modifier) {
        AndroidView(
            modifier = modifier,
            factory = { context ->
                // on below line specifying ad view.
                AdView(context).apply {
                    setAdSize(AdSize.BANNER)
                    adUnitId = bannerId
                    loadAd(AdRequest.Builder().build())
                }
            },
            update = { adView ->
                // Reload ad if needed
                adView.loadAd(AdRequest.Builder().build())
            }
        )
    }

    fun showInterstialAd() {
        InterstitialAd.load(
            context,
            interstitialId, //Change this with your own AdUnitID!
            AdRequest.Builder().build(),
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    mInterstitialAd = null
                }

                override fun onAdLoaded(ad: InterstitialAd) {
                    mInterstitialAd = ad
                    mInterstitialAd!!.show(context)
                }
            },
        )
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun getAdvertisingId(context: Context) {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val adInfo = AdvertisingIdClient.getAdvertisingIdInfo(context)
                val adId = adInfo.id
                print("Advertising ID: $adId")
                withContext(Dispatchers.Main) {
                    Log.d("AdmobService", "Advertising ID: $adId")
                    print("Advertising ID: $adId")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}