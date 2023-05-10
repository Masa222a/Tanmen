package jp.example.tanmen.model.Entity

import java.io.Serializable

data class Shop(
    val image: String,
    val name: String,
    val address: String?,
    val hours: String?
): Serializable