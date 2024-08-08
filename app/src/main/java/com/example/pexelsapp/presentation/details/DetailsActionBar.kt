package com.example.pexelsapp.presentation.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.pexelsapp.R

@Composable
fun DetailsActionBar(
    onDownloadClick: () -> Unit,
    onBookmarkClick: () -> Unit,
    isInBookmarks: Boolean
) {
    Row(
        modifier = Modifier
            .height(64.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(100))
                .background(MaterialTheme.colorScheme.surfaceVariant),
            verticalAlignment = Alignment.CenterVertically
        ) {
            FilledIconButton(
                modifier = Modifier.size(56.dp),
                shape = RoundedCornerShape(100),
                colors = IconButtonDefaults.filledIconButtonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                onClick = onDownloadClick
            ) {
                Icon(
                    modifier = Modifier
                        .size(20.dp),
                    painter = painterResource(id = R.drawable.download_icon),
                    contentDescription = "back_icon",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
            Text(
                modifier = Modifier
                    .padding(start = 12.dp, end = 24.dp),
                style = MaterialTheme.typography.headlineMedium,
                text = "Download"
            )
        }
        FilledIconButton(
            modifier = Modifier.size(56.dp),
            shape = RoundedCornerShape(100),
            colors = IconButtonDefaults.filledIconButtonColors(
                containerColor = if (isInBookmarks) MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.surfaceVariant
            ),
            onClick = {
                onBookmarkClick()
            }
        ) {
            Icon(
                modifier = Modifier
                    .size(24.dp),
                painter = if (isInBookmarks) painterResource(id = R.drawable.active_bookmarks_button_icon)
                else painterResource(id = R.drawable.inactive_bookmarks_button_icon),
                contentDescription = "back_icon",
                tint = if (isInBookmarks) MaterialTheme.colorScheme.onPrimary
                else MaterialTheme.colorScheme.onSurface
            )
        }
    }
}