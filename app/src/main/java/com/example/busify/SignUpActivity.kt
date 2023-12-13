package com.example.busify

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.busify.ui.theme.BusifyTheme
import com.google.firebase.firestore.FirebaseFirestore


data class User(
    val username: String,
    val email: String,
    val password: String,
)

class SignUpActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
                BusifyTheme {
                    // A surface container using the 'background' color from the theme
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        SignUpScreen()
                    }
                }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen() {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f)) // Set the alpha value for transparency
        ) {
            Image(
                painter = painterResource(id = R.drawable.appbackground),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop, // Crop the image to fill the entire space
                alignment = Alignment.TopStart // Align the image to the top-left corner
            )

            val context = LocalContext.current

            val db = FirebaseFirestore.getInstance()
            val usernameState = remember { mutableStateOf("") }
            val passwordState = remember { mutableStateOf("") }
            val emailState = remember { mutableStateOf("") }
            val confirmPasswordSate = remember { mutableStateOf("") }

            var passwordVisibility by remember { mutableStateOf(false) }

            val icon = if (passwordVisibility)
                painterResource(id = R.drawable.view)
            else
                painterResource(id = R.drawable.eye)

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Heading
                Text(
                    text = "Welcome to Busify!",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp,
                    fontFamily = FontFamily.Cursive,
                    modifier = Modifier.padding(bottom = 16.dp, top = 30.dp)
                )

                // Signup Page content (Add your signup page components here)
                // For example, a TextField and a Button
                OutlinedTextField(
                    value = usernameState.value,
                    onValueChange = { usernameState.value = it },
                    label = { Text("Username") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Username Icon",
                            tint = Color.White
                        )
                    },
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
                    value = emailState.value,
                    onValueChange = { emailState.value = it },
                    label = { Text("Email") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Email,
                            contentDescription = "Email Icon",
                            tint = Color.White
                        )
                    },
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
                    value = passwordState.value,
                    onValueChange = { passwordState.value = it },
                    label = { Text("Password") },
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
                    value = confirmPasswordSate.value,
                    onValueChange = { confirmPasswordSate.value = it },
                    label = { Text("Confirm Password") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = "Confirm Password Icon",
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


                // Signup Button
                Button(
                    onClick = {
                        handleSignUp(
                            context,
                            usernameState.value,
                            passwordState.value,
                            emailState.value,
                            confirmPasswordSate.value,
                            db
                        )
                    },
                    modifier = Modifier.width(120.dp),
                    colors = ButtonDefaults.buttonColors(Color(6, 214, 160))
                ) {

                    Text(text = "Sign Up")
                }

                // Divider
                Divider(
                    color = Color.White,
                    thickness = 1.dp,
                    modifier = Modifier
                        .width(170.dp)
                        .padding(vertical = 16.dp)
                )

                // Login Button to move to Login Screen
                Button(
                    onClick = {
                        val intent = Intent(context, LoginActivity::class.java)
                        context.startActivity(intent)
                    },
                    modifier = Modifier.width(120.dp),
                    colors = ButtonDefaults.buttonColors(Color(0, 180, 216))
                ) {

                    Text(text = "Move To Login")
                }
            }
        }
    }

fun handleSignUp(context: Context, username: String, password: String, email: String, confirmPassword: String, db : FirebaseFirestore) {

    if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
        showToast(context,"Please fill in all fields")
        return
    }

    if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
        showToast(context,"Invalid email format")
        return
    }

    if (password.length < 6) {
        showToast(context,"Password must be at least 6 characters long")
        return
    }

    if (password != confirmPassword) {
        showToast(context,"Passwords do not match")
        return
    }

    // If all checks pass, show a success toast
    val user = User(username, email, password)
    val usersCollection = db.collection("User")
    usersCollection
        .add(user)
        .addOnSuccessListener {
            // Handle success (data saved successfully)
            Toast.makeText(context, "User signed up successfully!", Toast.LENGTH_SHORT).show()
        }
        .addOnFailureListener { e ->
            // Handle failure (error occurred)
            Toast.makeText(context, "Error: $e", Toast.LENGTH_SHORT).show()
        }
    showToast(context,"Sign Up Successful!")

}

fun showToast(context: Context, message: String) {
    Toast.makeText(
        context,
        message,
        Toast.LENGTH_SHORT
    ).show()
}


//@Preview(showBackground = true, heightDp = 550, widthDp = 350)
//@Composable
//fun SignUpPreview() {
//    BusifyTheme {
//        SignUpScreen()
//    }
//}