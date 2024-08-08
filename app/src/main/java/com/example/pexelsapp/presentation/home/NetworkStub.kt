package com.example.pexelsapp.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.pexelsapp.R

@Composable
fun NetworkStub(
    onClick:() -> Unit
){
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Icon(
            modifier = Modifier
                .size(120.dp)
                .offset(x = 4.dp),
            painter = painterResource(id = R.drawable.no_network_icon),
            contentDescription = "search_icon",
            tint = MaterialTheme.colorScheme.onSurface
        )
        TextButton(
            onClick = onClick,
        ){
            Text(text = "Try Again", style = MaterialTheme.typography.headlineLarge)

        }
    }
}