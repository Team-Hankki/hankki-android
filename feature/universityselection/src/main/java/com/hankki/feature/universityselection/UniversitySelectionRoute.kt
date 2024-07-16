package com.hankki.feature.universityselection

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hankki.core.common.extension.noRippleClickable
import com.hankki.core.designsystem.component.button.HankkiButton
import com.hankki.core.designsystem.component.layout.BottomBlurLayout
import com.hankki.core.designsystem.theme.Gray200
import com.hankki.core.designsystem.theme.Gray400
import com.hankki.core.designsystem.theme.Gray900
import com.hankki.core.designsystem.theme.HankkiTheme
import com.hankki.core.designsystem.theme.White
import com.hankki.domain.universityselection.entity.UniversitySelectionEntity
import com.hankki.feature.universityselection.component.UniversityItem

@Composable
fun UniversitySelectionRoute(navigateToHome: () -> Unit) {
    val universitySelectionViewModel: UniversitySelectionViewModel = hiltViewModel()
    val universitySelectionState by universitySelectionViewModel.universitySelectionState.collectAsStateWithLifecycle()

    UniversitySelectionScreen(
        universities = universitySelectionState.universities,
        selectedUniversity = universitySelectionState.selectedUniversity,
        onSelectUniversity = { universitySelectionViewModel.selectUniversity(it) },
        onPostSelectedUniversity = {
            universitySelectionViewModel.postUniversity()
            navigateToHome()
        },
        navigateHome = navigateToHome
    )
}

@Composable
fun UniversitySelectionScreen(
    universities: List<UniversitySelectionEntity>,
    selectedUniversity: UniversitySelectionEntity?,
    onSelectUniversity: (UniversitySelectionEntity) -> Unit,
    onPostSelectedUniversity: () -> Unit,
    navigateHome: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
            .navigationBarsPadding(),
    ) {
        Spacer(modifier = Modifier.height(68.dp))

        Column(modifier = Modifier.padding(horizontal = 22.dp)) {
            Text(
                text = stringResource(id = R.string.select_university),
                style = HankkiTheme.typography.h1,
                color = Gray900
            )

            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = stringResource(id = R.string.wait_a_minute),
                style = HankkiTheme.typography.body4,
                color = Gray400
            )

            Spacer(modifier = Modifier.height(34.dp))
        }

        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.BottomCenter
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 22.dp)
            ) {
                items(universities) { university ->
                    UniversityItem(
                        university = university,
                        isSelected = selectedUniversity == university,
                        onSelectUniversity = { onSelectUniversity(university) }
                    )
                    if (university != universities.last()) {
                        HorizontalDivider(thickness = 1.dp, color = Gray200)
                    }
                }
                item {
                    BottomBlurLayout(imageBlur = com.hankki.core.designsystem.R.drawable.img_white_gradient_bottom_middle)
                }
            }

            BottomBlurLayout(imageBlur = com.hankki.core.designsystem.R.drawable.img_white_gradient_bottom_middle)

            Column(
                modifier = Modifier
                    .noRippleClickable()
                    .padding(horizontal = 22.dp)
            ) {
                HankkiButton(
                    text = stringResource(id = R.string.select),
                    textStyle = HankkiTheme.typography.sub2,
                    onClick = {
                        navigateHome
                        onPostSelectedUniversity()
                    },
                    modifier = Modifier
                        .fillMaxWidth(),
                    enabled = selectedUniversity != null
                )

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = stringResource(id = R.string.no_university_looking),
                    style = HankkiTheme.typography.caption1,
                    color = Gray400,
                    textDecoration = TextDecoration.Underline,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .noRippleClickable(onClick = navigateHome)
                )
                Spacer(modifier = Modifier.height(31.dp))
            }
        }
    }
}
