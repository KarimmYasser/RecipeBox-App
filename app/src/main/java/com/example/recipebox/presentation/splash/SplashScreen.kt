package com.example.recipebox.presentation.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.recipebox.R
import com.example.recipebox.ui.theme.Primary

@Composable
fun SplashScreen(
    onSplashFinished: () -> Unit = {},
    viewModel: SplashViewModel = viewModel()
) {
    val isFinished = viewModel.isSplashFinished.collectAsState()
    
    LaunchedEffect(Unit) {
        kotlinx.coroutines.delay(2500)
        onSplashFinished()
    }

        Column (
            modifier = Modifier
                .fillMaxSize()
                .background(Primary),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center
        ){
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "App Logo",
                modifier = Modifier
                    .size(200.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.loading),
                contentDescription = "App Logo",
                modifier = Modifier
                    .size(100.dp)
            )
        }



}


@Preview(showBackground = true , showSystemUi = true)
@Composable
fun SplashPreview(){
    SplashScreen()
}