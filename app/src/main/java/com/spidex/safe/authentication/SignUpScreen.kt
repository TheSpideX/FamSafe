package com.spidex.safe

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.spidex.safe.authentication.AuthViewModel


@Composable
fun SignUpScreen(
    authViewModel: AuthViewModel,
    onNavigateToLogin: () -> Unit,
    onSuccess : () ->Unit
) {
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmedPassword by remember { mutableStateOf("") }
    val focusManager: FocusManager = LocalFocusManager.current
    var isValidEmail by remember { mutableStateOf(true) }
    var isValidPassword by remember { mutableStateOf(true) }
    var isValidUsername by remember { mutableStateOf(true) }

    val currentError by authViewModel.errorMessage.collectAsState(initial = null)
    val isLoading by authViewModel.isCreatingUser.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = username,
            onValueChange = {
                username = it
                isValidUsername = it.isNotBlank()
            },
            label = { Text("Username") },
            isError = !isValidUsername,
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            keyboardActions = KeyboardActions(
                onDone = { focusManager.moveFocus(FocusDirection.Down) }
            )
        )
        if (!isValidUsername) {
            Text("Username cannot be blank", color = Color.Red)
        }

        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = email,
            onValueChange = {
                email = it
                isValidEmail = it.isNotBlank() && android.util.Patterns.EMAIL_ADDRESS.matcher(it).matches()
            },
            label = { Text("Email") },
            isError = !isValidEmail,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            keyboardActions = KeyboardActions(
                onDone = { focusManager.moveFocus(FocusDirection.Down) }
            )
        )
        if (!isValidEmail) {
            Text("Please enter a valid email", color = Color.Red)
        }

        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
                isValidPassword = it.length >= 6
            },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            isError = !isValidPassword,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            keyboardActions = KeyboardActions(
                onDone = { focusManager.moveFocus(FocusDirection.Down) }
            )
        )
        if (!isValidPassword) {
            Text("Password must be at least 6 characters long", color = Color.Red)
        }

        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = confirmedPassword,
            onValueChange = { confirmedPassword = it },
            label = { Text("Confirm Password") },
            visualTransformation = PasswordVisualTransformation(),
            isError = password != confirmedPassword,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            keyboardActions = KeyboardActions(
                onDone = { focusManager.clearFocus() }
            )
        )
        if (password != confirmedPassword) {
            Text("Passwords do not match", color = Color.Red)
        }

        if (currentError != null) {
            Text(
                text = currentError!!,
                color = Color.Red,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = { authViewModel.signUpWithEmailAndPassword(email, password, username) },
            enabled = isValidEmail && isValidPassword && password == confirmedPassword && !isLoading,
        ) {
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier
                    .width(24.dp)
                    .height(24.dp))
            } else {
                Text("Sign Up")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        TextButton(onClick = {
            onNavigateToLogin()
        }) {
            Text("Already have an account? Login")
        }
    }

    LaunchedEffect(authViewModel.currentUser) {
        authViewModel.currentUser.collect { user ->
            if (user != null) {
                onSuccess()
            }
        }
    }
}