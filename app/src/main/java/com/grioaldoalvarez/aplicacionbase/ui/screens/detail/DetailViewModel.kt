package com.grioaldoalvarez.aplicacionbase.ui.screens.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grioaldoalvarez.aplicacionbase.data.Movie
import com.grioaldoalvarez.aplicacionbase.data.MoviesRepository
import kotlinx.coroutines.launch

class DetailViewModel(private val id:Int): ViewModel() {

    private val repository = MoviesRepository()

    var state by mutableStateOf(UIState())
        private set

    init {
        viewModelScope.launch {
            state = UIState(loading = true)
            state = UIState(loading = false, movie = repository.findMovieById(id))
        }
    }

    data class UIState(
        val loading:Boolean = false,
        val movie:Movie? = null
    )
}