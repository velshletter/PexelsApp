package com.example.pexelsapp.presentation.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.pexelsapp.R
import com.example.pexelsapp.ui.theme.ThemeColors

@Composable
fun SearchBar(
    value: String = "",
    onValueChange: (String) -> Unit,
    onSearchClick: () -> Unit,
    onClearClick: () -> Unit

) {

    val keyboardController = LocalSoftwareKeyboardController.current
    OutlinedTextField(
        modifier = Modifier
            .height(56.dp)
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        value = value,
        onValueChange = onValueChange,
        placeholder = {
            Text(text = "Search", style = MaterialTheme.typography.headlineSmall)
        },
        textStyle = MaterialTheme.typography.headlineSmall,
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
        ),
        leadingIcon = {
            Icon(
                modifier = Modifier
                    .size(20.dp)
                    .offset(x = 4.dp),
                painter = painterResource(id = R.drawable.search_icon),
                contentDescription = "search_icon",
                tint = MaterialTheme.colorScheme.primary
            )
        },
        trailingIcon = {
            if (value.isNotEmpty()) {
                Icon(
                    modifier = Modifier
                        .size(18.dp)
                        .offset(x = (-4).dp)
                        .clickable {
                            onClearClick()
                        },
                    painter = painterResource(id = R.drawable.cross_icon),
                    contentDescription = "clear_icon",
                    tint = ThemeColors.Day.unfocusedIcon
                )
            }
        },
        shape = RoundedCornerShape(64.dp),
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                onSearchClick()
                keyboardController?.hide()
            }
        )
    )
}

