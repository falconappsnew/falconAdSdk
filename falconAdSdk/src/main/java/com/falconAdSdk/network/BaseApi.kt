package com.falconAdSdk.network

import com.falconAdSdk.model.InterstitialAdModel
import retrofit2.Call
import retrofit2.http.*


interface BaseApi {

    @Headers("Content-Type: application/json")
    @GET("get-interstitial-ad.php")
    fun getInterstitialAd(): Call<InterstitialAdModel>

    @Headers("Content-Type: application/json")
    @GET("get-banner-ad.php")
    fun getBannerAd(): Call<InterstitialAdModel>

    @Headers("Content-Type: application/json")
    @GET("register-ad-click.php")
    fun registerAdClick(@Query("hash_id") hash_id:String?): Call<String>

    @Headers("Content-Type: application/json")
    @GET("register-interstitial-ad-impression.php")
    fun registerInterstitialAdImpression(@Query("hash_id") hash_id:String?): Call<String>
}