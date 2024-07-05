package com.hankki.feature.universityselection

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
import com.hankki.core.designsystem.component.HankkiButton
import com.hankki.core.designsystem.theme.Gray200
import com.hankki.core.designsystem.theme.Gray400
import com.hankki.core.designsystem.theme.Gray800
import com.hankki.core.designsystem.theme.Gray900
import com.hankki.core.designsystem.theme.Red
import com.hankki.core.designsystem.theme.hankkiTypography
import com.hankki.domain.dummy.entity.response.UniversityModel
import com.hankki.feature.university.R

@Composable
fun UniversitySelectionRoute(navigateToHome: () -> Unit) {
    val universitySelectionViewModel: UniversitySelectionViewModel = hiltViewModel()
    val universities by universitySelectionViewModel.universities.collectAsStateWithLifecycle()
    val selectedUniversity by universitySelectionViewModel.selectedUniversity.collectAsStateWithLifecycle()

    UniversitySelectionScreen(
        universities = universities,
        selectedUniversity = selectedUniversity,
        onSelectUniversity = { universitySelectionViewModel.selectUniversity(it) },
        navigateHome = navigateToHome
    )
}

@Composable
fun UniversitySelectionScreen(
    universities: List<UniversityModel>,
    selectedUniversity: String?,
    onSelectUniversity: (String) -> Unit,
    navigateHome: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 22.dp)
            .navigationBarsPadding(),
    ) {
        Spacer(modifier = Modifier.height(68.dp))
        Text(
            text = stringResource(id = R.string.select_university),
            style = hankkiTypography().h1,
            color = Gray900
        )

        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = stringResource(id = R.string.wait_a_minute),
            style = hankkiTypography().body4,
            color = Gray400
        )

        Spacer(modifier = Modifier.height(34.dp))
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            items(universities) { university ->
                Text(
                    text = university.name,
                    style = hankkiTypography().body1,
                    modifier = Modifier
                        .fillMaxWidth()
                        .noRippleClickable {
                            onSelectUniversity(university.name)
                        }
                        .padding(14.dp),
                    color = if (selectedUniversity == university.name) Red else Gray800
                )
                HorizontalDivider(thickness = 1.dp, color = Gray200)
            }
        }

        Spacer(modifier = Modifier.height(10.dp))
        HankkiButton(
            text = stringResource(id = R.string.select),
            textStyle = hankkiTypography().sub2,
            onClick = navigateHome,
            modifier = Modifier
                .fillMaxWidth(),
            enabled = selectedUniversity != null
        )

        Spacer(modifier = Modifier.height(14.dp))

        Text(
            text = stringResource(id = R.string.no_university_looking),
            style = hankkiTypography().caption1,
            color = Gray400,
            textDecoration = TextDecoration.Underline,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .noRippleClickable(navigateHome)
        )
        Spacer(modifier = Modifier.height(10.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewUniversityScreen() {
    val dummyData = listOf(
        UniversityModel(1, "한양대"), UniversityModel(2, "성신여대"),
        UniversityModel(3, "성균관대"), UniversityModel(4, "건국대"),
        UniversityModel(5, "경희대"), UniversityModel(6, "외대"),
        UniversityModel(7, "연세대"), UniversityModel(8, "이화여대"),
        UniversityModel(9, "홍익대"), UniversityModel(10, "숭실대"),
        UniversityModel(11, "고려대"), UniversityModel(12, "중앙대"),
        UniversityModel(13, "동국대"), UniversityModel(14, "서강대"),
        UniversityModel(15, "경기대"), UniversityModel(16, "숙명여대"),
        UniversityModel(17, "단국대"), UniversityModel(18, "명지대"),
        UniversityModel(19, "서울대"), UniversityModel(20, "국민대")
    ).sortedBy { it.name }

    UniversitySelectionScreen(
        universities = dummyData,
        selectedUniversity = null,
        onSelectUniversity = {},
        navigateHome = {}
    )
}