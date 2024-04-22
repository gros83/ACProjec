package com.grioaldoalvarez.aplicacionbase.ui.screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.grioaldoalvarez.aplicacionbase.data.movies
import com.grioaldoalvarez.aplicacionbase.ui.screens.detail.DetailScreen
import com.grioaldoalvarez.aplicacionbase.ui.screens.home.HomeScreen

@Composable
fun Navigation() {
    // Esto es para agregar la navegacion entre pantallas
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(onClick = {movie ->
                navController.navigate("detail/${movie.id}")
            })
        }
        composable(
            "detail/{movieId}",
            arguments = listOf(navArgument("movieId") { type = NavType.IntType})
        ){ backStackEntry ->
            val movieId = backStackEntry.arguments?.getInt("movieId")
            DetailScreen(
                movie = movies.first { it.id == movieId },
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}