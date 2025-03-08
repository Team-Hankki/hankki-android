package com.hankki.feature.my.myjogbodetail.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hankki.core.common.extension.noRippleClickable
import com.hankki.core.designsystem.component.topappbar.HankkiTopBar
import com.hankki.core.designsystem.theme.Gray900
import com.hankki.core.designsystem.theme.HankkiTheme
import com.hankki.core.designsystem.theme.HankkijogboTheme
import com.hankki.core.designsystem.theme.Red500
import com.hankki.feature.my.R
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun JogboHeader(
    isSharedJogbo: Boolean,
    isJogboOwner: Boolean,
    jogboTitle: String,
    jogboChips: PersistentList<String>,
    userName: String,
    navigateToMyJogbo: (Boolean) -> Unit,
    onClickShareButton: () -> Unit
) {
    HankkiTopBar(
        modifier = Modifier
            .background(Red500)
            .statusBarsPadding(),
        leadingIcon = {
            Image(
                imageVector = ImageVector.vectorResource(R.drawable.icon_back),
                contentDescription = "Back",
                modifier = Modifier
                    .padding(start = 7.dp)
                    .size(40.dp)
                    .noRippleClickable(onClick = { navigateToMyJogbo(false) })
            )
        },
        content = {
            Text(
                text = if (isSharedJogbo && !isJogboOwner) stringResource(R.string.shared_jogbo)
                else stringResource(R.string.my_jogbo),
                style = HankkiTheme.typography.sub3,
                color = Gray900
            )
        }
    )

    JogboFolder(
        title = jogboTitle,
        chips = jogboChips,
        userName = userName,
        onClickShareButton = onClickShareButton,
        isJogboOwner = isJogboOwner
    )
}

@Preview
@Composable
fun JogboHeaderPreview() {
    HankkijogboTheme {
        JogboHeader(
            isSharedJogbo = true,
            isJogboOwner = true,
            jogboTitle = "title",
            jogboChips = persistentListOf(),
            userName = "nickname",
            navigateToMyJogbo = {},
            onClickShareButton = {}
        )
    }
}
