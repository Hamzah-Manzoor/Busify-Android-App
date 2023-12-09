package com.example.busify

//import androidx.compose.ui.platform.LocalDensityOwner
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
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.busify.ui.theme.BusifyTheme


val DarkBlue = Color(10, 40, 80)

class BuyTicket : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BusifyTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    BuyTicketScreen()
                }
            }
        }
    }
}

@Composable
fun BuyTicketScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Blue)
            .padding(0.dp)
    ) {
        // Page Header - Balance Section
        HeaderSection()

        TripCard(
            busName = "Safari Bus",
            sourceCity = "Faisalabad",
            destinationCity = "Gujranwala",
            departureTime = "09:30 AM",
            arrivalTime = "04:00 PM",
            ticketCost = "$45",
            bookedSeats = listOf(true, false, true, false, false, true),
            onSeatSelected = { updatedSeats ->
                // Update the booked seats for "Desert Voyager"
                // You can add your own logic here
            }
        )

        TripCard(
            busName = "Mountain Explorer",
            sourceCity = "Sialkot",
            destinationCity = "Sargodha",
            departureTime = "11:00 AM",
            arrivalTime = "05:30 PM",
            ticketCost = "$50",
            bookedSeats = listOf(true, false, true, false, false, true),
            onSeatSelected = { updatedSeats ->
                // Update the booked seats for "Desert Voyager"
                // You can add your own logic here
            }
        )

        TripCard(
            busName = "Desert Voyager",
            sourceCity = "Hyderabad",
            destinationCity = "Sukkur",
            departureTime = "12:45 PM",
            arrivalTime = "06:15 PM",
            ticketCost = "$55",
            bookedSeats = listOf(true, false, true, false, false, true),
            onSeatSelected = { updatedSeats ->
                // Update the booked seats for "Desert Voyager"
                // You can add your own logic here
            }
        )

        TripCard(
            busName = "Royal Express",
            sourceCity = "Bahawalpur",
            destinationCity = "Dera Ghazi Khan",
            departureTime = "02:15 PM",
            arrivalTime = "07:45 PM",
            ticketCost = "$60",
            bookedSeats = listOf(true, false, true, false, false, true),
            onSeatSelected = { updatedSeats ->
                // Update the booked seats for "Desert Voyager"
                // You can add your own logic here
            }
        )

        TripCard(
            busName = "Majestic Travels",
            sourceCity = "Mirpur Khas",
            destinationCity = "Jhelum",
            departureTime = "03:45 PM",
            arrivalTime = "09:15 PM",
            ticketCost = "$65",
            bookedSeats = listOf(true, false, true, false, false, true),
            onSeatSelected = { updatedSeats ->
                // Update the booked seats for "Desert Voyager"
                // You can add your own logic here
            }
        )

        TripCard(
            busName = "Sunrise Transport",
            sourceCity = "Nowshera",
            destinationCity = "Muzaffarabad",
            departureTime = "05:30 PM",
            arrivalTime = "11:00 PM",
            ticketCost = "$70",
            bookedSeats = listOf(true, false, true, false, false, true),
            onSeatSelected = { updatedSeats ->
                // Update the booked seats for "Desert Voyager"
                // You can add your own logic here
            }
        )


        // Add more TripCard components as needed
    }
}

@Composable
fun HeaderSection() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(DarkBlue)
            .padding(1.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {

        // Back Button
        BackButton()

        // Balance Section
        BalanceSection()


    }
}

@Composable
fun BackButton() {
    IconButton(
        onClick = { /* Handle back button click */ },
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "Back",
            tint = Color.LightGray // Set the color to light gray
        )
    }
}

