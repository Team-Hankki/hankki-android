package com.hankki.feature.my.newjogbo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
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
import com.hankki.core.common.extension.addFocusCleaner
import com.hankki.core.common.extension.noRippleClickable
import com.hankki.core.designsystem.component.button.HankkiButton
import com.hankki.core.designsystem.component.button.HankkiExpandedButton
import com.hankki.core.designsystem.component.dialog.SingleButtonDialog
import com.hankki.core.designsystem.component.textfield.HankkiCountTextField
import com.hankki.core.designsystem.component.topappbar.HankkiTopBar
import com.hankki.core.designsystem.event.LocalSnackBarTrigger
import com.hankki.core.designsystem.theme.Gray400
import com.hankki.core.designsystem.theme.Gray900
import com.hankki.core.designsystem.theme.HankkiTheme
import com.hankki.core.designsystem.theme.HankkijogboTheme
import com.hankki.core.designsystem.theme.White
import com.hankki.feature.my.R

@Composable
fun NewJogboRoute(
    navigateUp: () -> Unit,
    navigateToMyJogbo: (Boolean) -> Unit,
    favoriteId: Long,
    isSharedJogbo: Boolean = false,
    newJogboViewModel: NewJogboViewModel = hiltViewModel(),
) {
    val state by newJogboViewModel.newJogboState.collectAsStateWithLifecycle()
    val lifecycleOwner = LocalLifecycleOwner.current
    val snackBar = LocalSnackBarTrigger.current

    LaunchedEffect(newJogboViewModel.newJogboSideEffect, lifecycleOwner) {
        newJogboViewModel.newJogboSideEffect.flowWithLifecycle(lifecycleOwner.lifecycle)
            .collect { sideEffect ->
                when (sideEffect) {
                    is NewJogboSideEffect.NavigateToNewJogbo -> {
                        if (isSharedJogbo) {
                            snackBar("족보가 추가되었습니다")
                            navigateToMyJogbo(false)
                        } else navigateUp()
                    }

                    is NewJogboSideEffect.ShowErrorDialog -> {
                        newJogboViewModel.updateErrorDialog(state.errorDialogState)
                        newJogboViewModel.resetTitle()
                    }
                }
            }
    }

    NewJogboScreen(
        navigateUp = navigateUp,
        title = state.title,
        onTitleChange = newJogboViewModel::setTitle,
        tags = state.tags,
        onTagsChange = newJogboViewModel::setTags,
        buttonEnabledState = state.buttonEnabled,
        editTagsLength = newJogboViewModel::editTagsLength,
        createNewJogbo = newJogboViewModel::createNewJogbo,
        createSharedJogbo = newJogboViewModel::createSharedJogbo,
        errorDialogState = state.errorDialogState,
        updateErrorDialogState = { newJogboViewModel.updateErrorDialog(state.errorDialogState) },
        isSharedJogbo = isSharedJogbo,
        favoriteId = favoriteId
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun NewJogboScreen(
    navigateUp: () -> Unit,
    title: String,
    onTitleChange: (String) -> Unit,
    tags: String,
    onTagsChange: (String) -> Unit,
    editTagsLength: (String) -> Int,
    buttonEnabledState: Boolean,
    createNewJogbo: () -> Unit,
    createSharedJogbo: (Long) -> Unit,
    errorDialogState: Boolean,
    updateErrorDialogState: () -> Unit,
    modifier: Modifier = Modifier,
    isSharedJogbo: Boolean,
    favoriteId: Long,
) {
    val isVisibleIme = WindowInsets.isImeVisible
    val focusManager = LocalFocusManager.current
    val tracker = LocalTracker.current

    if (errorDialogState) {
        SingleButtonDialog(
            title = stringResource(R.string.cannot_create_duplicated_name),
            description = stringResource(R.string.cannot_create_duplicated_jogbo_name),
            buttonTitle = stringResource(R.string.check),
            onConfirmation = updateErrorDialogState
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
            .background(White)
            .addFocusCleaner(focusManager),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HankkiTopBar(
            modifier = Modifier
                .padding(top = 19.dp)
                .height(40.dp),
            leadingIcon = {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.icon_back),
                    contentDescription = "Back",
                    modifier = Modifier
                        .padding(start = 10.dp)
                        .noRippleClickable(onClick = navigateUp),
                    tint = Color.Unspecified
                )
            })

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            modifier = Modifier
                .align(Alignment.Start)
                .padding(horizontal = 22.dp),
            textAlign = TextAlign.Start,
            text = if (isSharedJogbo) stringResource(R.string.create_shared_jogbo_name) else stringResource(
                R.string.create_new_jogbo
            ),
            style = HankkiTheme.typography.suitH1,
            color = Gray900
        )

        Text(
            modifier = Modifier
                .align(Alignment.Start)
                .padding(horizontal = 22.dp)
                .padding(top = 10.dp),
            textAlign = TextAlign.Start,
            text = if (isSharedJogbo) stringResource(R.string.create_shared_jogbo_name_description) else stringResource(
                R.string.create_new_jogbo_description
            ),
            style = HankkiTheme.typography.body6,
            color = Gray400
        )

        Spacer(modifier = Modifier.height(36.dp))

        HankkiCountTextField(
            modifier = Modifier.padding(horizontal = 22.dp),
            title = stringResource(R.string.jogbo_name),
            value = title,
            valueLength = title.length,
            placeholder = stringResource(R.string.name_example),
            onValueChanged = onTitleChange,
            trailingIcon = true,
            resetTitle = errorDialogState
        )

        Spacer(modifier = Modifier.height(22.dp))

        HankkiCountTextField(
            modifier = Modifier.padding(horizontal = 22.dp),
            title = stringResource(R.string.jobgo_tag),
            value = tags,
            valueLength = editTagsLength(tags),
            placeholder = stringResource(R.string.tag_example),
            onValueChanged = onTagsChange,
            trailingIcon = false,
            resetTitle = errorDialogState
        )

        Spacer(modifier = Modifier.weight(1f))

        if (isVisibleIme) {
            HankkiExpandedButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .imePadding(),
                text = if (isSharedJogbo) stringResource(R.string.add) else stringResource(R.string.make_jogbo),
                onClick = {
                    createSharedJogbo(favoriteId)
                    tracker.track(
                        type = EventType.COMPLETED,
                        name = "Shared_Jokbo_MyJokbo_Add",
                        properties = mapOf(
                            "족보" to title
                        )
                    )
                },
                enabled = buttonEnabledState,
                textStyle = HankkiTheme.typography.sub3,
            )

        } else {
            HankkiButton(
                modifier = Modifier
                    .padding(horizontal = 22.dp)
                    .fillMaxWidth()
                    .padding(bottom = 15.dp),
                text = if (isSharedJogbo) stringResource(R.string.add) else stringResource(R.string.make_jogbo),
                onClick = {
                    createNewJogbo()
                    tracker.track(
                        type = EventType.COMPLETED,
                        name = "Shared_Jokbo_MyJokbo_Add",
                        properties = mapOf(
                            "족보" to title
                        )
                    )
                },
                enabled = buttonEnabledState,
                textStyle = HankkiTheme.typography.sub3,
            )
        }
    }
}

@Composable
@Preview
fun NewJogboScreenPreview() {
    val dummyEditTagsLength: (String) -> Int = { tags ->
        tags.replace("#", "").length
    }

    HankkijogboTheme {
        NewJogboScreen(
            navigateUp = {},
            title = "",
            onTitleChange = {},
            tags = "",
            onTagsChange = {},
            editTagsLength = dummyEditTagsLength,
            buttonEnabledState = false,
            createNewJogbo = {},
            createSharedJogbo = {},
            errorDialogState = false,
            updateErrorDialogState = {},
            isSharedJogbo = false,
            favoriteId = 0L
        )
    }
}

@Composable
@Preview
fun NewShareJogboScreenPreview() {
    val dummyEditTagsLength: (String) -> Int = { tags ->
        tags.replace("#", "").length
    }

    HankkijogboTheme {
        NewJogboScreen(
            navigateUp = {},
            title = "",
            onTitleChange = {},
            tags = "",
            onTagsChange = {},
            editTagsLength = dummyEditTagsLength,
            buttonEnabledState = false,
            createNewJogbo = {},
            createSharedJogbo = {},
            errorDialogState = false,
            updateErrorDialogState = {},
            isSharedJogbo = true,
            favoriteId = 0L
        )
    }
}
