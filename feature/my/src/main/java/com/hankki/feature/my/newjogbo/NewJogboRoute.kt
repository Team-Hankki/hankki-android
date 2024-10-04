package com.hankki.feature.my.newjogbo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.hankki.core.common.extension.addFocusCleaner
import com.hankki.core.common.extension.noRippleClickable
import com.hankki.core.designsystem.component.button.HankkiMediumButton
import com.hankki.core.designsystem.component.dialog.SingleButtonDialog
import com.hankki.core.designsystem.component.textfield.HankkiCountTextField
import com.hankki.core.designsystem.component.topappbar.HankkiTopBar
import com.hankki.core.designsystem.theme.Gray900
import com.hankki.core.designsystem.theme.HankkiTheme
import com.hankki.core.designsystem.theme.HankkijogboTheme
import com.hankki.core.designsystem.theme.White
import com.hankki.feature.my.R

@Composable
fun NewJogboRoute(
    navigateUp: () -> Unit,
    newJogboViewModel: NewJogboViewModel = hiltViewModel()
) {
    val state by newJogboViewModel.newJogboState.collectAsStateWithLifecycle()
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(newJogboViewModel.newJogboSideEffect, lifecycleOwner) {
        newJogboViewModel.newJogboSideEffect.flowWithLifecycle(lifecycleOwner.lifecycle)
            .collect { sideEffect ->
                when (sideEffect) {
                    is NewJogboSideEffect.NavigateToNewJogbo -> navigateUp()
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
        errorDialogState = state.errorDialogState,
        updateErrorDialogState = { newJogboViewModel.updateErrorDialog(state.errorDialogState) }
    )
}

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
    errorDialogState: Boolean,
    updateErrorDialogState: () -> Unit,
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current

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
        HankkiTopBar(leadingIcon = {
            Icon(
                imageVector = ImageVector.vectorResource(id = com.hankki.core.designsystem.R.drawable.ic_arrow_left),
                contentDescription = "Back",
                modifier = Modifier
                    .padding(start = 6.dp)
                    .size(44.dp)
                    .noRippleClickable(onClick = navigateUp),
                tint = Color.Unspecified
            )
        })

        Column(
            modifier = Modifier.padding(horizontal = 22.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(46.dp))

            Text(
                text = stringResource(R.string.new_store_jogbo),
                style = HankkiTheme.typography.h1,
                color = Gray900
            )

            Spacer(modifier = Modifier.height(58.dp))

            HankkiCountTextField(
                title = stringResource(R.string.name_jogbo),
                value = title,
                valueLength = title.length,
                placeholder = stringResource(R.string.name_example),
                onValueChanged = onTitleChange,
                trailingIcon = true,
                resetTitle = errorDialogState
            )

            Spacer(modifier = Modifier.height(33.dp))

            HankkiCountTextField(
                title = stringResource(R.string.think_jogbo),
                value = tags,
                valueLength = editTagsLength(tags),
                placeholder = stringResource(R.string.tag_example),
                onValueChanged = onTagsChange,
                trailingIcon = false,
                resetTitle = errorDialogState
            )

            Spacer(modifier = Modifier.height(38.dp))

            HankkiMediumButton(
                text = stringResource(R.string.make_jogbo),
                onClick = createNewJogbo,
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
            errorDialogState = false,
            updateErrorDialogState = {}
        )
    }
}
