package jp.re.tanmen.model.Entity

import java.io.Serializable

data class Shop(
    val image: String,
    val name: String,
    val address: String?,
    val hours: String?
): Serializable