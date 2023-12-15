package com.example.busify

//import androidx.compose.ui.platform.LocalDensityOwner
import android.os.Bundle
import android.util.Log
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.runtime.LaunchedEffect
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
import com.google.firebase.firestore.FirebaseFirestore


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
                    val username = intent.getStringExtra("username") ?: ""
                    BuyTicketScreen(username)
                }
            }
        }
    }
}

data class Trip(
    val busId: Int,
    val busName: String,
    val sourceCity: String,
    val destinationCity: String,
    val departureTime: String,
    val arrivalTime: String,
    val ticketCost: Int,
    val bookedSeats: Map<String, String>  // Change this line
)



@Composable
fun BuyTicketScreen(username : String) {

    var trips by remember { mutableStateOf(emptyList<TripWithId>()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Blue)
            .padding(0.dp)
            .verticalScroll(rememberScrollState())
    ) {
        val db = FirebaseFirestore.getInstance()
        LaunchedEffect(key1 = Unit) {
            // Fetch trips from the database when the screen is launched
            getTripsFromDatabase(
                db,
                onSuccess = { fetchedTrips ->
                    trips = fetchedTrips
                },
                onFailure = { e ->
                    // Handle failure
                }
            )
        }

        // Page Header - Balance Section
        HeaderSection()

        // Display TripCard for each fetched trip
        trips.forEach { tripWithId ->
            TripCard(username, trip = tripWithId.trip, onSeatSelected = { updatedSeats ->
                // Update the booked seats for the selected trip
                // You can add your own logic here

                // After booking is successful, refetch the trips from the database
                getTripsFromDatabase(
                    db,
                    onSuccess = { fetchedTrips ->
                        trips = fetchedTrips
                    },
                    onFailure = { e ->
                        // Handle failure
                    }
                )
            })
        }

        // Add more TripCard components as needed
    }
}



fun getTripsFromDatabase(
    db: FirebaseFirestore,
    onSuccess: (List<TripWithId>) -> Unit,
    onFailure: (Exception) -> Unit
) {
    val trips = mutableListOf<TripWithId>()
    val tripsCollection = db.collection("Trips")

    tripsCollection
        .get()
        .addOnSuccessListener { documents ->
            for (document in documents) {
                val trip = Trip(
                    busId = document.getLong("id")?.toInt() ?: 0,
                    busName = document.getString("busName") ?: "",
                    sourceCity = document.getString("sourceCity") ?: "",
                    destinationCity = document.getString("destinationCity") ?: "",
                    departureTime = document.getString("departureTime") ?: "",
                    arrivalTime = document.getString("arrivalTime") ?: "",
                    ticketCost = (document.getDouble("ticketCost") ?: 0.0).toInt(),
                    bookedSeats = document.get("bookedSeats") as? Map<String, String> ?: emptyMap()  // Change this line
                )
                val tripWithId = TripWithId(trip)
                trips.add(tripWithId)
            }
            onSuccess(trips)
        }
        .addOnFailureListener { e ->
            onFailure(e)
        }
}


