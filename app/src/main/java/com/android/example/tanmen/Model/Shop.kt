package com.android.example.tanmen.Model

import com.squareup.picasso.RequestCreator
import java.io.Serializable

data class Shop(
    var image: RequestCreator,
    var name: String,
    var address: String?,
    var hours: String?
): Serializable