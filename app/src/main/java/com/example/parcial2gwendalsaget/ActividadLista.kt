package com.example.parcial2gwendalsaget

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.parcial2gwendalsaget.ui.theme.Parcial2GwendalSagetTheme
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class ActividadLista : AppCompatActivity() {
    private lateinit var eventoRef: DatabaseReference

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        eventoRef = FirebaseDatabase.getInstance().getReference("evento")

        setContent {
            Parcial2GwendalSagetTheme {
                Column(modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)) {
                    Text("Lista de Eventos", fontSize = 30.sp, modifier = Modifier.padding(bottom = 16.dp))
                    val context = LocalContext.current
                        Button(
                            onClick = {
                                val intent = Intent(context, MainActivity::class.java)
                                context.startActivity(intent)
                            }
                        ) {
                            Text(text = stringResource(R.string.Principal), fontSize = 20.sp)
                        }
                    supportFragmentManager.beginTransaction()
                        .replace(android.R.id.content, EventoFragment())
                        .commit()
                }
            }
        }
    }
}