package com.example.pexelsapp.presentation.details

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.pexelsapp.R
import com.example.pexelsapp.domain.model.ResponseState
import com.example.pexelsapp.presentation.EmptyStub

@Composable
fun DetailsScreen(
    detailsViewModel: DetailsViewModel,
    navController: NavHostController,
    photoId: Int?,
    isFromBookmarks: Boolean?
) {
    LaunchedEffect(Unit) {
        detailsViewModel.getPhotoInfo(photoId, isFromBookmarks)
    }
    val context = LocalContext.current
    val density = LocalDensity.current

    val photoInfo by detailsViewModel.photoInfo.collectAsState()
    val getImageInfoState by detailsViewModel.loadingState.collectAsState()
    val downloadPhotoState by detailsViewModel.toastMessage.collectAsState()
    val isInBookmarks by detailsViewModel.isInBookmarks.collectAsState()

    var width by remember { mutableStateOf(0.dp) }
    var aspectRatio by remember { mutableFloatStateOf(0f) }
    var height by remember { mutableStateOf(0.dp) }

    if (downloadPhotoState != null) {
        Toast.makeText(context, downloadPhotoState, Toast.LENGTH_SHORT).show()
        detailsViewModel.resetToastMessage()
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Spacer(modifier = Modifier.size(16.dp))
        DetailsTopBar(
            title = photoInfo?.photographer ?: "",
            onClick = { navController.popBackStack() }
        )
        Spacer(modifier = Modifier.size(12.dp))
        if (getImageInfoState is ResponseState.Loading) {
            LinearProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp),
            )
        } else Spacer(modifier = Modifier.size(4.dp))
        Spacer(modifier = Modifier.size(12.dp))
        when {
            getImageInfoState is ResponseState.Error -> {
                EmptyStub(
                    description = "Image not found",
                    onClick = { navController.popBackStack() }
                )
            }
        }
        photoInfo?.let { photo ->
            val request = ImageRequest.Builder(context)
                .data(photo.source)
                .crossfade(true)
                .build()
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp)
                    .verticalScroll(rememberScrollState())
                    .onGloballyPositioned { layoutCoordinates ->
                        width = with(density) {
                            layoutCoordinates.size.width.toDp()
                        }
                        aspectRatio =
                            photo.width.toFloat() / photo.height.toFloat()
                        height = width / aspectRatio
                    }
            ) {
                AsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(height)
                        .clip(shape = RoundedCornerShape(20.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant),
                    model = request,
                    contentScale = ContentScale.FillWidth,
                    placeholder = painterResource(id = R.drawable.placeholder_foreground),
                    onSuccess = { detailsViewModel.endLoading() },
                    contentDescription = "original_photo_quality"
                )
                Spacer(modifier = Modifier.size(24.dp))
                DetailsActionBar(
                    onDownloadClick = {
                        detailsViewModel.downloadPhoto()
                    },
                    onBookmarkClick = { detailsViewModel.onBookmarkClick() },
                    isInBookmarks = isInBookmarks
                )
                Spacer(modifier = Modifier.size(8.dp))
            }
        }

    }
}