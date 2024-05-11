package com.teixeirarios.mad

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        // Encontre o botão pelo ID
        val startGameButton: Button = findViewById(R.id.startGameButton)

        // Configurar um listener de clique para o botão
        startGameButton.setOnClickListener {
            // Iniciar a Activity do jogo
            val intent = Intent(this, AndroidLauncher::class.java)
            startActivity(intent)
        }
    }
}