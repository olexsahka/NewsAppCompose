package com.loc.newsapp.presentation.navgraph

sealed class Route(val route: String) {

    object OnBoardingScreen : Route(route = "onBoardingScreen")
    object HomeScreen : Route(route = "HomeScreen")
    object SearchScreen : Route(route = "SearchScreen")
    object DetailsScreen : Route(route = "DetailsScreen")
    object AppStartNavigation : Route(route = "AppStartNavigation")
    object NewsNavigation : Route(route = "NewsNavigation")
    object NewsNavigationScreen : Route(route = "NewsNavigationScreen")
    object BookmarkScreen : Route(route = "BookmarkScreen")
}