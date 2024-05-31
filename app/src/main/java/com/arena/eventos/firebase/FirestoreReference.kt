package com.arena.eventos.firebase
import com.google.firebase.firestore.FirebaseFirestore

abstract class FirestoreReference(firestore: FirebaseFirestore) {
    open val eventRef = firestore.collection(Collections.EVENTS)
}