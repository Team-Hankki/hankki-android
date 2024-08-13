package com.hankki.feature.report.finish.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hankki.core.common.extension.noRippleClickable
import com.hankki.core.designsystem.R
import com.hankki.core.designsystem.theme.Gray500
import com.hankki.core.designsystem.theme.Gray850
import com.hankki.core.designsystem.theme.HankkiTheme
import com.hankki.core.designsystem.theme.HankkijogboTheme
import com.hankki.core.designsystem.theme.Red
import com.hankki.core.designsystem.theme.White

@Composable
fun ReportFinishCard(
    storeName: String,
    isAddedJogbo: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(15.dp),
        colors = CardDefaults.cardColors(
            containerColor = White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 1.dp
        )
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 22.dp, vertical = 16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = stringResource(id = com.hankki.feature.report.R.string.my_reported_store),
                    color = Gray500,
                    style = HankkiTheme.typography.caption1,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = storeName,
                    color = Gray850,
                    style = HankkiTheme.typography.body1,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }

            Spacer(modifier = Modifier.width(23.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.noRippleClickable(onClick)
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_add_red),
                    contentDescription = "add",
                    tint = Red
                )
                Text(
                    text = stringResource(
                        id =
                        if (isAddedJogbo){
                            com.hankki.feature.report.R.string.add_my_other_jogbo
                        }else {
                            com.hankki.feature.report.R.string.add_my_jogbo
                        }
                    ),
                    color = Red,
                    style = HankkiTheme.typography.body3,
                    modifier = Modifier
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ReportFinishCardPreview() {
    HankkijogboTheme {
        ReportFinishCard(storeName = "한끼네 한정식", isAddedJogbo = false, onClick = {}, modifier = Modifier.fillMaxWidth())
    }
}
