package com.douglasrondini.drive_20_android.data.model

import com.google.gson.annotations.SerializedName

data class UpdatePriceRequest(
    @SerializedName("precoAula") val price: Double
)
