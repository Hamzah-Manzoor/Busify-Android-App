package com.example.busify

//import androidx.compose.ui.graphics.Color.Companion.Blue

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.LocalAtm
import androidx.compose.material.icons.filled.Payment
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.busify.ui.theme.BusifyTheme


val LightBlue = Color(170, 210, 245)
val Blue = Color(17, 58, 105)

class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BusifyTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val username = intent.getStringExtra("username") ?: ""
                    val email = intent.getStringExtra("email") ?: ""
                    HomeScreen(username, email)
                }
            }
        }
    }
}

@Composable
fun HomeScreen(username : String, email: String) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Top section with balance
        WelcomeSection(username)

        // "Busify" section
        BusifySection()

        // Main options section
        MainOptionsSection(username, email)
    }
}



@Composable
fun WelcomeSection(username: String) {
    // Placeholder for balance, replace with actual balance logic
    Text(
        text = "Welcome, $username",
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        style = MaterialTheme.typography.bodyMedium, // Adjust the typography style for the desired font size
        color = Color.White,
        textAlign = TextAlign.Center
    )
}

@Composable
fun BusifySection() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .background(Blue)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Busify",
            style = MaterialTheme.typography.headlineLarge, // Adjust the typography style for the desired font size and style
            color = Color.White,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 40.sp,
            fontFamily = FontFamily.Serif,
        )
    }
}


@Composable
fun MainOptionsSection(username: String, email: String) {
    val context = LocalContext.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(LightBlue)
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp)
        ) {
            OptionCard(OptionItem("Buy Ticket", Icons.Default.LocalAtm),context, username, email)
            OptionCard(OptionItem("My Profile", Icons.Default.AccountCircle),context, username , email)
        }
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp)
        ) {
            OptionCard(OptionItem("Booking History", Icons.Default.History),context, username, email)
            OptionCard(OptionItem("Refund", Icons.Default.Payment),context, username, email)
            //Any Other Card
        }
    }
}

@Composable
fun OptionCard(option: OptionItem,context: Context, username: String, email: String) {
    Card(
        modifier = Modifier
            .padding(vertical = 16.dp, horizontal = 8.dp)
            .clip(MaterialTheme.shapes.medium)
            .clickable {
                navigateToScreen(
                    option.title,
                    context,
                    username ,
                    email
                )
            } // Example: navigate to screen based on option title
            .fillMaxWidth()
            .height(120.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = option.icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(48.dp) // Adjust the icon size as needed
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = option.title,
                style = MaterialTheme.typography.bodySmall, // Adjust the typography style for the text
                textAlign = TextAlign.Center // Center align the text
            )
        }
    }
}

fun navigateToBuyTicket(context: Context, username: String) {
    val intent = Intent(context, BuyTicket::class.java)
    intent.putExtra("username", username)
    context.startActivity(intent)
}

fun navigateToUserProfile(context: Context, username: String, email: String){
    val intent = Intent(context, UserProfile::class.java)
    intent.putExtra("username", username)
    intent.putExtra("email", email)
    context.startActivity(intent)
}

//fun navigateToHistory(context: Context, username: String, email: String){
//}
fun navigateToScreen(title: String, context: Context, username: String, email: String) {
    if(title == "Buy Ticket"){
        navigateToBuyTicket(context, username)
    }
    if(title == "My Profile"){
        navigateToUserProfile(context, username, email)
    }
    if(title == "Booking History"){
    }
}


data class OptionItem(val title: String, val icon: ImageVector)

//@Preview(heightDp = 550, widthDp = 350)
//@Composable
//fun HomePreview() {
//    BusifyTheme {
//        HomeScreen(username = "Juni")
//    }
//}