package com.hankki.feature.my.mypage

import android.content.Intent
import android.net.Uri
import android.os.Handler
import android.os.Looper
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.hankki.core.common.amplitude.EventType
import com.hankki.core.common.amplitude.LocalTracker
import com.hankki.core.common.extension.noRippleClickable
import com.hankki.core.common.utill.UiState
import com.hankki.core.designsystem.component.dialog.DoubleButtonDialog
import com.hankki.core.designsystem.component.layout.EmptyImageWithText
import com.hankki.core.designsystem.component.layout.HankkiLoadingScreen
import com.hankki.core.designsystem.theme.Gray400
import com.hankki.core.designsystem.theme.Gray50
import com.hankki.core.designsystem.theme.Gray500
import com.hankki.core.designsystem.theme.Gray900
import com.hankki.core.designsystem.theme.HankkiTheme
import com.hankki.core.designsystem.theme.HankkijogboTheme
import com.hankki.core.designsystem.theme.White
import com.hankki.feature.my.R
import com.hankki.feature.my.mypage.MyViewModel.Companion.INQUIRY
import com.hankki.feature.my.mypage.MyViewModel.Companion.INQUIRY_PAGE
import com.hankki.feature.my.mypage.MyViewModel.Companion.LIKE
import com.hankki.feature.my.mypage.MyViewModel.Companion.REPORT
import com.hankki.feature.my.mypage.MyViewModel.Companion.TERMS_OF_USE
import com.hankki.feature.my.mypage.MyViewModel.Companion.TERMS_OF_USE_PAGE
import com.hankki.feature.my.mypage.component.ArrowIconButton
import com.hankki.feature.my.mypage.component.IconButton
import com.hankki.feature.my.mypage.model.MyModel
import com.jakewharton.processphoenix.ProcessPhoenix

@Composable
fun MyRoute(
    paddingValues: PaddingValues,
    navigateUp: () -> Unit,
    navigateToJogbo: (Boolean) -> Unit,
    navigateToStore: (String) -> Unit,
    myViewModel: MyViewModel = hiltViewModel(),
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val state by myViewModel.myState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(true) {
        myViewModel.getUserInformation()
    }

    LaunchedEffect(myViewModel.mySideEffect, lifecycleOwner) {
        myViewModel.mySideEffect.flowWithLifecycle(lifecycleOwner.lifecycle).collect { sideEffect ->
            when (sideEffect) {
                is MySideEffect.ShowWebView -> {
                    val url = when (sideEffect.type) {
                        TERMS_OF_USE -> TERMS_OF_USE_PAGE
                        INQUIRY -> INQUIRY_PAGE
                        else -> return@collect
                    }
                    context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
                }

                is MySideEffect.ShowLogoutSuccess -> {
                    Handler(Looper.getMainLooper()).post {
                        ProcessPhoenix.triggerRebirth(context)
                    }
                }

                is MySideEffect.ShowDeleteWithdrawSuccess -> {
                    Handler(Looper.getMainLooper()).post {
                        ProcessPhoenix.triggerRebirth(context)
                    }
                }
            }
        }
    }

    BackHandler(onBack = navigateUp)

    MyScreen(
        paddingValues = paddingValues,
        navigateToMyJogbo = navigateToJogbo,
        navigateToMyStore = navigateToStore,
        state = state,
        showWebView = myViewModel::showWebView,
        updateDialog = myViewModel::updateDialogState,
        onLogout = myViewModel::logout,
        onDeleteWithdraw = myViewModel::deleteWithdraw
    )
}

