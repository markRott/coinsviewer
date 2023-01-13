package com.rt.coinsviewer.splash

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.rt.coinsviewer.R
import com.rt.coinsviewer.navigation.NavManager
import com.rt.coinsviewer.navigation.Screens
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navHostController: NavHostController? = null) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {

        val scale = remember { Animatable(0.0f) }

        LaunchedEffect(
            key1 = true,
            block = {
                scale.animateTo(
                    targetValue = 1f,
                    animationSpec = tween(
                        800,
                        easing = { OvershootInterpolator(4f).getInterpolation(it) })
                )

                delay(500)
                NavManager.navigate(
                    Screens.Home,
                    navHostController,
                    true,
                    Screens.Splash
                )
            })

        Image(
            alignment = Alignment.Center,
            painter = painterResource(id = R.drawable.ic_bitcoin),
            contentDescription = null,
            modifier = Modifier
                .width(128.dp)
                .height(128.dp)
                .scale(scale.value)
        )
    }
}