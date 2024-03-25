package com.ssafy.sungchef.features.component

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.ssafy.sungchef.R


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ImageComponent(
    modifier : Modifier,
    imageResource : Any,
    contentDescription : String = "",

){
    /**
     * 원형 이미지를 load할 때는 modifier 인자에 .clip(CircleShape)을 붙혀야한다.
     */
    GlideImage(
        model = imageResource,
        modifier = modifier
            .fillMaxHeight()
            .aspectRatio(1f),
        contentDescription = contentDescription,
        contentScale = ContentScale.FillBounds,
        failure = placeholder(R.drawable.icon_image_fail)
    )
}
