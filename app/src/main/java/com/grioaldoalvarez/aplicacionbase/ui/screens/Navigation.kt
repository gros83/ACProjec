package com.grioaldoalvarez.aplicacionbase.ui.screens

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.grioaldoalvarez.aplicacionbase.ui.screens.detail.DetailScreen
import com.grioaldoalvarez.aplicacionbase.ui.screens.detail.DetailViewModel
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
            val movieId = requireNotNull(backStackEntry.arguments?.getInt("movieId"))
            DetailScreen(
                // TEnemos que crear el DetailViewModel dentro de la funcion viewModel
                // porque si no cada vez que se reconstruyera ese composable se crearia
                // ese DetailViewModel
                // Una cosa buena que tiene esta funcion con respecto a xml es que no necesitamos crear
                // una factory para crear viewModels con argumentos la factory la genera la misma funcion
                // viewModel dentro de su propio codigo
                // Tambien existe un a sobre carga de viewModel que permite pasarle una factory como
                // argumento si la quieres seguir usando
                viewModel{ DetailViewModel(movieId) },
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}