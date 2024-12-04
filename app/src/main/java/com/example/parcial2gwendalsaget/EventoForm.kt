package com.example.parcial2gwendalsaget

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.firebase.database.DatabaseReference

@Composable
fun EventoForm(eventoRef: DatabaseReference) {

    var nombre by remember { mutableStateOf("") }
    var fecha by remember { mutableStateOf("") }
    var direccion by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var aforo by remember { mutableStateOf("") }
    var preciotext by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }
    var precio = 0.0f

    Column(modifier = Modifier.padding(16.dp)) {
        TextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre") },
            modifier = Modifier.fillMaxWidth()
        )

        TextField(
            value = fecha,
            onValueChange = { fecha = it },
            label = { Text("Fecha") },
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
        )

        TextField(
            value = preciotext,
            onValueChange = {
                if (it.length <= 5) {
                    preciotext = it.filter { float -> float.isDigit() }
                    precio = preciotext.toFloatOrNull() ?: 0f
                }
            },
            label = { Text("Precio") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
        )

        TextField(
            value = descripcion,
            onValueChange = { if (it.length <= 500) descripcion = it },
            label = { Text("Descripcion") },
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
        )
        TextField(
            value = direccion,
            onValueChange = { direccion = it },
            label = { Text("Direccion") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = aforo,
            onValueChange = { aforo = it },
            label = { Text("Aforo") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                if (nombre.isNotEmpty() && fecha.isNotEmpty() && direccion.isNotEmpty()) {
                    val eventoId = eventoRef.push().key ?: ""
                    val newEvento = Evento(eventoId, nombre, precio, fecha, direccion, descripcion, aforo)
                    eventoRef.child(eventoId).setValue(newEvento)
                    message = "Nuevo Evento !"
                    nombre = ""
                    fecha = ""
                    preciotext = ""
                    direccion = ""
                    descripcion = ""
                    aforo = ""
                }
                else {
                    message = "Los campos : Nombre, Fecha y Direccion son obligatorios"
                }
            },
            modifier = Modifier.padding(top = 16.dp).align(Alignment.End)
        ) {
            Text("Añadir")
        }

        if (message.isNotEmpty()) {
            Text(message, color = MaterialTheme.colorScheme.primary, modifier = Modifier.padding(top = 8.dp))
        }
    }
}