package com.android.example.tanmen.Model

import java.io.Serializable

data class Shop(
    var image: String,
    var name: String,
    var address: String?,
    var hours: String?
): Serializable