@Composable
fun MyScreen(
    paddingValues: PaddingValues,
    navigateToMyJogbo: (Boolean) -> Unit,
    navigateToMyStore: (String) -> Unit,
    state: MyState,
    showWebView: (String) -> Unit,
    updateDialog: (DialogState) -> Unit,
    onLogout: () -> Unit,
    onDeleteWithdraw: () -> Unit,
) {
    val tracker = LocalTracker.current
    val scrollState = rememberScrollState()

    when (state.uiState) {
        UiState.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(White)
            ) {
                HankkiLoadingScreen(modifier = Modifier.align(Alignment.Center))
            }
        }

        is UiState.Success -> {
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .background(White),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(30.dp))

                    Row(
                        modifier = Modifier
                            .padding(start = 22.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Image(
                            modifier = Modifier
                                .size(62.dp)
                                .clip(CircleShape),
                            imageVector = ImageVector.vectorResource(R.drawable.img_user_profile),
                            contentDescription = null,
                            contentScale = ContentScale.Crop
                        )

                        Column(
                            modifier = Modifier.padding(start = 14.dp)
                        ) {
                            Text(
                                text = stringResource(
                                    R.string.message_user_name,
                                    state.myModel.nickname
                                ),
                                color = Gray900,
                                style = HankkiTheme.typography.suitH3,
                                textAlign = TextAlign.Start
                            )
                            Text(
                                text = stringResource(
                                    R.string.message_user,
                                    state.myModel.nickname
                                ),
                                color = Gray500,
                                style = HankkiTheme.typography.suitBody4,
                                textAlign = TextAlign.Start
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    Row(
                        modifier = Modifier.padding(horizontal = 24.dp),
                        horizontalArrangement = Arrangement.spacedBy(18.dp)
                    ) {
                        IconButton(
                            modifier = Modifier.weight(1f),
                            image = R.drawable.ic_myjokbo,
                            text = stringResource(R.string.my_jogbo),
                            onclick = {
                                tracker.track(
                                    type = EventType.CLICK,
                                    name = "Mypage_MyJokbo"
                                )
                                navigateToMyJogbo(false)
                            }
                        )

                        IconButton(
                            modifier = Modifier.weight(1f),
                            image = R.drawable.ic_newjaebo,
                            text = stringResource(R.string.description_store_report),
                            onclick = {
                                navigateToMyStore(REPORT)
                            }
                        )

                        IconButton(
                            modifier = Modifier.weight(1f),
                            image = R.drawable.ic_myheart,
                            text = stringResource(R.string.description_store_like),
                            onclick = {
                                navigateToMyStore(LIKE)
                            }
                        )
                    }

                    Spacer(
                        modifier = Modifier
                            .padding(top = 18.dp)
                            .fillMaxWidth()
                            .height(10.dp)
                            .background(Gray50)
                    )

                    Column(modifier = Modifier.padding(horizontal = 22.dp)) {

                        ArrowIconButton(
                            stringResource(R.string.inquiry),
                            { showWebView(INQUIRY) })

                        ArrowIconButton(
                            stringResource(R.string.terms_of_use),
                            { showWebView(TERMS_OF_USE) })

                        ArrowIconButton(
                            stringResource(R.string.logout),
                            { updateDialog(DialogState.LOGOUT) })

                        Spacer(modifier = Modifier.height(4.dp))

                        Row(
                            modifier = Modifier,
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.End
                        ) {
                            Text(
                                text = stringResource(R.string.quit),
                                modifier = Modifier
                                    .padding(top = 13.dp, bottom = 14.dp)
                                    .noRippleClickable(onClick = { updateDialog(DialogState.QUIT) })
                                    .weight(1f),
                                textAlign = TextAlign.End,
                                style = HankkiTheme.typography.body6,
                                color = Gray400,
                            )
                            Image(
                                imageVector = ImageVector.vectorResource(R.drawable.ic_quit),
                                contentDescription = stringResource(id = R.string.quit),
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                }
            }
        }

        is UiState.Failure -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                EmptyImageWithText(
                    text = stringResource(id = com.hankki.core.designsystem.R.string.error_text)
                )
            }
        }
    }

    if (state.dialogSate != DialogState.CLOSED) {
        DoubleButtonDialog(
            title = if (state.dialogSate == DialogState.LOGOUT) stringResource(R.string.ask_logout) else stringResource(
                R.string.disappear_jogbo
            ),
            positiveButtonTitle = stringResource(id = R.string.maintain),
            negativeButtonTitle = if (state.dialogSate == DialogState.LOGOUT) stringResource(id = R.string.logout) else stringResource(
                R.string.quit
            ),
            onPositiveButtonClicked = { updateDialog(DialogState.CLOSED) },
            onNegativeButtonClicked = {
                if (state.dialogSate == DialogState.LOGOUT) {
                    onLogout()
                } else if (state.dialogSate == DialogState.QUIT) {
                    onDeleteWithdraw()
                }
                updateDialog(DialogState.CLOSED)
            },
            isAntiPattern = true
        )
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
            state = MyState(
                myModel = MyModel(nickname = "John Doe"),
                uiState = UiState.Success(MyModel(nickname = "John Doe"))
            ),
            updateDialog = {},
            showWebView = { _ -> },
            onLogout = {},
            onDeleteWithdraw = {},
        )
    }
}


enum class DialogState {
    CLOSED,
    LOGOUT,
    QUIT
}
