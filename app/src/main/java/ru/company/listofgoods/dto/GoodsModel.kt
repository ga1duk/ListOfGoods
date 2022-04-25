package ru.company.listofgoods.dto

data class GoodsModel(
    val status: String,
    val TOVARY: List<TovaryModel>
)

data class TovaryModel(
    val ID: Int,
    val NAME: String,
    val data: List<DataModel>
)

data class DataModel(
    val ID: Int,
    val NAME: String,
    val DETAIL_PICTURE: String,
    val EXTENDED_PRICE: List<PriceModel>
)

data class PriceModel(
    val PRICE: Double
)
