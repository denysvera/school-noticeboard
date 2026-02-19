package com.nativkod.schoolnoticeboard.app.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.nativkod.schoolnoticeboard.presentation.ui.NoticeDetailScreen
import com.nativkod.schoolnoticeboard.presentation.ui.NoticeFeedScreen

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(navController, startDestination = Routes.FEED) {

        composable(Routes.FEED) {
            NoticeFeedScreen(
                onNoticeClick = { id -> navController.navigate(Routes.detailRoute(id)) }
            )
        }

        composable(Routes.detailPattern) {
            NoticeDetailScreen(
                vm = hiltViewModel(),
                onBack = { navController.popBackStack() }
            )
        }
    }
}