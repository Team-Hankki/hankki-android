package com.hankki.feature.my

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.hankki.core.common.extension.noRippleClickable
import com.hankki.core.designsystem.theme.Gray400
import com.hankki.core.designsystem.theme.HankkiTheme
import com.hankki.core.designsystem.theme.HankkijogboTheme
import com.hankki.core.designsystem.theme.Red
import com.hankki.core.designsystem.theme.White
import com.hankki.domain.my.entity.UserInfoEntity
import com.hankki.feature.my.component.ButtonWithArrowIcon
import com.hankki.feature.my.component.ButtonWithImageAndBorder
import com.hankki.feature.my.component.MyTitle

@Composable
fun MyRoute(
    paddingValues: PaddingValues,
    navigateToDummy: () -> Unit,
    myViewModel: MyViewModel = hiltViewModel()
) {
    val myState = myViewModel.myState.collectAsStateWithLifecycle().value

    LaunchedEffect(true) {
        myViewModel.loadMockInformation()
    }

    MyScreen(paddingValues, navigateToDummy = navigateToDummy, myState)
}

@Composable
fun MyScreen(
    paddingValues: PaddingValues,
    navigateToDummy: () -> Unit,
    myState: MyState
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .padding(paddingValues)
            .padding(horizontal = 22.dp)
            .fillMaxSize()
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MyTitle(title = stringResource(R.string.title))
        Spacer(modifier = Modifier.height(15.dp))
        AsyncImage(
            modifier = Modifier
                .size(98.dp)
                .clip(CircleShape),
            model = myState.userState.image,
            contentDescription = stringResource(R.string.profile_image),
            contentScale = ContentScale.None
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = stringResource(R.string.sub_title, myState.userState.name),
            color = com.hankki.core.designsystem.theme.Gray900,
            style = HankkiTheme.typography.suitH2,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(19.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Red,
                    shape = RoundedCornerShape(12.dp)
                )
                .clip(RoundedCornerShape(12.dp)),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = stringResource(R.string.my_jogbo),
                style = HankkiTheme.typography.sub1,
                color = White,
                modifier = Modifier.padding(start = 28.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.ic_mygraphic),
                contentDescription = null,
                modifier = Modifier.padding(end = 29.dp)
            )
        }
        Spacer(modifier = Modifier.height(19.dp))
        Row {
            ButtonWithImageAndBorder(
                R.drawable.ic_report,
                stringResource(R.string.description_store_report),
                Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(18.dp))
            ButtonWithImageAndBorder(
                R.drawable.ic_good,
                stringResource(R.string.description_store_like),
                Modifier.weight(1f)
            )
        }
        ButtonWithArrowIcon(stringResource(R.string.faq), navigateToDummy)
        ButtonWithArrowIcon(stringResource(R.string.inquiry), navigateToDummy)
        ButtonWithArrowIcon(stringResource(R.string.logout), navigateToDummy)
        Text(
            text = stringResource(R.string.quit),
            modifier = Modifier
                .fillMaxWidth()
                .noRippleClickable()
                .padding(top = 16.dp, end = 2.dp),
            textAlign = TextAlign.End,
            style = HankkiTheme.typography.body4,
            color = Gray400
        )
    }
}


@Preview(showBackground = true)
@Composable
fun MyScreenPreview() {
    HankkijogboTheme {
        MyScreen(
            paddingValues = PaddingValues(), navigateToDummy = {}, myState = MyState(
                UserInfoEntity(name = "송한끼", image = R.drawable.ic_good)
            )
        )
    }
}