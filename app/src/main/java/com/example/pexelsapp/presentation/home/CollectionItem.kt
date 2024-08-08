package com.example.pexelsapp.presentation.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.pexelsapp.domain.model.collection.Collection

@Composable
fun CollectionItem(
    collection: Collection,
    onClick: () -> Unit,
    isSelected: Boolean
) {
    SuggestionChip(
        modifier = Modifier.padding(end = 12.dp),
        onClick = onClick,
        label = {
            Text(
                modifier = Modifier.padding(vertical = 8.dp, horizontal = 4.dp),
                text = collection.title,
                style = if (isSelected)
                    MaterialTheme.typography.headlineMedium
                else MaterialTheme.typography.headlineSmall,

                )
        },
        colors = SuggestionChipDefaults.suggestionChipColors(
            containerColor = if (isSelected)
                MaterialTheme.colorScheme.primary
            else MaterialTheme.colorScheme.surfaceVariant,
            labelColor = if (isSelected)
                MaterialTheme.colorScheme.onPrimary
            else MaterialTheme.colorScheme.onSurface
        ),
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(width = 0.dp, color = Color.Transparent)
    )
}
