package com.example.busify

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class Ticket(val title: String, val description: String, val src: String, val dest: String, val price: String, val date: String)

@Composable
fun TicketHistoryPage(ticketList: List<Ticket>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(17, 58, 105))
            .padding(16.dp)
    ) {
        // Back button
        Button(
            onClick = { /* Handle back button click */ },
            modifier = Modifier
                .align(Alignment.Start)
                .padding(8.dp),
            elevation = null,
            shape = RoundedCornerShape(percent = 50),

            colors = ButtonDefaults.buttonColors(
                //backgroundColor = Color.Transparent,
                contentColor = Color.White
            )
        ) {

            Icon(Icons.Outlined.ArrowBack, contentDescription = "Back", tint = Color.White)
        }

        // Title: History
        Text(
            text = "History",
            fontSize = 48.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            textAlign = TextAlign.Center
        )

        // Ticket History Rows
        ticketList.forEach { ticket ->
            TicketRow(ticket = ticket)
        }
    }
}

@Composable
fun TicketRow(ticket: Ticket) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White, shape = RoundedCornerShape(8.dp))
            .padding(16.dp)
            .clickable { /* Handle ticket click */ }
            .padding(bottom = 16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            // Emoji (Bus)
            Text(
                text = "\uD83D\uDE8C",
                fontSize = 24.sp,
                modifier = Modifier.padding(end = 8.dp)
            )

            // Ticket Title and Description
            Column {
                Text(
                    text = ticket.title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = ticket.description,
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // From and Dest
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(

                text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = Color.Black, // Change color here
                            fontWeight = FontWeight.Bold // Set text to bold
                        )
                    ) {
                        append("From: ")
                    }
                    append("${ticket.src}")
                },
                fontSize = 14.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = Color.Black, // Change color here
                            fontWeight = FontWeight.Bold // Set text to bold
                        )
                    ) {
                        append("To: ")
                    }
                    append("${ticket.dest}")
                },
                fontSize = 14.sp
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Ticket Price and Date
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = ticket.price,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = ticket.date,
                fontSize = 12.sp,
                color = Color.Gray
            )
        }
    }
    Spacer(modifier = Modifier.height(24.dp))

}
