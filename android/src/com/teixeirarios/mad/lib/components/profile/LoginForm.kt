package com.teixeirarios.mad.lib.components.profile

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.gson.Gson
import com.teixeirarios.mad.lib.store.tokenState.TokenState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

@Composable
fun LoginForm(onToggleForm: () -> Unit) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var loading by remember { mutableStateOf(false) }
    var message by remember { mutableStateOf<String?>(null) }

    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current;

    fun handleClick() {

        if (username.isEmpty() || password.isEmpty() || loading) return

        coroutineScope.launch {
            loading = true
            message = try {
                loginUser(username, password, context)
            } catch (e: Exception) {
                "Erro ao realizar login: ${e.message}"
            } finally {
                loading = false
            }
        }
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Login",
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        TextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Usuário") },
            modifier = Modifier
                .background(Color.White, shape = RoundedCornerShape(12.dp))
                .height(56.dp)
                .width(300.dp),
            shape = RoundedCornerShape(12.dp)
        )
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Senha") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .background(Color.White, shape = RoundedCornerShape(12.dp))
                .height(56.dp)
                .width(300.dp),
            shape = RoundedCornerShape(12.dp)
        )
        Button(
            onClick = { handleClick() },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFAA0000)),
            modifier = Modifier
                .height(56.dp)
                .width(300.dp)
        ) {
            Text("Entrar", color = Color.White)
        }
        TextButton(onClick = onToggleForm) {
            Text("Não tem conta criada?", color = Color(0xFFAA0000))
        }

        message?.let {
            Text(it)
        }
    }
}

suspend fun loginUser(username: String, password: String, context: Context): String {
    return withContext(Dispatchers.IO) {
        val client = OkHttpClient()

        val json = JSONObject().apply {
            put("username", username)
            put("password", password)
        }

        val requestBody = json.toString()
            .toRequestBody("application/json".toMediaTypeOrNull())

        val request = Request.Builder()
            .url("http://10.0.2.2:8080/user/login")
            .post(requestBody)
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw Exception(response.message)

            val responseJson = response.body?.string() ?: throw Exception("Erro ao realizar login!")
            val token = parseTokenResponse(responseJson) ?: throw Exception("Erro ao parsear o token!")

            TokenState.saveToken(context = context, token = token)
            return@withContext "Login realizado com sucesso!"
        }
    }
}

data class TokenResponse(
    val token: String
)

fun parseTokenResponse(json: String): String? {
    val gson = Gson()
    val tokenResponse = gson.fromJson(json, TokenResponse::class.java)
    return tokenResponse.token
}

// jhonatan1998rioss
// 12345678