@Composable
fun TripCard(
    busName: String,
    sourceCity: String,
    destinationCity: String,
    departureTime: String,
    arrivalTime: String,
    ticketCost: String,
    bookedSeats: List<Boolean> = List(6) { false }, // Default to an empty list of 6 seats
    onSeatSelected: (List<Boolean>) -> Unit
) {
    var isPopupVisible by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .padding(vertical = 10.dp, horizontal = 13.dp)
            .clip(MaterialTheme.shapes.medium)
            .clickable {
                //onSeatSelected(bookedSeats.map { !it }) // Invert the booked seats for demonstration purposes
                isPopupVisible = true
            }
            .fillMaxWidth()
            .height(110.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(LightBlue)
                .padding(16.dp),
        ) {
            // Departure and arrival times at the bottom
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Bus: $busName", style = MaterialTheme.typography.labelSmall)
                Text(
                    text = "Cost: $ticketCost",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.ExtraBold
                )
            }

            Text(text = "$sourceCity To $destinationCity", style = MaterialTheme.typography.bodyMedium, fontSize = 20.sp)

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "$departureTime", style = MaterialTheme.typography.bodyMedium)
                Text(text = "$arrivalTime", style = MaterialTheme.typography.bodyMedium)
            }

            // Display booked seats as "X" or "O"
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                bookedSeats.forEachIndexed { index, isBooked ->
                    Text(
                        text = if (isBooked) "X" else "O",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.ExtraBold,
                        color = if (isBooked) Color.Red else Color.Green
                    )
                    if (index < bookedSeats.size - 1) {
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                }
            }
        }
    }

    // Display the SeatSelectionPopup when isPopupVisible is true
    if (isPopupVisible) {
        SeatSelectionPopup(
            busName = busName,
            bookedSeats = bookedSeats,
            onSeatSelected = {
                onSeatSelected(it)
                isPopupVisible = false
            },
            onDismiss = {
                isPopupVisible = false
            }
        )
    }
}


@Composable
fun SeatSelectionPopup(
    busName: String,
    bookedSeats: List<Boolean>,
    onSeatSelected: (List<Boolean>) -> Unit,
    onDismiss: () -> Unit
) {
    var selectedSeats by remember { mutableStateOf(bookedSeats) }
    var buyEnabled by remember { mutableStateOf(false) }
    var totalPrice by remember { mutableStateOf(0) }

    // Create a list to keep track of whether each seat was originally booked by the user
    val userBookedSeats = remember { bookedSeats.map { !it } }

    // Calculate total price based on selected seats by the current user
    totalPrice = selectedSeats.countIndexed { index, selected -> selected && userBookedSeats[index] }

    // Create a dialog with custom dimensions
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
            usePlatformDefaultWidth = false
        )
    ) {
        // Dialog Content
        Column(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth()
                .background(Color.White)
        ) {
            // Top Section - Bus Name and Total Price
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp, horizontal = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Bus: $busName", style = MaterialTheme.typography.bodyLarge)
                Text(text = "Total: $$totalPrice", style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.ExtraBold)
            }

            // Seat Selection Section
            val seatsInRow = 2
            val numberOfRows = (selectedSeats.size + seatsInRow - 1) / seatsInRow

            for (rowIndex in 0 until numberOfRows) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp, horizontal = 6.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    for (seatIndex in 0 until seatsInRow) {
                        val index = rowIndex * seatsInRow + seatIndex
                        if (index < selectedSeats.size) {
                            Box(
                                modifier = Modifier
                                    .height(90.dp)
                                    .width(160.dp)
                                    .padding(horizontal = 8.dp)
                                    .background(if (selectedSeats[index]) Color.Green else Color.LightGray)
                                    .clickable {
                                        // Only allow selecting seats that were originally booked by the user
                                        if (!bookedSeats[index]) {
                                            selectedSeats = selectedSeats.toMutableList().apply {
                                                set(index, !get(index))
                                            }
                                            buyEnabled = selectedSeats.any { it }
                                            totalPrice = selectedSeats.countIndexed { i, selected -> selected && userBookedSeats[i] }
                                        }
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = (index + 1).toString(),
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 40.sp,
                                    color = if (selectedSeats[index]) Color.White else Color.Black
                                )
                            }
                        }
                    }
                }
            }

            // Bottom Section - Buy and Cancel Buttons
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp, horizontal = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = {
                        // Handle buy button click
                        onSeatSelected(selectedSeats)
                        onDismiss()
                    },
                    enabled = totalPrice > 0 // Check if the total cost is greater than zero
                ) {
                    Text(text = "Buy", color = Color.White)
                }

                Button(
                    onClick = {
                        // Handle cancel button click
                        onDismiss()
                    }
                ) {
                    Text(text = "Cancel", color = Color.White)
                }
            }

        }
    }
}

// Extension function to count elements based on their index and a predicate
private inline fun <T> Iterable<T>.countIndexed(predicate: (index: Int, T) -> Boolean): Int {
    var count = 0
    for ((index, element) in this.withIndex()) {
        if (predicate(index, element)) {
            count++
        }
    }
    return count
}











@Preview(heightDp = 550, widthDp = 350)
@Composable
fun BuyTicketPreview() {
    BusifyTheme {
        BuyTicketScreen()
    }
}
