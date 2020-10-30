package com.falconAdSdk.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class InterstitialAdModel(
    @SerializedName("hash_id")
    var hash_id:String? = null,

    @SerializedName("ad_image_url")
    var ad_image_url:String? = null,

    @SerializedName("ad_source_url")
    var ad_source_url:String? = null
):Serializable