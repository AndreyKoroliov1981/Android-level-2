package space.korolev.myapplication.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun ListCharactersScreen(navController: NavController){
    Scaffold {
        Text("List Characters Screen", modifier=Modifier.padding(24.dp))
    }
}