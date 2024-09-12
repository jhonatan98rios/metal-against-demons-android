package com.teixeirarios.mad.lib.components.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.teixeirarios.mad.lib.store.tokenState.TokenState

@Composable
fun ProfileScreen() {

    val context = LocalContext.current
    val token by TokenState.tokenFlow.collectAsState()
    val isLogged = token != null

    if (!isLogged) {
        Authentication()

    } else {
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Profile Screen",
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Button(onClick = { TokenState.clearToken(context) }) {
                Text("Logout")
            }
        }
    }
}