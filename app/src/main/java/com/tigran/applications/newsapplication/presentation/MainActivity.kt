package com.tigran.applications.newsapplication.presentation


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.tigran.applications.newsapplication.presentation.newssources.NewsSourcesScreen
import com.tigran.applications.newsapplication.presentation.sourcepage.SOURCE_ID_KEY
import com.tigran.applications.newsapplication.presentation.sourcepage.SourceArticlesScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsApp()
        }
    }
}

@Composable
fun NewsApp() {
    val navController = rememberNavController()
    NewsNavHost(navController = navController)
}

@Composable
fun NewsNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = NEWS_SOURCES_SCREEN
    ) {
        composable(NEWS_SOURCES_SCREEN) {
            NewsSourcesScreen(
                onSourceClicked = { sourceId ->
                    navController.navigate(
                        "$SOURCE_ARTICLES_SCREEN/$sourceId"
                    )
                }
            )
        }
        composable(
            route = "$SOURCE_ARTICLES_SCREEN/{$SOURCE_ID_KEY}",
            arguments = listOf(
                navArgument(SOURCE_ID_KEY) { type = NavType.StringType }
            )
        ) {
            SourceArticlesScreen()
        }
    }
}