package com.nativkod.schoolnoticeboard.presentation.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.nativkod.schoolnoticeboard.R
import com.nativkod.schoolnoticeboard.domain.model.Notice

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoticeDetailContent(
    notice: Notice,
    padding: PaddingValues
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .padding(padding)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {

        Surface(
            shape = MaterialTheme.shapes.medium,
            tonalElevation = 1.dp,
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data(notice.imageUrl)
                    .crossfade(true)
                    .diskCachePolicy(CachePolicy.ENABLED)
                    .memoryCachePolicy(CachePolicy.ENABLED)
                    .build(),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                placeholder = painterResource(R.drawable.ic_image_placeholder),
                error = painterResource(R.drawable.ic_broken_image)
            )
        }

        Spacer(Modifier.height(16.dp))

        Text(
            text = notice.title,
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(Modifier.height(8.dp))

        Text(
            text = notice.publishedAtFriendlyLocal,
            style = MaterialTheme.typography.labelMedium
        )

        Spacer(Modifier.height(16.dp))

        Text(
            text = notice.body,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}