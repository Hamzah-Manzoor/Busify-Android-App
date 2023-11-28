package com.example.busify

import android.content.Context
import android.os.Bundle
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
import androidx.compose.foundation.shape.RoundedCornerShape
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

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BusifyTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LoginScreen()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(
                color = Color.Black,
                shape = RoundedCornerShape(16.dp)
            )
    ) {
        Image(
            painter = painterResource(id = R.drawable.appbackground),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop, // Crop the image to fill the entire space
            alignment = Alignment.TopStart // Align the image to the top-left corner
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f)) // Set the alpha value for transparency
        ) {

            val context = LocalContext.current
            val usernameState = remember { mutableStateOf("") }
            val passwordState = remember { mutableStateOf("") }

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
                    text = "Welcome Back",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp,
                    fontFamily = FontFamily.Cursive,
                    modifier = Modifier.padding(bottom = 16.dp, top = 30.dp)
                )

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

                // Login Button
                Button(
                    onClick = { handleLogin(context, usernameState.value, passwordState.value) },
                    modifier = Modifier.width(120.dp),
                    colors = ButtonDefaults.buttonColors(Color(6, 214, 160))
                ) {

                    Text(text = "Login");
                }

                // Divider
                Divider(
                    color = Color.White,
                    thickness = 1.dp,
                    modifier = Modifier
                        .width(170.dp)
                        .padding(vertical = 16.dp)
                )

                // Google Registration Button
                Button(
                    onClick = { /* Handle Google registration button click */ },
                    modifier = Modifier.width(250.dp),
                    colors = ButtonDefaults.buttonColors(Color(255, 195, 0))
                ) {

                    Text(text = "Login with Google")
                }

            }
        }
    }
}

fun handleLogin(context: Context, username : String, password: String){

    if(username.isEmpty() && password.isEmpty()){
        showToast(context,"Please fill in all fields")
    }

    if(username.isEmpty()){
        showToast(context,"Please Enter Username")
    }

    if(password.isEmpty()){
        showToast(context,"Please Enter Password")
    }

    showToast(context,"LogIn Successful");
}

@Preview(showBackground = true, heightDp = 550, widthDp = 350)
@Composable
fun LoginPreview() {
    BusifyTheme {
        LoginScreen()
    }
}