package com.example.pexelsapp.presentation.bookmarks

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.pexelsapp.R
import com.example.pexelsapp.domain.model.photo.Photo

@Composable
fun BookmarkGridItem(
    photo: Photo,
    onClick: () -> Unit,
    width: Dp,
) {
    val context = LocalContext.current
    val request = remember {
        ImageRequest.Builder(context)
            .data(Uri.parse(photo.source))
            .crossfade(true)
            .build()
    }
    val aspectRatio = photo.width.toFloat() / photo.height.toFloat()
    val height = width / aspectRatio
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .clip(shape = RoundedCornerShape(20.dp))
            .clickable {
                onClick()
            }
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surfaceVariant),
            placeholder = painterResource(id = R.drawable.placeholder_foreground),
            model = request,
            contentDescription = "",
            contentScale = ContentScale.FillWidth
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF000000).copy(0.5f))
                .height(36.dp)
                .align(Alignment.BottomStart),
            contentAlignment = Alignment.Center

        ){
            Text(
                modifier = Modifier
                    .padding(start = 20.dp, end = 20.dp, bottom = 4.dp),
                text = photo.photographer,
                maxLines = 1,
                color = Color.White,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.headlineSmall
            )
        }
    }
}