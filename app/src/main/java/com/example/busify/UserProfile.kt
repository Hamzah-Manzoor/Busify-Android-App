package com.example.busify

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.busify.ui.theme.BusifyTheme
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

class UserProfile : ComponentActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        setContent {
            BusifyTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val username = intent.getStringExtra("username") ?: ""
                    val email = intent.getStringExtra("email") ?: ""
                    UserProfileView(username,email)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserProfileView(username : String, email: String) {
    val context = LocalContext.current
    val db = FirebaseFirestore.getInstance()

    val userProfileImageUrl = R.drawable.busifyimg
    val currentPasswordState = remember { mutableStateOf("") }
    val newPasswordState = remember { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }

    val icon = if (passwordVisibility)
        painterResource(id = R.drawable.view)
    else
        painterResource(id = R.drawable.eye)

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
                    text = "$username",
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
                    text = "$email",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White // Text color set to white
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Change Password Button
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                OutlinedTextField(
                    value = currentPasswordState.value,
                    onValueChange = { currentPasswordState.value = it },
                    label = { Text("Current Password") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = "Password Icon",
                            tint = Color.White
                        )
                    },
                    trailingIcon = {
                        IconButton(onClick = {
                            passwordVisibility = !passwordVisibility
                        }) {
                            Icon(
                                painter = icon,
                                contentDescription = "Show Password/ hide password"
                            )
                        }
                    },
                    visualTransformation = if (passwordVisibility) VisualTransformation.None
                    else PasswordVisualTransformation(),

                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.White, // Set the outline color when focused
                        unfocusedBorderColor = Color.White.copy(alpha = 0.5f), // Set the outline color when not focused
                    ),
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                )

                OutlinedTextField(
                    value = newPasswordState.value,
                    onValueChange = { newPasswordState.value = it },
                    label = { Text("New Password") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = "Password Icon",
                            tint = Color.White
                        )
                    },
                    trailingIcon = {
                        IconButton(onClick = {
                            passwordVisibility = !passwordVisibility
                        }) {
                            Icon(
                                painter = icon,
                                contentDescription = "Show Password/ hide password"
                            )
                        }
                    },
                    visualTransformation = if (passwordVisibility) VisualTransformation.None
                    else PasswordVisualTransformation(),

                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.White, // Set the outline color when focused
                        unfocusedBorderColor = Color.White.copy(alpha = 0.5f), // Set the outline color when not focused
                    ),
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                )
//                Button(
//                    onClick = { /* Handle change password click */ },
//                    modifier = Modifier.fillMaxSize(),
//                    shape = CircleShape,
//                    colors = ButtonDefaults.buttonColors(
//                        // backgroundColor = Color.Transparent,
//                        contentColor = Color.White
//                    )
//                ) {
//                    Text(
//                        text = "Change Password",
//                        fontSize = 20.sp
//                    )
//                }
                Button(
                    onClick = { handleChangePassword(currentPasswordState.value, newPasswordState.value, context, username, db)},
                    modifier = Modifier.width(120.dp),
                    colors = ButtonDefaults.buttonColors(Color(6, 214, 160))
                ) {

                    Text(text = "Update")
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

        }
    }
}

