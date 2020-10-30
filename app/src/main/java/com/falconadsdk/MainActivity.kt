package com.falconadsdk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.falconAdSdk.FalconInterstitialAd
import com.falconAdSdk.banner.AdView
import com.falconAdSdk.listener.FalconAdListener

class MainActivity : AppCompatActivity() {

    private var falconInterstitialAd : FalconInterstitialAd? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        loadBannerAd()

        falconInterstitialAd = FalconInterstitialAd(this)
        falconInterstitialAd?.setAdListener(falconAdListener)
        falconInterstitialAd?.loadAd()

    }

    private fun loadBannerAd(){
        val adView = findViewById<AdView>(R.id.adView)
        adView.loadAd()
    }

    private val falconAdListener:FalconAdListener = object :FalconAdListener{

        override fun onAdClosed() {
            super.onAdClosed()
        }

        override fun onAdFailedToLoad() {
            super.onAdFailedToLoad()

            //Toast.makeText(this@MainActivity,"Error",Toast.LENGTH_LONG).show()
        }

        override fun onAdLoaded() {
            if(falconInterstitialAd?.isAdLoaded==true){
                falconInterstitialAd?.showAd()
            }
        }

        override fun onAdOpened() {
            super.onAdOpened()
        }

    }
}