package com.example.firestorerestartbug

import androidx.activity.ComponentActivity
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import timber.log.Timber

class MainActivity : ComponentActivity() {

    private lateinit var registration: ListenerRegistration

    override fun onStart() {
        super.onStart()
        readFirestore()
    }

    private fun readFirestore() {
        registration = Firebase.firestore.collection("config").document("single-id")
            .addSnapshotListener { snapshot, error ->
                val contents = snapshot?.toObject(FirestoreResponse::class.java)
                Timber.d("Firestore contents: $contents")
                Timber.d("Firestore error: $error")
            }
    }

    override fun onStop() {
        registration.remove()
        super.onStop()
    }
}

data class FirestoreResponse(
    val enabled: Boolean = false,
)
