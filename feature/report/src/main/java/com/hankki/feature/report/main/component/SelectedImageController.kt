package com.hankki.feature.report.main.component

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter.State.Empty.painter
import com.hankki.core.common.extension.bounceClick
import com.hankki.core.common.extension.noRippleClickable
import com.hankki.core.designsystem.theme.Gray100
import com.hankki.core.designsystem.theme.Gray700
import com.hankki.core.designsystem.theme.HankkiTheme
import com.hankki.core.designsystem.theme.HankkijogboTheme
import com.hankki.core.designsystem.theme.White

@Composable
fun SelectedImageController(
    imageUri: Uri,
    deleteImage: () -> Unit = {},
    changeImage: () -> Unit = {},
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box {
            AsyncImage(
                model = imageUri,
                contentDescription = "selected image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(84.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .padding(top = 12.dp, end = 12.dp)
                    .align(Alignment.BottomStart)
            )
            Icon(
                painter = painterResource(id = com.hankki.core.designsystem.R.drawable.ic_circle_dark_x),
                contentDescription = "delete",
                tint = Color.Unspecified,
                modifier = Modifier
                    .size(30.dp)
                    .align(Alignment.TopEnd)
                    .noRippleClickable(deleteImage)
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .bounceClick(onClick = changeImage)
                .clip(RoundedCornerShape(16.dp))
                .background(Gray100)
                .padding(vertical = 8.dp, horizontal = 10.dp)
        ) {
            Text(
                text = "변경하기",
                style = HankkiTheme.typography.body3,
                color = Gray700
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SelectedImageControllerPreview() {
    HankkijogboTheme {
        SelectedImageController("https://picsum.photos/200/300".toUri())
    }
}
