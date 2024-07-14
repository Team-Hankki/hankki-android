package com.hankki.feature.my

import com.hankki.domain.my.entity.MyJogboDetailEntity
import com.hankki.domain.my.entity.Store
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

data class MyJogboDetailState(
    val myStoreItems: PersistentList<MyJogboDetailEntity> = persistentListOf(
        MyJogboDetailEntity(
            title = "",
            tags = listOf("", ""),
            stores = listOf(
                Store(0, "", "", "", 0, 0)
            )
        )
    )
)