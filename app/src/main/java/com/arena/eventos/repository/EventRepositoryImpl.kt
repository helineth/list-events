package com.arena.eventos.repository

import android.util.Log
import com.arena.eventos.database.model.EventType
import com.arena.eventos.database.model.ExpositorType
import com.arena.eventos.firebase.Collections
import com.arena.service.FirebaseSingleton
import kotlinx.coroutines.tasks.await

class EventRepositoryImpl{

    private val firestore = FirebaseSingleton.provide()

//    fun getAllEvents(
//        onSuccess:(events:List<EventType>)->Unit,
//        onFailure:()->Unit
//    ){
//
//        val events = mutableListOf<EventType>()
//        val query = firestore.collection(Collections.EVENTS)
//        query.get().addOnSuccessListener { result ->
//            if (!result.isEmpty){
//                for (document in result.documents){
//                    val event = document.toObject(EventType::class.java)
//                    event?.let{eventsList->
//                        if(eventsList.state)
//                            events.add(eventsList)
//                    }
//                }
//                onSuccess(events)
//            }else{
//                Log.d("FirestoreTest", "isEmpty")
//                onFailure()
//            }
//        }
//    }

    suspend fun getAllEvents(): List<EventType> {
        return try {
            val events = mutableListOf<EventType>()
            val query = firestore.collection(Collections.EVENTS).get().await()
            for (document in query.documents) {
                val event = document.toObject(EventType::class.java)
                event?.let { eventsList ->
                    if (eventsList.state)
                        events.add(eventsList)
                }
            }
            events
        } catch (e: Exception) {
            emptyList()
        }
    }

    fun getEventById(eventId: String, onSuccess: (event: EventType) -> Unit, onFailure: () -> Unit) {

        val query = firestore.collection(Collections.EVENTS).document(eventId)
        query.get().addOnSuccessListener { result ->
            if (result.exists()) {
                val event = result.toObject(EventType::class.java)
                event?.let {
                    onSuccess(it)
                }
            } else {
                onFailure()
            }
        }

    }

    fun getExpositorById(expositorId: String, onSuccess: (expositor: ExpositorType) -> Unit, onFailure: () -> Unit) {

        val query = firestore.collection(Collections.EXPOSITORES).document(expositorId)
        query.get().addOnSuccessListener { result ->
            if (result.exists()) {
                val event = result.toObject(ExpositorType::class.java)
                event?.let {
                    onSuccess(it)
                }
            } else {
                onFailure()
            }
        }

    }

}