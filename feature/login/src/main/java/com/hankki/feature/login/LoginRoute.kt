package com.hankki.feature.login

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.hankki.core.common.extension.noRippleClickable
import com.hankki.core.designsystem.component.dialog.SingleButtonDialog
import com.hankki.core.designsystem.theme.HankkiTheme
import com.hankki.core.designsystem.theme.HankkijogboTheme
import com.hankki.core.designsystem.theme.White
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import kotlinx.coroutines.flow.collectLatest

@Composable
fun LoginRoute(
    isLoginDialogNeed: Boolean,
    navigateToHome: () -> Unit,
    navigateToOnboarding: () -> Unit,
) {
    val viewModel: LoginViewModel = hiltViewModel()
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current
    val isButtonEnabled by viewModel.isButtonEnabled.collectAsStateWithLifecycle()
    val state by viewModel.loginState.collectAsStateWithLifecycle()

    LaunchedEffect(true) {
        if (isLoginDialogNeed) {
            viewModel.updateLoginDialog()
        }
    }

    LaunchedEffect(viewModel.loginSideEffects, lifecycleOwner) {
        viewModel.loginSideEffects
            .flowWithLifecycle(lifecycleOwner.lifecycle)
            .collectLatest { sideEffect ->
                when (sideEffect) {
                    is LoginSideEffect.LoginSuccess -> {
                        if (sideEffect.isRegistered) {
                            navigateToHome()
                        } else {
                            navigateToOnboarding()
                        }
                    }

                    is LoginSideEffect.LoginError -> {
                        // login error
                    }

                    is LoginSideEffect.StartKakaoTalkLogin -> {
                        startKakaoTalkLogin(context) { token, error ->
                            viewModel.handleLoginResult(token, error)
                        }
                    }

                    is LoginSideEffect.StartKakaoWebLogin -> {
                        startKakaoWebLogin(context) { token, error ->
                            viewModel.handleLoginResult(token, error)
                        }
                    }
                }
            }
    }

    LoginScreen(
        isButtonEnabled = isButtonEnabled,
        onLoginClick = {
            viewModel.startKakaoLogin(
                isKakaoTalkAvailable = UserApiClient.instance.isKakaoTalkLoginAvailable(context)
            )
        },
        loginDialogState = state.loginDialogState,
        updateLoginDialogState = viewModel::updateLoginDialog
    )
}


@Composable
fun LoginScreen(
    isButtonEnabled: Boolean,
    onLoginClick: () -> Unit,
    loginDialogState: Boolean,
    updateLoginDialogState: () -> Unit
) {

    if (loginDialogState) {
        SingleButtonDialog(
            title = stringResource(R.string.deleted_jogbo),
            buttonTitle = stringResource(R.string.check),
            onConfirmation = {
                updateLoginDialogState()
            }
        )
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.login),
            contentDescription = "Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .align(Alignment.TopStart)
                .fillMaxSize()
        ) {
            Spacer(modifier = Modifier.weight(0.1f))

            Image(
                imageVector = ImageVector.vectorResource(id = R.drawable.logo),
                contentDescription = "Logo",
                modifier = Modifier
                    .padding(start = 22.dp)
            )

            Spacer(modifier = Modifier.weight(0.0175f))

            Text(
                text = stringResource(R.string.done_worry),
                color = White,
                style = HankkiTheme.typography.sub1,
                modifier = Modifier
                    .padding(start = 22.dp)
                    .align(Alignment.Start)
            )

            Spacer(modifier = Modifier.weight(0.8825f))
        }

        Image(
            painter = painterResource(id = R.drawable.btn_kakao),
            contentDescription = "Kakao Login Button",
            modifier = Modifier
                .noRippleClickable {
                    if (isButtonEnabled) {
                        onLoginClick()
                    }
                }
                .align(Alignment.BottomCenter)
                .padding(bottom = 24.dp)
                .padding(horizontal = 22.dp)
                .navigationBarsPadding()
        )
    }
}

private fun startKakaoTalkLogin(
    context: Context,
    handleLogin: (OAuthToken?, Throwable?) -> Unit,
) {
    UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
        handleLogin(token, error)
    }
}

private fun startKakaoWebLogin(
    context: Context,
    handleLogin: (OAuthToken?, Throwable?) -> Unit,
) {
    UserApiClient.instance.loginWithKakaoAccount(context) { token, error ->
        handleLogin(token, error)
    }
}

@Composable
@Preview
fun LoginPreview() {
    HankkijogboTheme {
        LoginScreen(
            isButtonEnabled = true,
            onLoginClick = {},
            loginDialogState = false,
            updateLoginDialogState = {},
        )
    }
}
