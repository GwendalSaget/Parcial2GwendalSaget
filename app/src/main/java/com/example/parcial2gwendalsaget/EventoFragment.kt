package com.example.parcial2gwendalsaget

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import com.google.firebase.database.*

data class Evento(
    val id: String? = null,
    val nombre: String,
    val precio: Float,
    val fecha: String,
    val direccion: String,
    val descripcion: String,
    val aforo: String,
) {
    constructor() : this(id = null, nombre = "", precio = 0f, fecha = "", direccion = "", descripcion = "", aforo = "")
}

class EventoFragment : Fragment() {
    private lateinit var eventoRef: DatabaseReference
    private var eventoList by mutableStateOf(listOf<Evento>())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        eventoRef = FirebaseDatabase.getInstance().getReference("evento")

        eventoRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val eventos = mutableListOf<Evento>()
                snapshot.children.forEach {
                    val evento = it.getValue(Evento::class.java)?.copy(id = it.key)
                    if (evento != null) {
                        eventos.add(evento)
                    }
                }
                eventoList = eventos
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })

        return ComposeView(requireContext()).apply {
            setContent {
                LazyColumn(modifier = Modifier
                    .fillMaxSize()
                    .fillMaxWidth()
                    .padding(top = 100.dp)
                    .padding(50.dp)){
                    items(eventoList) { evento ->
                        EventoItem(
                            EVento = evento,
                            onDelete = {
                                evento.id?.let { eventoRef.child(it).removeValue() }
                            },
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun EventoItem(EVento: Evento, onDelete: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp)
            .border(1.dp, MaterialTheme.colorScheme.primary),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        /*val id: String? = null,
        val nombre: String,
        val precio: Float,
        val fecha: String,
        val direccion: String,
        val descripcion: String,
        val aforo: String,*/
        Column(modifier = Modifier.padding(8.dp)) {
            Text(text = "Nombre : ${EVento.nombre}", style = MaterialTheme.typography.titleMedium)
            Text(text = "Fecha : ${EVento.fecha}", style = MaterialTheme.typography.bodyLarge)
            if (EVento.precio != 0.0f){
                Text(text = "Precio : ${EVento.precio}", style = MaterialTheme.typography.bodyLarge)
            }
            else{
                Text(text = "Precio : gratis", style = MaterialTheme.typography.bodyLarge)
            }
            Text(text = "Direccion : ${EVento.precio}", style = MaterialTheme.typography.bodyLarge)
            Text(text = "Aforo : ${EVento.aforo}", style = MaterialTheme.typography.bodyLarge)
            Text(text = "Descripcion : ${EVento.descripcion}", style = MaterialTheme.typography.bodyMedium)

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = onDelete,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error
                ),
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Borrar evento")
            }

        }
    }
}