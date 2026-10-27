package com.mastermartini.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.mastermartini.R

@Composable
fun LogoImage(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(R.drawable.logo),
        contentDescription = "Logo de Master Martini",
        contentScale = ContentScale.Fit,
        modifier = modifier
            .fillMaxWidth(0.85f)
            .aspectRatio(180f / 110f)
    )
}
