package space.korolev.myapplication.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource

import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import space.korolev.myapplication.R


@Composable
fun HelloScreen(navController: NavController) {
    Column() {
        Surface(color = Color.Yellow) {
            Text(
                text = "Hello, this application will introduce you to the cartoon characters Rick and Marty. Click on the picture to continue.",
                modifier = Modifier.padding(16.dp)
            )
        }
        Image(painterResource(id = R.drawable.first_image),
            contentDescription = "description of the image",
            modifier = Modifier
                .fillMaxSize()
                .clickable(onClick = {
                    navController.navigate("listCharacters")
                }
                ))
    }
}
