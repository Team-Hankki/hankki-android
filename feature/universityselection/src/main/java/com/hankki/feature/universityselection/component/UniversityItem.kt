package com.hankki.feature.universityselection.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.hankki.core.common.extension.noRippleClickable
import com.hankki.core.designsystem.theme.Gray800
import com.hankki.core.designsystem.theme.HankkiTheme
import com.hankki.core.designsystem.theme.Red500
import com.hankki.domain.universityselection.entity.UniversitySelectionEntity

@Composable
fun UniversityItem(
    university: UniversitySelectionEntity,
    isSelected: Boolean,
    onSelectUniversity: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Text(
        text = university.name,
        style = if (isSelected) HankkiTheme.typography.sub3 else HankkiTheme.typography.body1,
        modifier = modifier
            .fillMaxWidth()
            .noRippleClickable {
                onSelectUniversity(university.name)
            },
        color = if (isSelected) Red500 else Gray800
    )
}
