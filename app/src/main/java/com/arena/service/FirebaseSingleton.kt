package com.arena.service

import android.annotation.SuppressLint
import com.arena.eventos.firebase.SERVER_IP
import com.google.firebase.firestore.FirebaseFirestore

object FirebaseSingleton {
    @SuppressLint("StaticFieldLeak")
    private val firestore = FirebaseFirestore.getInstance()
//        init {
//            firestore.useEmulator(SERVER_IP, 8080)
//        }
    fun provide():FirebaseFirestore{
        return  firestore
    }
}

//if request.auth != nul