package com.hankki.feature.universityselection.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hankki.core.common.extension.noRippleClickable
import com.hankki.core.designsystem.theme.Gray800
import com.hankki.core.designsystem.theme.HankkiTheme
import com.hankki.core.designsystem.theme.Red
import com.hankki.domain.universityselection.entity.UniversitySelectionEntity

@Composable
fun UniversityItem(
    university: UniversitySelectionEntity,
    isSelected: Boolean,
    onSelectUniversity: (String) -> Unit
) {
    Text(
        text = university.name,
        style = if (isSelected) HankkiTheme.typography.sub3 else HankkiTheme.typography.body1,
        modifier = Modifier
            .fillMaxWidth()
            .noRippleClickable {
                onSelectUniversity(university.name)
            }
            .padding(14.dp),
        color = if (isSelected) Red else Gray800
    )
}