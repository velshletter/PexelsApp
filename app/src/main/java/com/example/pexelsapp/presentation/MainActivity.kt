package com.example.pexelsapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.pexelsapp.presentation.bookmarks.BookmarksScreen
import com.example.pexelsapp.presentation.bookmarks.BookmarksViewModel
import com.example.pexelsapp.presentation.details.DetailsScreen
import com.example.pexelsapp.presentation.details.DetailsViewModel
import com.example.pexelsapp.presentation.home.HomeScreen
import com.example.pexelsapp.presentation.home.HomeViewModel
import com.example.pexelsapp.ui.theme.PexelsAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()

        val homeViewModel: HomeViewModel by viewModels()
        val detailsViewModel: DetailsViewModel by viewModels()
        val bookmarksViewModel: BookmarksViewModel by viewModels()
        homeViewModel.getCuratedPhotosAndFeaturedCollections()

        setContent {
            PexelsAppTheme {
                val navController = rememberNavController()
                val currentBackStackEntry by navController.currentBackStackEntryAsState()
                Scaffold(
                    bottomBar = {
                        if (currentBackStackEntry?.destination?.route != NavItems.Details.route) {
                            BottomNavigationBar(
                                navController = navController
                            )
                        }
                    }) { paddingValues ->
                    NavHost(
                        modifier = Modifier.padding(paddingValues = paddingValues),
                        navController = navController,
                        startDestination = NavItems.Home.route
                    ) {
                        composable(NavItems.Home.route) {
                            HomeScreen(
                                homeViewModel = homeViewModel,
                                navController = navController
                            )
                        }
                        composable(NavItems.Details.route) { backStackEntry ->
                            val photoId = backStackEntry.arguments?.getString("photoId")?.toInt()
                            val isFromBookmarks =
                                backStackEntry.arguments?.getString("isFromBookmarks")?.toBoolean()
                            DetailsScreen(
                                detailsViewModel = detailsViewModel,
                                navController = navController,
                                photoId = photoId,
                                isFromBookmarks = isFromBookmarks
                            )
                        }
                        composable(NavItems.Bookmarks.route) {
                            BookmarksScreen(
                                bookmarksViewModel = bookmarksViewModel,
                                navController = navController
                            )
                        }
                    }
                }
            }
        }
    }
}