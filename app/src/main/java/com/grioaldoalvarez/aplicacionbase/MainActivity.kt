package com.grioaldoalvarez.aplicacionbase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil.compose.AsyncImage
import com.grioaldoalvarez.aplicacionbase.ui.screens.Navigation
import com.grioaldoalvarez.aplicacionbase.ui.screens.detail.DetailScreen
import com.grioaldoalvarez.aplicacionbase.ui.screens.home.HomeScreen
import com.grioaldoalvarez.aplicacionbase.ui.theme.AplicacionBaseTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Es para permitir que se pinte de un extremo a otro de la pantalla
        enableEdgeToEdge()

        setContent {
            Navigation()
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LazyVerticalGridExample5() {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Movies") },
                scrollBehavior = scrollBehavior
            )
        },
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
    ) { padding ->
        LazyVerticalGrid(
            columns = GridCells.Adaptive(120.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.padding(horizontal = 4.dp),
            contentPadding = padding
        ) {
            items(movies){ movie ->
                MovieItem4(movie = movie)
            }
        }
    }
}

@Composable
fun MovieItem4(movie: Movie) {
    Column {
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

@Composable
fun LazyVerticalGridExample4() {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(120.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier.padding(horizontal = 4.dp)
    ) {
        items(movies){ movie ->
            MovieItem3(movie = movie)
        }
    }
}

@Composable
fun MovieItem3(movie: Movie) {
    Column {
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

@Composable
fun LazyVerticalGridExample3() {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(120.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier.padding(horizontal = 4.dp)
    ) {
        items(movies){ movie ->
            MovieItem2(movie = movie)
        }
    }
}

@Composable
fun MovieItem2(movie: Movie) {
    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(2 / 3f)
                .background(Color.Gray)
        )
        Text(
            text = movie.title,
            style = MaterialTheme.typography.bodySmall,
            maxLines = 1,
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Composable
fun LazyVerticalGridExample2() {
    LazyVerticalGrid(columns = GridCells.Adaptive(120.dp)) {
        items(movies){ movie ->
            MovieItem2(movie = movie)
        }
    }
}

@Composable
fun MovieItem1(movie: Movie) {
    Box(
        modifier = Modifier
            .padding(4.dp)
            .size(120.dp)
            .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
    ){
        Text(
            text = movie.title,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun LazyVerticalGridExample() {
    // Los Lazy list son el equivalente a los REcicleViews de Android.
    LazyVerticalGrid(columns = GridCells.Fixed(2)) {
        items(100){ index ->
            Text("Item $index", Modifier.padding(16.dp))
        }
    }
}

@Composable
fun LazyVerticalGridExample1() {
    // Los Lazy list son el equivalente a los REcicleViews de Android.
    // Cuando tiene Adaptive y rotas la pantalla se muestran la cantidad de elementos
    // que caben en pantalla.
    // Así con esto es mas facil adaptar la UI a distintos tamaños de pantalla
    LazyVerticalGrid(columns = GridCells.Adaptive(120.dp)) {
        items(100){ index ->
            Text("Item $index", Modifier.padding(16.dp))
        }
    }
}

@Composable
fun LazyColumnExample() {
    // Los Lazy list son el equivalente a los REcicleViews de Android.
    // El Lazy Row funciona igual
    LazyColumn {
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .background(Color.LightGray)
            )
        }
        items(100){ index ->
            Text("Item $index", Modifier.padding(16.dp))
        }
        // Se puede ir metiendo mas items o item individuales para agregar mas cosas
    }
}