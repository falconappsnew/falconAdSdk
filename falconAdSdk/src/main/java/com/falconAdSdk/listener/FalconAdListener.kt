package com.falconAdSdk.listener

interface FalconAdListener {

    fun onAdClosed() {}

    fun onAdFailedToLoad() {}

    fun onAdOpened() {}

    fun onAdLoaded() {}
}