package com.example.busify

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.ui.graphics.*;
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.*

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.LocalTextInputService
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.busify.ui.theme.BusifyTheme

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
        style = MaterialTheme.typography.bodySmall
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
            style = MaterialTheme.typography.titleSmall,
            color = Color.White
        )
    }
}

@Composable
fun MainOptionsSection() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Blue)
            .padding(16.dp)
    ) {
        val options = listOf(
            //OptionItem("Buy Ticket", Icons.Default.Lo),
            OptionItem("My Profile", Icons.Default.AccountCircle),
            //OptionItem("Booking History", Icons.Default.History),
            //OptionItem("Refund", Icons.Default.Payment)


        )

        items(options.chunked(2)) { rowItems ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                rowItems.forEach { option ->
                    OptionCard(option)
                }
            }
        }
    }
}

@Composable
fun OptionCard(option: OptionItem) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .clip(MaterialTheme.shapes.medium)
            .clickable { /* Handle click here */ }
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
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = option.title, style = MaterialTheme.typography.titleSmall)
        }
    }
}

data class OptionItem(val title: String, val icon: ImageVector)

@Preview(heightDp = 550, widthDp = 350)
@Composable
fun HomePreview() {
    BusifyTheme {
        HomeScreen()
    }
}
