package com.nativkod.schoolnoticeboard.presentation.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.nativkod.schoolnoticeboard.R
import com.nativkod.schoolnoticeboard.domain.model.Notice

@Composable
fun NoticeRow(
    notice: Notice,
    onClick: () -> Unit
) {
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .testTag("notice_row_${notice.id}")
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            if (notice.imageUrl.isNullOrBlank()) {
                Surface(
                    modifier = Modifier.size(72.dp),
                    tonalElevation = 2.dp,
                    shape = MaterialTheme.shapes.medium
                ) {}
            } else {
                Surface(
                    shape = MaterialTheme.shapes.medium,
                    tonalElevation = 1.dp,
                    modifier = Modifier.size(72.dp)
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(context)
                            .data(notice.imageUrl)
                            .crossfade(true)
                            .size(144)
                            .diskCachePolicy(CachePolicy.ENABLED)
                            .memoryCachePolicy(CachePolicy.ENABLED)
                            .build(),
                        contentDescription = null,
                        modifier = Modifier.size(72.dp),
                        placeholder = painterResource(R.drawable.ic_image_placeholder),
                        error = painterResource(R.drawable.ic_broken_image)
                    )
                }
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = notice.title,
                    modifier = Modifier.testTag("notice_title_${notice.id}"),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(Modifier.height(4.dp))

                Text(
                    text = notice.body,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(Modifier.height(6.dp))

                Text(
                    text = notice.publishedAtFriendlyLocal,
                    modifier = Modifier.testTag("notice_date_${notice.id}"),
                    style = MaterialTheme.typography.labelSmall
                )
            }
        }
    }
}
