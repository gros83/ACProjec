package com.grioaldoalvarez.aplicacionbase.ui.screens.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grioaldoalvarez.aplicacionbase.data.Movie
import com.grioaldoalvarez.aplicacionbase.data.MoviesRepository
import kotlinx.coroutines.launch

// Despues de que creamos el ViewModel el objetivo es sacar todo lo que podamos al ViewModel
// para esto lo que tenemos que hacer es crear un estado que se corresponda con el estado que luego
// queremos pintar en la pantalla.
class HomeViewModel : ViewModel() {
    // Con el by nos ahorramos el value para no tener que poner state.value y asi lo podemos usar directamente
    // como si fuera una variable de tipo UIState.
    /*
    Debemos hacer privado el set del state para que el state se pueda modificar solo desde el HomeViewModel
     */
    var state by mutableStateOf(UIState())
        private set

    // Se recomienda hacer inyeccion de dependendias del repositorio aun que sea de forma manual
    // Solo se pone aqui por el nivel en el que estamos del curso
    private val repository = MoviesRepository()

    /*
    La region no la debe devolver la UI, En realidad debe haber un RegionRepository que sea capaz de devolvernos
    la region. Entonces el repositorio de fetchPopularMovies pueda utilizar el repositorio del Region para recuperar
     */
    fun onUIReady(region: String) {
        viewModelScope.launch {
            state = UIState(loading = true)
            // Con el repository el viewModel ya no es el que decide de donde se estan sacando los datos
            // por que ahora eso lo hace el repository.
            state = UIState(loading = false, movies = repository.fetchPopularMovies(region))
        }
    }
    data class UIState (
        val loading: Boolean = false,
        val movies:List<Movie> = emptyList()
    )
}