@Composable
fun LogoutButton(onLogoutConfirmed: () -> Unit, context: Context) {
    val coroutineScope = rememberCoroutineScope()
    val dialogState = remember { mutableStateOf(false) }

    Button(
        onClick = {
            coroutineScope.launch {
                // Show the AlertDialog
                dialogState.value = true
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .padding(4.dp),
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(Color(214, 40, 40)))
     {
        Text(text = "Logout", fontSize = 20.sp)
    }

    if (dialogState.value) {
        // Show the AlertDialog
        AlertDialog(
            onDismissRequest = {
                // Handle dismiss (e.g., if user clicks outside the dialog)
                dialogState.value = false
            },
            title = {
                Text(text = "Confirm Logout", color = MaterialTheme.colorScheme.primary)
            },
            text = {
                Text(text = "Are you sure you want to logout?")
            },
            confirmButton = {
                Button(
                    onClick = {
                        // Dismiss the dialog
                        dialogState.value = false
                        // Perform logout action and navigate to login screen
                        onLogoutConfirmed()
                        val intent = Intent(context, LoginActivity::class.java)
                        context.startActivity(intent)
                    }
                ) {
                    Text(text = "Yes")
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        // Dismiss the dialog
                        dialogState.value = false
                    }
                ) {
                    Text(text = "No")
                }
            },
        )
    }
}


//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun ChangePasswordButton(onPasswordChanged: () -> Unit, context: Context, username: String, db: FirebaseFirestore) {
//    val coroutineScope = rememberCoroutineScope()
//    val dialogState = remember { mutableStateOf(false) }
//    val currentPasswordState = remember { mutableStateOf("") }
//    val newPasswordState = remember { mutableStateOf("") }
//
//    Button(
//        onClick = {
//            coroutineScope.launch {
//                // Show the custom-sized Dialog
//                dialogState.value = true
//            }
//        },
//        modifier = Modifier
//            .fillMaxWidth()
//            .height(50.dp)
//            .padding(4.dp),
//        shape = CircleShape,
//        colors = ButtonDefaults.buttonColors(contentColor = Color.White)
//    ) {
//        Text(text = "Change Password", fontSize = 20.sp)
//    }
//
//    if (dialogState.value) {
//        // Custom-sized Dialog
//        Box(
//            modifier = Modifier
//                .fillMaxSize()
//                .background(Color.White)
//                .padding(16.dp)
//                .size(300.dp, 200.dp), // Adjust the size as needed
//            contentAlignment = Alignment.Center
//        ) {
//            // Content of the custom Dialog
//            Column(
//                modifier = Modifier.fillMaxSize()
//            ) {
//                // Title
//                Text(
//                    text = "Change Password",
//                    fontWeight = FontWeight.Bold,
//                    fontSize = 20.sp,
//                    color = Color.Black
//                )
//
//                Spacer(modifier = Modifier.height(16.dp))
//
//                // Current Password TextField
//                OutlinedTextField(
//                    value = currentPasswordState.value,
//                    onValueChange = { currentPasswordState.value = it },
//                    label = { Text("Current Password") },
//                    visualTransformation = PasswordVisualTransformation(),
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(30.dp) // Adjust the height as needed
//                        .padding(bottom = 8.dp)
//                )
//
//                // Add some spacing between the two text fields
//                Spacer(modifier = Modifier.height(8.dp))
//
//                // New Password TextField
//                OutlinedTextField(
//                    value = newPasswordState.value,
//                    onValueChange = { newPasswordState.value = it },
//                    label = { Text("New Password") },
//                    visualTransformation = PasswordVisualTransformation(),
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(30.dp) // Adjust the height as needed
//                )
//
//                Spacer(modifier = Modifier.height(16.dp))
//
//                // Confirm and Cancel buttons
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(top = 8.dp),
//                    horizontalArrangement = Arrangement.End
//                ) {
//                    Button(
//                        onClick = {
//                            // Dismiss the custom Dialog
//                            dialogState.value = false
//                            // Perform change password action
//                            handleChangePassword(currentPasswordState.value, newPasswordState.value, context, username, db)
//                            // Callback to notify that the password has been changed
//                            onPasswordChanged()
//                        }
//                    ) {
//                        Text(text = "Change Password")
//                    }
//
//                    Spacer(modifier = Modifier.width(8.dp))
//
//                    Button(
//                        onClick = {
//                            // Dismiss the custom Dialog
//                            dialogState.value = false
//                        }
//                    ) {
//                        Text(text = "Cancel")
//                    }
//                }
//            }
//        }
//    }
//}



fun handleChangePassword(currentPassword: String, newPassword: String, context: Context, username: String, db : FirebaseFirestore) {

    val usersCollection = db.collection("User")

    usersCollection
        .whereEqualTo("username", username)
        .get()
        .addOnSuccessListener { documents ->
            if (!documents.isEmpty) {
                // User found in Firestore
                // Check if the provided current password matches the stored password
                val userDocument = documents.documents[0]
                val storedPassword = userDocument.getString("password")

                if (currentPassword == storedPassword) {
                    // Current password is valid, update the password in Firestore
                    userDocument.reference
                        .update("password", newPassword)
                        .addOnSuccessListener {
                            showToast(context, "Password updated successfully!")
                        }
                        .addOnFailureListener { e ->
                            showToast(context, "Error updating password: $e")
                        }
                } else {
                    showToast(context, "Incorrect current password")
                }
            } else {
                // User not found in Firestore
                showToast(context, "User not found.")
            }
        }
        .addOnFailureListener { e ->
            showToast(context, "Error checking user: $e")
        }
}


