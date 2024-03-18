package com.ssafy.sungchef.features.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.request.RequestOptions

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ImageComponent(
    modifier : Modifier,
    imageResource : Any,
    isCircle : Boolean,
    contentDescription : String = ""
){
    if (isCircle) {
        GlideImage(
            model = imageResource,
            modifier = modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .clip(CircleShape),
            contentDescription = contentDescription,
            contentScale = ContentScale.FillBounds
        )
    } else {
        GlideImage(
            model = imageResource,
            modifier = modifier
                .fillMaxWidth()
                .aspectRatio(1f),
            contentDescription = contentDescription,
            contentScale = ContentScale.FillBounds
        )
    }
}