data class TripWithId(val trip: Trip)




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
        //BalanceSection(username)


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
    username: String,
    trip: Trip,
    onSeatSelected: (List<Boolean>) -> Unit
) {
    var isPopupVisible by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .padding(vertical = 10.dp, horizontal = 13.dp)
            .clip(MaterialTheme.shapes.medium)
            .clickable {
                isPopupVisible = true
            }
            .fillMaxWidth()
            .height(110.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(60, 90, 120))
                .padding(16.dp),
        ) {
            // Departure and arrival times at the bottom
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Bus: ${trip.busName} (${trip.busId})", style = MaterialTheme.typography.labelSmall)
                Text(
                    text = "Cost: $${trip.ticketCost}",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.ExtraBold
                )
            }

            Text(
                text = "${trip.sourceCity} To ${trip.destinationCity}",
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 20.sp
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "${trip.departureTime}", style = MaterialTheme.typography.bodyMedium)
                Text(text = "${trip.arrivalTime}", style = MaterialTheme.typography.bodyMedium)
            }

            // Display booked seats as "X" or "O"
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                for (index in 0 until trip.bookedSeats.size) {
                    val seatStatus = trip.bookedSeats[index.toString()] ?: ""
                    Text(
                        text = if (seatStatus.isNotBlank()) "X" else "O",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.ExtraBold,
                        color = if (seatStatus.isNotBlank()) Color.Red else Color.Green
                    )
                    if (index < trip.bookedSeats.size - 1) {
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                }
            }
        }
    }

    // Display the SeatSelectionPopup when isPopupVisible is true
    if (isPopupVisible) {
        SeatSelectionPopup(
            ticketCost = trip.ticketCost,
            userName = username,
            busId = trip.busId,
            sourceCity = trip.sourceCity, // Replace with actual source city
            destinationCity = trip.destinationCity, // Replace with actual destination city
            departureTime = trip.departureTime, // Replace with actual departure time
            arrivalTime = trip.arrivalTime,
            busName = trip.busName,
            bookedSeats = trip.bookedSeats.map { (_, value) -> value.isNotBlank() },
            onSeatSelected = {
                onSeatSelected(it.map { selected -> selected })
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
    ticketCost: Int,
    userName: String,
    busId: Int,
    sourceCity: String, // Replace with actual source city
    destinationCity: String, // Replace with actual destination city
    departureTime: String, // Replace with actual departure time
    arrivalTime: String,
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
    totalPrice = (selectedSeats.countIndexed { index, selected -> selected && userBookedSeats[index] })*ticketCost

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
                        saveBookingToDatabase(
                            busId,
                            userName,
                            busName,
                            selectedSeats,
                            totalPrice,
                            sourceCity,
                            destinationCity,
                            departureTime,
                            arrivalTime,
                            onSuccess = {
                                onSeatSelected(selectedSeats)
                                onDismiss()
                            }
                        )

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

// Function to save booking data to the database
fun saveBookingToDatabase(
    busId: Int,
    username: String,
    busName: String,
    bookedSeats: List<Boolean>,
    totalCost: Int,
    sourceCity: String,
    destinationCity: String,
    departureTime: String,
    arrivalTime: String,
    onSuccess: () -> Unit
) {
    val db = FirebaseFirestore.getInstance()
    val bookingHistoryCollection = db.collection("BookingHistory")
    val tripsCollection = db.collection("Trips")

    // Save booking data to BookingHistory collection
    val bookingData = hashMapOf(
        "busId" to busId,
        "username" to username,
        "busName" to busName,
        "bookedSeatNumbers" to bookedSeats.mapIndexedNotNull { index, selected -> if (selected) index + 1 else null },
        "totalCost" to totalCost,
        "sourceCity" to sourceCity,
        "destinationCity" to destinationCity,
        "departureTime" to departureTime,
        "arrivalTime" to arrivalTime
    )

    // Save booking data and get the automatically generated ID
    val bookingRef = bookingHistoryCollection.document() // Use the provided ID
    val bookingId = bookingRef.id

    // Save the booking data to BookingHistory collection
    bookingRef.set(bookingData)
        .addOnSuccessListener {
            Log.d("Booking", "Booking data saved to BookingHistory successfully")
        }
        .addOnFailureListener { e ->
            Log.w("Booking", "Error saving booking data to BookingHistory", e)
        }

    // Get the document from Trips collection
    val query = tripsCollection.whereEqualTo("id", busId)

    query.get()
        .addOnSuccessListener { tripDocSnapshots ->
            if (tripDocSnapshots.documents.isNotEmpty()) {
                // Assuming "id" is the field that contains busId
                val tripDoc = tripDocSnapshots.documents[0]

                // Log information about the retrieved document
                Log.d("Booking", "Retrieved document: $tripDoc")
                Log.d("Booking", "Source City: ${tripDoc["sourceCity"]}")

                // Update bookedSeats map in the document
                val updatedBookedSeats = tripDoc["bookedSeats"] as? MutableMap<String, String> ?: mutableMapOf()
                bookedSeats.forEachIndexed { index, selected ->
                    if (selected) {
                        // Replace the existing string at the selected index with the fixed string "testUser"
                        updatedBookedSeats[(index).toString()] = username // Use seat numbers as keys
                    }
                }

                // Log information about the updated bookedSeats
                Log.d("Booking", "Updated bookedSeats: $updatedBookedSeats")

                // Perform the transaction to update the document
                db.runTransaction { transaction ->
                    // Update the document with the new bookedSeats map
                    transaction.update(tripDoc.reference, "bookedSeats", updatedBookedSeats)

                    // Update sourceCity in the document
                    //transaction.update(tripDoc.reference, "sourceCity", "Lahore")

                    // Return a dummy result as we only need the transaction for updates
                    true
                }
                    .addOnSuccessListener {
                        // Handle success
                        Log.d("Booking", "Booking data saved successfully")
                        onSuccess() // Invoke the success callback
                    }
                    .addOnFailureListener { e ->
                        // Handle failure
                        Log.w("Booking", "Error updating document", e)
                        // You may want to handle this failure differently based on your requirements
                    }
            } else {
                Log.e("Booking", "No document found with id: $busId")
            }
        }
        .addOnFailureListener { e ->
            // Handle failure to get the document
            Log.e("Booking", "Error getting document with id: $busId", e)
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
        BuyTicketScreen("")
    }
}
