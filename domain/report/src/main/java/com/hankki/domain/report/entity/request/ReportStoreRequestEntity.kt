package com.hankki.domain.report.entity.request

data class ReportStoreRequestEntity(
    val name: String,
    val category: String,
    val address: String,
    val latitude: Double,
    val longitude: Double,
    val universityId: Long,
    val menus: List<MenuEntity>
){
    data class MenuEntity(
        val name: String,
        val price: String
    )
}
