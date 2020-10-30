package com.falconAdSdk.banner

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.AttributeSet
import android.view.View.OnClickListener
import androidx.appcompat.widget.AppCompatImageView
import com.falconAdSdk.R
import com.falconAdSdk.controller.ApiController
import com.falconAdSdk.extenstions.loadImageWithGif
import com.falconAdSdk.model.InterstitialAdModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


open class AdView:AppCompatImageView {

    private var interstitialAdModel:InterstitialAdModel? = null

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {

        initBannerAdView()
    }

    private fun initBannerAdView(){
        //val params: ViewGroup.LayoutParams = getLayoutParams()
        //params.height = 130
        //setLayoutParams(params)

        maxHeight = context.resources.getDimensionPixelSize(R.dimen.banner_max_height)
        minimumHeight = context.resources.getDimensionPixelSize(R.dimen.banner_max_height)
        isClickable = true
        scaleType = ScaleType.CENTER_CROP
        setOnClickListener(onClickListener)
    }

    fun loadAd(){
        getBannerAdFromServer()
    }

    private fun getBannerAdFromServer(){
        ApiController.provideApi().getBannerAd().enqueue(object : Callback<InterstitialAdModel> {

            override fun onResponse(call: Call<InterstitialAdModel>, response: Response<InterstitialAdModel>) {
                if (response.isSuccessful){
                    interstitialAdModel = response.body()
                    loadBannerAdImage()
                }else{

                }
            }

            override fun onFailure(call: Call<InterstitialAdModel>, t: Throwable) { }

        })
    }


    private fun loadBannerAdImage(){
        loadImageWithGif(interstitialAdModel?.ad_image_url)
    }

    private val onClickListener:OnClickListener = OnClickListener {
        if(interstitialAdModel?.ad_source_url!=null){
            registerAdClickToServer(interstitialAdModel?.hash_id)
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(interstitialAdModel?.ad_source_url))
            context.startActivity(browserIntent)
        }
    }

    private fun registerAdClickToServer(hashId:String?){
        ApiController.provideApi().registerAdClick(hashId).enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) { }
            override fun onFailure(call: Call<String>, t: Throwable) { }
        })
    }

}