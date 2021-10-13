package space.korolev.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import space.korolev.myapplication.screens.HelloScreen
import space.korolev.myapplication.screens.ListCharactersScreen
import space.korolev.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MyApplicationTheme {
                ComposeNavigation()
            }
        }
    }
}

@Composable
fun ComposeNavigation() {

    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "hello"
    ) {
        composable("hello") {
            HelloScreen(navController = navController)
        }
        composable("listCharacters") {
            ListCharactersScreen(navController = navController)
        }
    }
}



