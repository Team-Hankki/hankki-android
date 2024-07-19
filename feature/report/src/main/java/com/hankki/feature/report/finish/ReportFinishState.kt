package com.hankki.feature.report.finish

import com.hankki.core.designsystem.component.bottomsheet.JogboResponseModel
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

data class ReportFinishState(
    val count: Long = 0,
    val name: String = "",
    val storeName: String = "",
    val storeId: Long = 0,
    val showBottomSheet: Boolean = false,
    val jogboItems: PersistentList<JogboResponseModel> = persistentListOf(),
)
