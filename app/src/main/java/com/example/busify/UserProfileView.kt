package com.example.busify
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text


import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun UserProfileView() {
    val userProfileImageUrl = R.drawable.busifyimg // Replace this with the user's image resource
    val userEmail = "user@example.com" // Replace with actual user email
    val userFirstName = "John" // Replace with actual user first name

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(17, 58, 105)) // Setting background color using RGB values
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // User Image and Name Row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // User Image
                Image(
                    painter = painterResource(id = userProfileImageUrl),
                    contentDescription = "User Profile Image",
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.width(16.dp))

                // First Name
                Text(
                    text = userFirstName,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White // Text color set to white
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Email
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Email: ",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White // Text color set to white
                )

                Text(
                    text = userEmail,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White // Text color set to white
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Change Password Button
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .padding(4.dp)
                    .background(color = Color(255, 253, 253), shape = CircleShape)
            ) {
                Button(
                    onClick = { /* Handle change password click */ },
                    modifier = Modifier.fillMaxSize(),
                    shape = CircleShape,
                    colors = ButtonDefaults.buttonColors(
                       // backgroundColor = Color.Transparent,
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = "Change Password",
                        fontSize = 20.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))


            // Logout Button
            Button(
                onClick = { /* Handle logout click */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .padding(4.dp),
                shape = CircleShape,

                colors = ButtonDefaults.buttonColors(
                   // backgroundColor = Color(0xFFF5F5F5),
                    contentColor = Color.White
                )
                ) {

                Text(
                    text = "Logout",
                    fontSize = 20.sp
                )
            }
        }
    }
}

