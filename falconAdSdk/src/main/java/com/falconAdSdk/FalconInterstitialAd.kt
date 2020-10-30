package com.falconAdSdk

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.falconAdSdk.controller.ApiController
import com.falconAdSdk.activity.InterstitialAdActivity
import com.falconAdSdk.listener.FalconAdListener
import com.falconAdSdk.model.InterstitialAdModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

open class FalconInterstitialAd {

    private var context:Context? = null
    private var mFalconAdListener:FalconAdListener? = null
    private var interstitialAdModel:InterstitialAdModel? = null
    var isAdLoaded:Boolean = false

    constructor(context:Context){
        this.context = context
    }

    fun setAdListener(mFalconAdListener: FalconAdListener){
        this.mFalconAdListener = mFalconAdListener
    }

    fun loadAd(){
        getAdFromServer()
    }

    private fun loadInterstitialAdImage(){
        if(context==null && interstitialAdModel?.ad_image_url==null){
            falconAdListener.onAdFailedToLoad()
            return
        }
        Glide.with(context!!)
            .asBitmap()
            .load(interstitialAdModel?.ad_image_url).into(finish_target)
    }

    private fun getAdFromServer(){
        ApiController.provideApi().getInterstitialAd().enqueue(object : Callback<InterstitialAdModel> {

            override fun onResponse(call: Call<InterstitialAdModel>, response: Response<InterstitialAdModel>) {
                if (response.isSuccessful){
                    interstitialAdModel = response.body()
                    loadInterstitialAdImage()
                }else{
                    falconAdListener.onAdFailedToLoad()
                }
            }

            override fun onFailure(call: Call<InterstitialAdModel>, t: Throwable) {
                falconAdListener.onAdFailedToLoad()
            }

        })
    }

    val finish_target = object : CustomTarget<Bitmap>() {

        override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
            falconAdListener.onAdLoaded()
        }

        override fun onLoadStarted(placeholder: Drawable?) {
            super.onLoadStarted(placeholder)
        }

        override fun onLoadFailed(errorDrawable: Drawable?) {
            super.onLoadFailed(errorDrawable)
            falconAdListener.onAdFailedToLoad()
        }

        override fun onLoadCleared(placeholder: Drawable?) {

        }
    }

    fun showAd(){
        if(context!=null){
            context?.startActivity(InterstitialAdActivity.getIntent(context!!,interstitialAdModel))
        }
    }



    private val falconAdListener: FalconAdListener = object : FalconAdListener{

        override fun onAdClosed() {
            mFalconAdListener?.onAdClosed()
        }

        override fun onAdFailedToLoad() {
            isAdLoaded = false
            mFalconAdListener?.onAdFailedToLoad()
        }

        override fun onAdLoaded() {
            isAdLoaded = true
            mFalconAdListener?.onAdLoaded()
        }

        override fun onAdOpened() {
            mFalconAdListener?.onAdOpened()
        }
    }



}