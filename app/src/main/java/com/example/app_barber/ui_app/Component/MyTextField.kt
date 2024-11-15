package com.example.app_barber.ui_app.Component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.example.app_barbaber.ui.theme.Black_Background
import com.example.app_barbaber.ui.theme.Black_White
import com.example.app_barbaber.ui.theme.Blue_Ag

@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: @Composable (() -> Unit)? = null,
    modifier: Modifier = Modifier,
    showSearchIcon: Boolean = false,  // Shows or hides the search icon
    onClearClick: () -> Unit = {},   // Action for the clear icon
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = label,
        shape = MaterialTheme.shapes.small,
        modifier = modifier,
        leadingIcon = if (showSearchIcon) {
            {
                Icon(
                    Icons.Default.Search,
                    contentDescription = "Search",
                    tint = Color.Gray // Color of the search icon
                )
            }
        } else null,  // No leading icon when showSearchIcon is false
        trailingIcon = {
            if (value.isNotEmpty()) {
                IconButton(onClick = {
                    onClearClick()
                    onValueChange("") // Clears the content of the TextField
                }) {
                    Icon(
                        Icons.Default.Clear,
                        contentDescription = "Clear",
                        tint = Color.Gray // Color of the clear icon
                    )
                }
            }
        },
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            focusedContainerColor = Black_White,
            unfocusedContainerColor = Black_White,
            disabledContainerColor = Black_White,
            errorContainerColor = Black_White,
            disabledIndicatorColor = Color.Transparent,
            errorIndicatorColor = Color.Red,
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White,
            disabledTextColor = Color.White,
            errorTextColor = Color.White
        )
    )
}


@Composable
fun PasswordTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: @Composable (() -> Unit)? = null,
    modifier: Modifier = Modifier,
    onClearClick: () -> Unit = {},   // Action for the clear icon
) {
    var passwordVisible by remember { mutableStateOf(false) }

    TextField(
        value = value,
        onValueChange = onValueChange,
        label = label,
        shape = MaterialTheme.shapes.small,
        modifier = modifier,
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            Row {
                if (value.isNotEmpty()) {
                    IconButton(onClick = {
                        onClearClick()
                        onValueChange("") // Clears the content of the TextField
                    }) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = "Clear",
                            tint = Color.Gray // Color of the clear icon
                        )
                    }
                }

                IconButton(onClick = {
                    passwordVisible = !passwordVisible
                }) {
                    Icon(
                        imageVector = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = if (passwordVisible) "Hide password" else "Show password",
                        tint = Color.Gray // Color of the visibility toggle icon
                    )
                }
            }
        },
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            focusedContainerColor = Black_White,
            unfocusedContainerColor = Black_White,
            disabledContainerColor = Black_White,
            errorContainerColor = Black_White,
            disabledIndicatorColor = Color.Transparent,
            errorIndicatorColor = Color.Red,
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White,
            disabledTextColor = Color.White,
            errorTextColor = Color.White
        )
    )
}
