package com.example.pexelsapp.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.pexelsapp.R
import com.example.pexelsapp.domain.model.photo.PhotoResponse

@Composable
fun HomeGridItem(
    item: PhotoResponse,
    onClick: () -> Unit,
    width: Dp,
) {
    val context = LocalContext.current
    val request = remember {
        ImageRequest.Builder(context)
            .data(item.src.medium)
            .crossfade(true)
            .build()
    }
    val aspectRatio = item.width.toFloat() / item.height.toFloat()
    val height = width / aspectRatio
    AsyncImage(
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .clip(shape = RoundedCornerShape(20.dp))
            .clickable {
                onClick()
            }
            .background(MaterialTheme.colorScheme.surfaceVariant),
        placeholder = painterResource(id = R.drawable.placeholder_foreground),
        model = request,
        contentDescription = "",
        contentScale = ContentScale.FillWidth
    )
}

