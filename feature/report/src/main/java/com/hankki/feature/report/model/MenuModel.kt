package com.hankki.feature.report.model

import com.hankki.domain.report.entity.MenuEntity

data class MenuModel(
    val name: String = "",
    val price: String = "",
    val isPriceError: Boolean = false
)

fun MenuEntity.toMenuModel() = MenuModel(name, price.toString())
fun MenuModel.toMenuEntity() = MenuEntity(name, price.toLong())
