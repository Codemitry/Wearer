package com.codemitry.wearer.models

import java.net.URI
import java.time.Year

data class ClothingItem(
    val name: String,
//    val types: Set<ClothingItemTypes>? = null,
    val brand: String? = null,
    val colors: Set<String>? = null,
    val size: String? = null,
    val purchaseYear: Year? = null,
    val materials: Set<String>? = null,
    val caringLabels: Set<CaringLabels>,
    val photo: URI
)


enum class CaringLabels {

}