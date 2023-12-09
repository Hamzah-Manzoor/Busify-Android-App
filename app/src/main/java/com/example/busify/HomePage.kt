package com.example.busify

//import androidx.compose.material3.Icons
//import androidx.compose.ui.graphics.Color.Companion.LightBlue
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
//import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.busify.ui.theme.BusifyTheme


val LightBlue = Color(173, 216, 230)
val Blue = Color(17, 58, 105)

class HomePage : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BusifyTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    HomeScreen()
                }
            }
        }
    }
}

@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Top section with balance
        BalanceSection()

        // "Busify" section
        BusifySection()

        // Main options section
        MainOptionsSection()
    }
}

@Composable
fun BalanceSection() {
    // Placeholder for balance, replace with actual balance logic
    Text(
        text = "Balance: $100.00",
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
fun MainOptionsSection() {
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
            OptionCard(OptionItem("Buy Ticket", Icons.Default.LocalAtm))
            OptionCard(OptionItem("My Profile", Icons.Default.AccountCircle))
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp)
        ) {
            OptionCard(OptionItem("Booking History", Icons.Default.History))
            OptionCard(OptionItem("Refund", Icons.Default.Payment))
            //Any Other Card
        }
    }
}

@Composable
fun OptionCard(option: OptionItem) {
    Card(
        modifier = Modifier
            .padding(vertical = 16.dp, horizontal = 8.dp)
            .clip(MaterialTheme.shapes.medium)
            .clickable { navigateToScreen(option.title) } // Example: navigate to screen based on option title
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

fun navigateToScreen(title: String) {
    TODO("Not yet implemented")
}


data class OptionItem(val title: String, val icon: ImageVector)

@Preview(heightDp = 550, widthDp = 350)
@Composable
fun HomePreview() {
    BusifyTheme {
        HomeScreen()
    }
}