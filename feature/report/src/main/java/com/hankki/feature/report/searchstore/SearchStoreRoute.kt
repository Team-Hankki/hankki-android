package com.hankki.feature.report.searchstore

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hankki.core.common.extension.addFocusCleaner
import com.hankki.core.common.extension.noRippleClickable
import com.hankki.core.designsystem.R
import com.hankki.core.designsystem.component.button.HankkiButton
import com.hankki.core.designsystem.component.layout.BottomBlurLayout
import com.hankki.core.designsystem.component.layout.TopBlurLayout
import com.hankki.core.designsystem.component.textfield.HankkiSearchTextField
import com.hankki.core.designsystem.component.topappbar.HankkiTopBar
import com.hankki.core.designsystem.theme.Gray100
import com.hankki.core.designsystem.theme.Gray900
import com.hankki.core.designsystem.theme.HankkiTheme
import com.hankki.core.designsystem.theme.HankkijogboTheme
import com.hankki.core.designsystem.theme.White
import com.hankki.feature.report.model.LocationModel
import com.hankki.feature.report.searchstore.component.LocationItem
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun SearchStoreRoute() {

}

@Composable
fun SearchStoreScreen(
    value: String,
    locations: PersistentList<LocationModel>,
    navigateUp: () -> Unit,
) {
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .background(White)
            .statusBarsPadding()
            .navigationBarsPadding()
            .fillMaxSize()
            .addFocusCleaner(focusManager)
    ) {
        HankkiTopBar(
            modifier = Modifier.background(White),
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_left),
                    contentDescription = "Back",
                    modifier = Modifier
                        .padding(start = 9.dp)
                        .size(44.dp)
                        .noRippleClickable(navigateUp)
                )
            }
        )
        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier
                .padding(horizontal = 22.dp)
        ) {
            Text(
                text = "식당이름으로 검색하면\n주소를 찾아드릴게요",
                style = HankkiTheme.typography.suitH2,
                color = Gray900
            )
            Spacer(modifier = Modifier.height(18.dp))
            HankkiSearchTextField(
                value = value,
                onTextChanged = {},
                onFocusChanged = {},
                clearText = { /*TODO*/ }
            )
        }

        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                item {
                     Spacer(modifier = Modifier.height(24.dp))
                }
                items(locations) { item ->
                    LocationItem(
                        location = item.location,
                        address = item.address,
                        onClick = {}
                    )

                    if (item != locations.last()) {
                        HorizontalDivider(color = Gray100)
                    }
                }
                item {
                    BottomBlurLayout()
                }
            }

            TopBlurLayout(
                modifier = Modifier.align(Alignment.TopCenter)
            )

            Box(
                modifier = Modifier.align(Alignment.BottomCenter),
                contentAlignment = Alignment.BottomCenter
            ) {
                BottomBlurLayout()
                HankkiButton(
                    text = "식당을 선택해주세요",
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 22.dp)
                        .padding(bottom = 15.dp)
                )
            }
        }

    }
}

@Preview
@Composable
fun SearchStoreScreenPreview() {
    HankkijogboTheme {
        SearchStoreScreen(
            "고동밥집",
            locations = persistentListOf(
                LocationModel(
                    location = "고동밥집 1호점",
                    address = "서울특별시 마포구 갈매기 고양이처럼 울음 ",
                    longitude = 0.0,
                    latitude = 0.0
                ),
                LocationModel(
                    location = "고동밥집 1호점",
                    address = "서울특별시 마포구 갈매기 고양이처럼 울음 ",
                    longitude = 0.0,
                    latitude = 0.0
                ),
                LocationModel(
                    location = "고동밥집 1호점",
                    address = "서울특별시 마포구 갈매기 고양이처럼 울음 ",
                    longitude = 0.0,
                    latitude = 0.0
                ),
                LocationModel(
                    location = "고동밥집 1호점",
                    address = "서울특별시 마포구 갈매기 고양이처럼 울음 ",
                    longitude = 0.0,
                    latitude = 0.0
                ),
                LocationModel(
                    location = "고동밥집 1호점",
                    address = "서울특별시 마포구 갈매기 고양이처럼 울음 ",
                    longitude = 0.0,
                    latitude = 0.0
                ),
                LocationModel(
                    location = "고동밥집 1호점",
                    address = "서울특별시 마포구 갈매기 고양이처럼 울음 ",
                    longitude = 0.0,
                    latitude = 0.0
                ),
                LocationModel(
                    location = "고동밥집 1호점",
                    address = "서울특별시 마포구 갈매기 고양이처럼 울음 ",
                    longitude = 0.0,
                    latitude = 0.0
                ),
                LocationModel(
                    location = "고동밥집 1호점",
                    address = "서울특별시 마포구 갈매기 고양이처럼 울음 ",
                    longitude = 0.0,
                    latitude = 0.0
                ),
                LocationModel(
                    location = "고동밥집 1호점",
                    address = "서울특별시 마포구 갈매기 고양이처럼 울음 ",
                    longitude = 0.0,
                    latitude = 0.0
                ),
            )
        ) {}
    }
}
