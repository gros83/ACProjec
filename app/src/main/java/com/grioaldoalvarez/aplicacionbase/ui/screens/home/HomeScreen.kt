package com.grioaldoalvarez.aplicacionbase.ui.screens.home

import android.Manifest
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.grioaldoalvarez.aplicacionbase.data.Movie
import com.grioaldoalvarez.aplicacionbase.R
import com.grioaldoalvarez.aplicacionbase.ui.common.PermissionRequestEffect
import com.grioaldoalvarez.aplicacionbase.ui.common.getRegion
import com.grioaldoalvarez.aplicacionbase.ui.theme.AplicacionBaseTheme
import kotlinx.coroutines.launch

@Composable
fun Screen(content: @Composable () -> Unit) {
    AplicacionBaseTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background,
            content = content
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onClick: (Movie) -> Unit,
    /* viewModel() es una funcion remember con una funcion Composable, que lo que va a hacer es irse
        a buscar al viewModelStoreOwner (Las activitis tienen un viewModelstoreOwner que es el que almacena
        todos esos viewModels) entonces cuando se produce iun cambio de configuracion o un cambio de rotación
        y se vuelve a llamar a la funcion viewModel() va a ir a buscarlo al viewModelStoreOwner, si ya existe
        lo devuelve y si no existe se crea uno nuevo.
        Se para como argumento porque si en los test se le quiere pasar un viewModelDistinto o si queremos pasarle
        uno distinto en una preview, tenemos control sobre que ViewModel le podemos dar si queremos pasarle
        un mock
     */
    vm: HomeViewModel = viewModel()
) {
    // El contexto se toma aqui porque se debe de ejecutar dentro del contexto de Composable
    val ctx = LocalContext.current
    var coroutineScope = rememberCoroutineScope()
    val state = vm.state

    PermissionRequestEffect(permission = Manifest.permission.ACCESS_COARSE_LOCATION) { granted ->
        coroutineScope.launch {
            val region = if (granted) ctx.getRegion() else "US"
            vm.onUIReady(region)
        }
    }
    Screen {
        val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = stringResource(id = R.string.app_name)) },
                    scrollBehavior = scrollBehavior
                )
            },
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            // contentWindowInsets = WindowInsets.safeDrawing Esto se agrega para que cuando unos paddings
            // de la toolbar y otras cosas mas se le sume correctamente al padding que se le pasa al hijo del Scaffold
            // para que el contenido hijo se muestre a correctamente a partir del padding que se le pasa, incluyendo
            // los insets de la barra de naveegacion (Es para que al momento que se llegue al ultimo de los elementos
            // se muestre correctamente el elemento final y no lo tape la barra)
            contentWindowInsets = WindowInsets.safeDrawing
        ) { padding ->

            if (state.loading) {
                /*
                Se muestra en un box para que lo centre, fill maxwidth para que ocupe todo el ancho
                y se la pasa el padding que nos pasa el Scaffold para usar esa padding.
                 */
                Box(modifier = Modifier.fillMaxSize().padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            LazyVerticalGrid(
                columns = GridCells.Adaptive(120.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.padding(horizontal = 4.dp),
                contentPadding = padding
            ) {
                items(state.movies, key = { it.id }) { it ->
                    MovieItem(movie = it) { onClick(it) }
                }
            }
        }
    }
}

@Composable
fun MovieItem(movie: Movie, onClick: () -> Unit) {
    Column(
        modifier = Modifier.clickable( onClick = onClick )
    ) {
        AsyncImage(
            model = movie.poster,
            contentDescription = movie.title,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(2 / 3f)
                .clip(MaterialTheme.shapes.small)
        )
        Text(
            text = movie.title,
            style = MaterialTheme.typography.bodySmall,
            maxLines = 1,
            modifier = Modifier.padding(8.dp)
        )
    }
}