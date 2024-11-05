package com.example.langio

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.langio.ui.theme.JacquesFrancoisShadow
import com.example.langio.ui.theme.LANGIOTheme

// -------------------------
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LANGIOTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = MaterialTheme.colorScheme.secondary
                ) { innerPadding ->
                    HomeScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}


// -----------------------------------
//class MainActivity : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContent {
//            LANGIOTheme {
//                val navController = rememberNavController()
//
//                NavHost(
//                    navController = navController,
//                    startDestination = "home" // Ekran początkowy
//                ) {
//                    composable("home") {
//                        HomeScreen(onNavigateToDetails = {
//                            navController.navigate("details")
//                        })
//                    }
//                    composable("details") {
//                        DetailsScreen(onNavigateBack = {
//                            navController.popBackStack() // Cofnij do poprzedniego ekranu
//                        })
//                    }
//                }
//            }
//        }
//    }
//}
// -----------------------------------

// --------------------------
//@Composable
//fun HomeScreen(onNavigateToDetails: () -> Unit) {
//    Column(
//        modifier = Modifier.fillMaxSize(),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
//    ) {
//        Text(text = "Home Screen", fontSize = 24.sp)
//        Button(onClick = { onNavigateToDetails() }) {
//            Text("Go to Details")
//        }
//    }
//}
//
//@Composable
//fun DetailsScreen(onNavigateBack: () -> Unit) {
//    Column(
//        modifier = Modifier.fillMaxSize(),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
//    ) {
//        Text(text = "Details Screen", fontSize = 24.sp)
//        Button(onClick = { onNavigateBack() }) {
//            Text("Go Back")
//        }
//    }
//}
// --------------------------


@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF403E3E)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "LANGIO",
            fontSize = 48.sp,
            color = Color(0xFF8559A5),
            fontWeight = FontWeight.Bold,
            fontFamily = JacquesFrancoisShadow,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        Image(
            painter = painterResource(id = R.drawable.cow_image), // Dodaj obrazek krowy do drawable
            contentDescription = "Cow Icon",
            modifier = Modifier.padding(vertical=38.dp).size(150.dp).padding(bottom = 32.dp)
        )

        Button(
            onClick = { /*  */ },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8559A5)),
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier
                .padding(vertical = 8.dp)
                .width(200.dp)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "I have an account", color = Color(0xFFFFE342))
                Text(text = "tengo una cuenta", color = Color(0xFFFFE342), fontSize = 12.sp)
            }
        }

        Button(
            onClick = { /*  */ },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE858AE)),
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier
                .padding(vertical = 38.dp)
                .width(200.dp)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "Get started", color = Color(0xFF430512))
                Text(text = "Empezar", color = Color(0xFF430512), fontSize = 12.sp)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    LANGIOTheme {
        HomeScreen()
    }
}
