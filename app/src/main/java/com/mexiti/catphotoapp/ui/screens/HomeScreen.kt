package com.mexiti.catphotoapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.mexiti.catphotoapp.R
import com.mexiti.catphotoapp.model.CatPhoto
import com.mexiti.catphotoapp.viewmodel.CatUiState


@Composable

fun HomeScreen(
    catUiState:CatUiState,
    modifier: Modifier= Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
               ){
    when( catUiState){
        is CatUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is CatUiState.Success -> PhotoGridScreen(photos = catUiState.photos)
        is CatUiState.Error -> ErrorScreen(modifier =  modifier.fillMaxSize())

    }
}

@Composable
fun PhotoGridScreen (
    photos: List<CatPhoto>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp) //espacio entre casillas 0
){
    //se adapta el grid al tamaño de imagen
    LazyVerticalGrid(
        columns = GridCells.Adaptive(200.dp),
        modifier = modifier.padding(horizontal = 4.dp),
        contentPadding = contentPadding
    ) {
        items(
            items = photos,
            key = {photo -> photo.id}
        ){
            photo -> CatPhotoCard(photo = photo, modifier =
        modifier
            .padding(4.dp)
            .fillMaxWidth()
            .aspectRatio(1.5f)
            )

        }


    }

}

@Composable
fun CatPhotoCard(
    photo: CatPhoto,
    modifier: Modifier,

){
    AsyncImage(model = ImageRequest.Builder(context = LocalContext.current).data(photo.url)
        .crossfade(true) //es ver la foto por pedazos hasta que cargue
        .build(),
        contentDescription = stringResource(R.string.cat_image),
        modifier = modifier, //asi se hace mas ancha o mas corta la imagen
        error = painterResource(id = R.drawable.error_404),
        placeholder = painterResource(id = R.drawable.carga),
        contentScale = ContentScale.Crop //ajustar al tamaño de pantalla
    )

}



@Composable
fun LoadingScreen(modifier: Modifier = Modifier){
    Box(modifier = modifier,
        contentAlignment = Alignment.Center
    ){
        Image(painter = painterResource(id = R.drawable.loader),
            contentDescription = "Loading")
    }

}

@Composable
fun ResultScreen(photos:String, modifier: Modifier = Modifier){
    Box(modifier = modifier,
        contentAlignment = Alignment.Center
        ){
        Text(text = photos )
    }
}
@Composable
fun ErrorScreen(modifier: Modifier = Modifier){
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(painter = painterResource(id = R.drawable.error_load)
            , contentDescription = "Error Loading" )
        Text(text = stringResource(R.string.problem_with_connection))
    }
}

/*
@Preview
@Composable
fun HomeScreenPreview(){
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        HomeScreen(catUiState = CatUiState.Success("photos"))
     }

}
*/