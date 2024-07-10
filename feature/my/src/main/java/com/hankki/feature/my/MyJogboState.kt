package com.hankki.feature.my

import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

data class MyJogboState(
    val myJogboItems: PersistentList<MyJogboModel> = persistentListOf()
)