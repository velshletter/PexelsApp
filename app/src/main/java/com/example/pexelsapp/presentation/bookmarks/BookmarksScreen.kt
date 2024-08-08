package com.example.pexelsapp.presentation.bookmarks

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.pexelsapp.domain.model.ResponseState
import com.example.pexelsapp.presentation.EmptyStub

//@Preview(showSystemUi = true)
@Composable
fun BookmarksScreen(
    bookmarksViewModel: BookmarksViewModel,
    navController: NavController
) {
    LaunchedEffect(Unit) {
        bookmarksViewModel.loadBookmarks()
    }
    val photoList by bookmarksViewModel.photoList.collectAsState()
    val responseState by bookmarksViewModel.responseState.collectAsState()
    val density = LocalDensity.current
    var columnWidth by remember { mutableStateOf(0.dp) }
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.size(16.dp))
        Row(
            modifier = Modifier
                .height(56.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Bookmarks",
                style = MaterialTheme.typography.headlineLarge
            )
        }
        if (responseState is ResponseState.Loading) {
            LinearProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp),
            )
        } else Spacer(modifier = Modifier.size(4.dp))
        if (photoList.isEmpty() && responseState !is ResponseState.Loading) {
            EmptyStub(
                description = "You haven't saved anything yet",
                onClick = { navController.popBackStack() }
            )
        } else {
            Spacer(modifier = Modifier.size(12.dp))
            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Fixed(2),
                verticalItemSpacing = 16.dp,
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(vertical = 16.dp, horizontal = 24.dp)
            ) {
                if (columnWidth == 0.dp) {
                    item {
                        Box(modifier = Modifier
                            .fillMaxWidth()
                            .onGloballyPositioned { layoutCoordinates ->
                                columnWidth = with(density) { layoutCoordinates.size.width.toDp() }
                            }
                        )
                    }
                }
                items(items = photoList, key = { it.id }) { item ->
                    BookmarkGridItem(
                        photo = item,
                        width = columnWidth,
                        onClick = {
                            navController.navigate("details/${true}/${item.id}") {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    }
}