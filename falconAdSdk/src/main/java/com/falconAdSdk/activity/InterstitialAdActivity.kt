package com.falconAdSdk.activity

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.AppCompatImageView
import com.falconAdSdk.R
import com.falconAdSdk.constants.FalconAdConstants
import com.falconAdSdk.controller.ApiController
import com.falconAdSdk.extenstions.loadImageWithGif
import com.falconAdSdk.model.InterstitialAdModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class InterstitialAdActivity :AppCompatActivity(){

    companion object{
        fun getIntent(context:Context,interstitialAdModel: InterstitialAdModel?):Intent{
            val intent = Intent(context,InterstitialAdActivity::class.java)
            intent.apply {
                putExtra(FalconAdConstants.KEY_INTERSTITIAL_AD_MODEL, interstitialAdModel)
            }
            return intent
        }
    }
    private var ivInterstitialAd:AppCompatImageView? = null
    private var btnCloseInterstitial:AppCompatImageButton? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_interstitial_dialog)

        ivInterstitialAd = findViewById(R.id.ivInterstitialAd)
        btnCloseInterstitial = findViewById(R.id.btnCloseInterstitial)

        btnCloseInterstitial?.setOnClickListener(onClickListener)
        ivInterstitialAd?.setOnClickListener(onClickListener)
        setInterstitialImage()
    }

    private fun getInterstitialAdUrl():String?{
        return (intent?.extras?.getSerializable(FalconAdConstants.KEY_INTERSTITIAL_AD_MODEL) as InterstitialAdModel?)?.ad_image_url
    }

    private fun getInterstitialSourceUrl():String?{
        return (intent?.extras?.getSerializable(FalconAdConstants.KEY_INTERSTITIAL_AD_MODEL) as InterstitialAdModel?)?.ad_source_url
    }

    private fun getInterstitialAdHashId():String?{
        return (intent?.extras?.getSerializable(FalconAdConstants.KEY_INTERSTITIAL_AD_MODEL) as InterstitialAdModel?)?.hash_id
    }

    private fun setInterstitialImage(){
        ivInterstitialAd?.loadImageWithGif(getInterstitialAdUrl())
    }

    private val onClickListener:View.OnClickListener = View.OnClickListener {
        when(it.id){
            R.id.btnCloseInterstitial -> {
                finish()
            }
            R.id.ivInterstitialAd -> {
                val sourceUrl = getInterstitialSourceUrl()
                val hashId = getInterstitialAdHashId()
                if(sourceUrl!=null){
                    registerAdClickToServer(hashId)
                    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(sourceUrl))
                    startActivity(browserIntent)
                    finish()
                }
            }
        }
    }

    private fun registerAdClickToServer(hashId:String?){
        ApiController.provideApi().registerAdClick(hashId).enqueue(object : Callback<String> {

            override fun onResponse(call: Call<String>, response: Response<String>) {
                
            }

            override fun onFailure(call: Call<String>, t: Throwable) {

            }

        })
    }

}