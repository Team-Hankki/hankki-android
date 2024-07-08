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
import com.hankki.core.designsystem.component.button.HankkiButton
import com.hankki.core.designsystem.theme.Gray200
import com.hankki.core.designsystem.theme.Gray400
import com.hankki.core.designsystem.theme.Gray900
import com.hankki.core.designsystem.theme.HankkiTheme
import com.hankki.domain.universityselection.UniversitySelectionModel
import com.hankki.feature.universityselection.component.UniversityItem
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList

@Composable
fun UniversitySelectionRoute(navigateToHome: () -> Unit) {
    val universitySelectionViewModel: UniversitySelectionViewModel = hiltViewModel()
    val universitySelectionState by universitySelectionViewModel.universitySelectionState.collectAsStateWithLifecycle()

    UniversitySelectionScreen(
        universities = universitySelectionState.universities.toPersistentList(),
        selectedUniversity = universitySelectionState.selectedUniversity,
        onSelectUniversity = { universitySelectionViewModel.selectUniversity(it) },
        navigateHome = navigateToHome
    )
}

@Composable
fun UniversitySelectionScreen(
    universities: PersistentList<UniversitySelectionModel>,
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
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            items(universities) { university ->
                UniversityItem(
                    university = university,
                    isSelected = selectedUniversity == university.name,
                    onSelectUniversity = onSelectUniversity
                )
                if (university != universities.last()) {
                    HorizontalDivider(thickness = 1.dp, color = Gray200)
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))
        HankkiButton(
            text = stringResource(id = R.string.select),
            textStyle = HankkiTheme.typography.sub2,
            onClick = navigateHome,
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
                .noRippleClickable(navigateHome)
        )
        Spacer(modifier = Modifier.height(10.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewUniversityScreen() {
    val dummyData = listOf(
        UniversitySelectionModel(1, "한양대"), UniversitySelectionModel(2, "성신여대"),
        UniversitySelectionModel(3, "성균관대"), UniversitySelectionModel(4, "건국대"),
        UniversitySelectionModel(5, "경희대"), UniversitySelectionModel(6, "외대"),
        UniversitySelectionModel(7, "연세대"), UniversitySelectionModel(8, "이화여대"),
        UniversitySelectionModel(9, "홍익대"), UniversitySelectionModel(10, "숭실대"),
        UniversitySelectionModel(11, "고려대"), UniversitySelectionModel(12, "중앙대"),
        UniversitySelectionModel(13, "동국대"), UniversitySelectionModel(14, "서강대"),
        UniversitySelectionModel(15, "경기대"), UniversitySelectionModel(16, "숙명여대"),
        UniversitySelectionModel(17, "단국대"), UniversitySelectionModel(18, "명지대"),
        UniversitySelectionModel(19, "서울대"), UniversitySelectionModel(20, "국민대")
    ).sortedBy { it.name }.toPersistentList()

    UniversitySelectionScreen(
        universities = dummyData,
        selectedUniversity = null,
        onSelectUniversity = {},
        navigateHome = {}
    )
}