package com.arena.eventos.database.model

import java.io.File
data class ExpositorType(
    var docId: String = "",
    val logoUrl: String = "",
    //val latitude: Double? = null,
    //val longitude: Double? = null,
    val id: Double? = null,
    val ref: String? = "",
    val name: String = "",
    val description: String = "",
    val labelId: Double? = null,
    val products: List<ProductTypes> = emptyList(),
    val websiteUrl: String? = null
)
data class ProductTypes(
    val productName: String = "",
    val productDescription: String = "",
    val productCover: String = "",
    //val productCoverFile: File? = null,
    val id: Double? = null
)
