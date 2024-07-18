package com.hankki.feature.my.mypage

import android.content.Intent
import android.net.Uri
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
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import coil.compose.AsyncImage
import com.hankki.core.common.extension.noRippleClickable
import com.hankki.core.designsystem.component.dialog.DoubleButtonDialog
import com.hankki.core.designsystem.component.topappbar.HankkiTopBar
import com.hankki.core.designsystem.theme.Gray400
import com.hankki.core.designsystem.theme.Gray900
import com.hankki.core.designsystem.theme.HankkiTheme
import com.hankki.core.designsystem.theme.HankkijogboTheme
import com.hankki.core.designsystem.theme.Red
import com.hankki.core.designsystem.theme.White
import com.hankki.feature.my.R
import com.hankki.feature.my.component.ButtonWithArrowIcon
import com.hankki.feature.my.component.ButtonWithImageAndBorder
import com.hankki.feature.my.mypage.MyViewModel.Companion.FAQ
import com.hankki.feature.my.mypage.MyViewModel.Companion.FAQ_PAGE
import com.hankki.feature.my.mypage.MyViewModel.Companion.INQUIRY
import com.hankki.feature.my.mypage.MyViewModel.Companion.INQUIRY_PAGE
import com.hankki.feature.my.mypage.MyViewModel.Companion.LIKE
import com.hankki.feature.my.mypage.MyViewModel.Companion.REPORT
import com.hankki.feature.my.mypage.model.MySideEffect

@Composable
fun MyRoute(
    paddingValues: PaddingValues,
    navigateToJogbo: () -> Unit,
    navigateToStore: (String) -> Unit,
    myViewModel: MyViewModel = hiltViewModel()
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val myState by myViewModel.myState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(true) {
        myViewModel.getUserInformation()
    }

    LaunchedEffect(myViewModel.mySideEffect, lifecycleOwner) {
        myViewModel.mySideEffect.flowWithLifecycle(lifecycleOwner.lifecycle).collect { sideEffect ->
            when (sideEffect) {
                is MySideEffect.ShowWebView -> {
                    val url = when (sideEffect.type) {
                        FAQ -> FAQ_PAGE
                        INQUIRY -> INQUIRY_PAGE
                        else -> return@collect
                    }
                    context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
                }
            }
        }
    }

    MyScreen(
        paddingValues = paddingValues,
        navigateToMyJogbo = navigateToJogbo,
        navigateToMyStore = navigateToStore,
        userName = myState.myModel.nickname,
        userImage = myState.myModel.profileImageUrl,
        showDialog = myState.showDialog,
        showWebView = myViewModel::showWebView,
        updateDialog = myViewModel::updateDialogState
    )
}

@Composable
fun MyScreen(
    paddingValues: PaddingValues,
    navigateToMyJogbo: () -> Unit,
    navigateToMyStore: (String) -> Unit,
    userName: String,
    userImage: String,
    showDialog: DialogState,
    showWebView: (String) -> Unit,
    updateDialog: (DialogState) -> Unit,
) {
    val scrollState = rememberScrollState()

    if (showDialog != DialogState.CLOSED) {
        DoubleButtonDialog(
            title = if (showDialog == DialogState.LOGOUT) stringResource(R.string.ask_logout) else stringResource(
                R.string.disappear_jogbo
            ),
            negativeButtonTitle = stringResource(id = R.string.go_back),
            positiveButtonTitle = if (showDialog == DialogState.LOGOUT) stringResource(id = R.string.logout) else stringResource(
                R.string.quit
            ),
            onNegativeButtonClicked = { updateDialog(DialogState.CLOSED) },
            onPositiveButtonClicked = {} //TODO: 로그아웃 api
        )
    }

    Column(
        modifier = Modifier
            .padding(paddingValues)
            .padding(horizontal = 22.dp)
            .fillMaxSize()
            .background(White)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HankkiTopBar(
            content = {
                Text(
                    text = stringResource(id = R.string.my),
                    color = Gray900,
                    style = HankkiTheme.typography.sub3
                )
            }
        )

        Spacer(modifier = Modifier.height(15.dp))

        AsyncImage(
            modifier = Modifier
                .size(98.dp)
                .clip(CircleShape),
            model = userImage,
            contentDescription = stringResource(R.string.profile_image),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = stringResource(R.string.message_user_name, userName),
            color = Gray900,
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
                .clip(RoundedCornerShape(12.dp))
                .padding(start = 28.dp, end = 17.dp)
                .noRippleClickable(onClick = navigateToMyJogbo),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = stringResource(R.string.my_jogbo),
                style = HankkiTheme.typography.sub1,
                color = White,
            )
            Image(
                painter = painterResource(id = R.drawable.ic_my_graphic),
                contentDescription = "jogbo graphic",
            )
        }

        Spacer(modifier = Modifier.height(19.dp))

        Row {
            ButtonWithImageAndBorder(
                R.drawable.ic_report,
                stringResource(R.string.description_store_report),
                Modifier
                    .weight(1f)
                    .noRippleClickable(onClick = { navigateToMyStore(REPORT) }),
            )
            Spacer(modifier = Modifier.width(18.dp))
            ButtonWithImageAndBorder(
                R.drawable.ic_good,
                stringResource(R.string.description_store_like),
                Modifier
                    .weight(1f)
                    .noRippleClickable(onClick = { navigateToMyStore(LIKE) }),
            )
        }

        ButtonWithArrowIcon(stringResource(R.string.faq), { showWebView(FAQ) })

        ButtonWithArrowIcon(stringResource(R.string.inquiry), { showWebView(INQUIRY) })

        ButtonWithArrowIcon(stringResource(R.string.logout), { updateDialog(DialogState.LOGOUT) })

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
                .noRippleClickable(onClick = {}),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                text = stringResource(R.string.quit),
                modifier = Modifier
                    .noRippleClickable(onClick = { updateDialog(DialogState.QUIT) })
                    .padding(top = 13.dp, bottom = 14.dp)
                    .weight(1f),
                textAlign = TextAlign.End,
                style = HankkiTheme.typography.body4,
                color = Gray400,
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_quit),
                contentDescription = stringResource(id = R.string.quit),
                tint = Gray400,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun MyScreenPreview() {
    HankkijogboTheme {
        MyScreen(
            paddingValues = PaddingValues(),
            navigateToMyJogbo = {},
            navigateToMyStore = {},
            userName = "",
            userImage = "",
            showDialog = DialogState.CLOSED,
            updateDialog = {},
            showWebView = {_->}
        )
    }
}


enum class DialogState {
    CLOSED,
    LOGOUT,
    QUIT
}

