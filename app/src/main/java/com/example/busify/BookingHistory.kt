package com.example.busify

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.busify.ui.theme.BusifyTheme
import com.google.firebase.firestore.FirebaseFirestore

class BookingHistory : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BusifyTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val username = intent.getStringExtra("username") ?: ""
                    BookingHistoryScreen(username)
                }
            }
        }
    }
}

class BookingData(
    val busName: String,
    val bookedSeatNumbers: List<Int>,
    val totalCost: Int,
    val sourceCity: String,
    val destinationCity: String,
    val departureTime: String,
    val arrivalTime: String
)

@Composable
fun BookingHistoryScreen(username: String) {
    var bookingData by remember { mutableStateOf(emptyList<BookingData>()) }

    val db = FirebaseFirestore.getInstance()
    LaunchedEffect(key1 = Unit) {
        // Fetch booking history from the database when the screen is launched
        getBookingHistoryFromDatabase(
            db,
            username,
            onSuccess = { fetchedBookingData ->
                bookingData = fetchedBookingData
            },
            onFailure = { e ->
                // Handle failure
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(10, 40, 80))
            .padding(0.dp)
            .verticalScroll(rememberScrollState())
    ) {
        // Page Header
        HeaderSection()

        // Display BookingCard for each fetched booking data
        LazyColumn {
            items(bookingData) { booking ->
                BookingCard(booking = booking)
            }
        }
    }
}

fun getBookingHistoryFromDatabase(
    db: FirebaseFirestore,
    username: String,
    onSuccess: (List<BookingData>) -> Unit,
    onFailure: (Exception) -> Unit
) {
    val bookingData = mutableListOf<BookingData>()
    val bookingHistoryCollection = db.collection("BookingHistory")

    bookingHistoryCollection
        .whereEqualTo("username", username)
        .get()
        .addOnSuccessListener { documents ->
            for (document in documents) {
                val booking = BookingData(
                    busName = document.getString("busName") ?: "",
                    bookedSeatNumbers = document.get("bookedSeatNumbers") as? List<Int> ?: emptyList(),
                    totalCost = (document.getDouble("totalCost") ?: 0.0).toInt(),
                    sourceCity = document.getString("sourceCity") ?: "",
                    destinationCity = document.getString("destinationCity") ?: "",
                    departureTime = document.getString("departureTime") ?: "",
                    arrivalTime = document.getString("arrivalTime") ?: ""
                )
                bookingData.add(booking)
            }
            onSuccess(bookingData)
        }
        .addOnFailureListener { e ->
            onFailure(e)
        }
}

@Composable
fun BookingCard(booking: BookingData) {
    Card(
        modifier = Modifier
            .padding(vertical = 10.dp, horizontal = 13.dp)
            .clip(MaterialTheme.shapes.medium)
            .fillMaxWidth()
            .height(150.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(60, 90, 120))
                .padding(16.dp),
        ) {
            Text(
                text = "Bus: ${booking.busName}",
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold
            )

            Text(
                text = "Seats: ${booking.bookedSeatNumbers.joinToString()}",
                style = MaterialTheme.typography.bodyMedium
            )

            Text(
                text = "Total Cost: $${booking.totalCost}",
                style = MaterialTheme.typography.bodyMedium
            )

            Text(
                text = "${booking.sourceCity} To ${booking.destinationCity}",
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 18.sp
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "${booking.departureTime}", style = MaterialTheme.typography.bodyMedium)
                Text(text = "${booking.arrivalTime}", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}

//@Composable
//fun HeaderSection() {
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .background(Color(10, 40, 80))
//            .padding(1.dp),
//        horizontalArrangement = Arrangement.SpaceBetween,
//        verticalAlignment = Alignment.CenterVertically,
//    ) {
//        BackButton()
//    }
//}

//@Composable
//fun BackButton() {
//    IconButton(
//        onClick = { /* Handle back button click */ },
//    ) {
//        Icon(
//            imageVector = Icons.Default.ArrowBack,
//            contentDescription = "Back",
//            tint = Color.LightGray
//        )
//    }
//}

@Preview(heightDp = 550, widthDp = 350)
@Composable
fun BookingHistoryPreview() {
    BusifyTheme {
        BookingHistoryScreen("testUser")
    }
}
