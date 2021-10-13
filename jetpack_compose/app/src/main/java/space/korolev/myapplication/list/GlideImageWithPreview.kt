package space.korolev.myapplication.list


import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil.compose.rememberImagePainter

import space.korolev.myapplication.R


@Composable
fun GlideImageWithPreview(
    data: Any?,
    modifier: Modifier? = null,
    contentDescription: String? = null,
    contentScale: ContentScale = ContentScale.Fit
) {
    if (data == null)
        Image(
            painter = painterResource(id = R.drawable.first_image),
            contentDescription = contentDescription,
            modifier = modifier ?: Modifier,
            alignment = Alignment.Center,
            contentScale = contentScale
        )
    else
        Image(painter= rememberImagePainter(
            data = data),
            contentDescription = contentDescription,
            modifier = modifier ?: Modifier,
            contentScale = contentScale
        )
}