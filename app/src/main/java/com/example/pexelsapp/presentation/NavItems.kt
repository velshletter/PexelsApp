package com.example.pexelsapp.presentation

import com.example.pexelsapp.R

sealed class NavItems(
    val route: String = "",
    val focusedIconId: Int = 0,
    val unfocusedIconId: Int = 0,
) {
    data object Home : NavItems(
        route = "home",
        focusedIconId = R.drawable.active_home_button_icon,
        unfocusedIconId = R.drawable.inactive_home_button_icon,
    )
    data object Bookmarks : NavItems(
        route = "bookmarks",
        focusedIconId = R.drawable.active_bookmarks_button_icon,
        unfocusedIconId = R.drawable.inactive_bookmarks_button_icon,
    )
    data object Details : NavItems(
        route = "details/{isFromBookmarks}/{photoId}"
    )
}