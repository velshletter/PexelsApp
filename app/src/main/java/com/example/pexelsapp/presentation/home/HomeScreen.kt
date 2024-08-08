package com.example.pexelsapp.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.example.pexelsapp.domain.model.ResponseState
import com.example.pexelsapp.presentation.EmptyStub


@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel,
    navController: NavHostController
) {
    val photoList by homeViewModel.photoResponseList.collectAsState()
    val featuredCollections by homeViewModel.featuredCollectionsList.collectAsState()
    val selectedCollection by homeViewModel.selectedCollection.collectAsState()
    val searchText by homeViewModel.searchText.collectAsState()
    val responseState by homeViewModel.responseState.collectAsState()

    val lazyCollectionsListState = rememberLazyListState()
    val lazyPhotoListState = rememberLazyStaggeredGridState()

    LaunchedEffect(featuredCollections) {
        lazyCollectionsListState.scrollToItem(0)
    }

    LaunchedEffect(photoList) {
        lazyPhotoListState.scrollToItem(0)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Spacer(modifier = Modifier.size(16.dp))
        SearchBar(
            value = searchText,
            onValueChange = { homeViewModel.onSearchTextChange(it) },
            onSearchClick = { homeViewModel.searchPhotosByTags() },
            onClearClick = { homeViewModel.clearSearchBar() }
        )
        Spacer(modifier = Modifier.size(12.dp))
        if (responseState is ResponseState.Loading) {
            LinearProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp),
            )
        } else Spacer(modifier = Modifier.size(4.dp))
        Spacer(modifier = Modifier.size(8.dp))
        LazyRow(
            state = lazyCollectionsListState
        ) {
            item { Spacer(modifier = Modifier.size(24.dp)) }
            items(featuredCollections) { collection ->
                CollectionItem(
                    collection = collection,
                    onClick = {
                        homeViewModel.onCollectionClick(collectionId = collection.id)
                    },
                    isSelected = collection.id == selectedCollection
                )
            }
            item { Spacer(modifier = Modifier.size(12.dp)) }
        }
        when {
            responseState is ResponseState.Error -> {
                NetworkStub(onClick = { homeViewModel.retryRequest() })
            }

            photoList.isEmpty() && responseState !is ResponseState.Loading -> {
                EmptyStub(
                    description = "No results found",
                    onClick = {
                        homeViewModel.clearSearchBar()
                        homeViewModel.getCuratedPhotosAndFeaturedCollections()
                    }
                )
            }

            else -> {
                Spacer(modifier = Modifier.size(8.dp))
                val density = LocalDensity.current
                var width by remember { mutableStateOf(0.dp) }
                LazyVerticalStaggeredGrid(
                    state = lazyPhotoListState,
                    columns = StaggeredGridCells.Fixed(2),
                    verticalItemSpacing = 16.dp,
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(vertical = 16.dp, horizontal = 24.dp)
                ) {
                    if (width == 0.dp) {
                        item {
                            Box(modifier = Modifier
                                .fillMaxWidth()
                                .onGloballyPositioned { layoutCoordinates ->
                                    width = with(density) { layoutCoordinates.size.width.toDp() }
                                }
                            )
                        }
                    }
                    items(items = photoList, key = { it.id }) { item ->
                        HomeGridItem(
                            item = item,
                            width = width,
                            onClick = {
                                navController.navigate("details/${false}/${item.id}") {
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
}