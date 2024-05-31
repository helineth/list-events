package com.arena.eventos.repository

import android.util.Log
import com.arena.eventos.database.model.EventType
import com.arena.eventos.database.model.NewsTypes
import com.arena.eventos.firebase.Collections
import com.arena.service.FirebaseSingleton
import kotlinx.coroutines.tasks.await

class NewRepositoryImpl{

    private val firestore = FirebaseSingleton.provide()

    fun getAllNews(
        onSuccess:(events:List<NewsTypes>)->Unit,
        onFailure:()->Unit
    ){

        val news = mutableListOf<NewsTypes>()
        val query = firestore.collection(Collections.NEWS)
        query.get().addOnSuccessListener { result ->
            if (!result.isEmpty){
                for (document in result.documents){
                    val event = document.toObject(NewsTypes::class.java)
                    event?.let{newsList->
                        news.add(newsList)
                    }
                }
                onSuccess(news)

            }else{
                Log.d("FirestoreTest", "isEmpty")
                onFailure()
            }
        }
    }

//    suspend fun getAllNews(): List<NewsTypes> {
//        return try {
//            val news = mutableListOf<NewsTypes>()
//            val query = firestore.collection(Collections.EVENTS).get().await()
//            for (document in query.documents) {
//                val data = document.toObject(NewsTypes::class.java)
//                data?.let{newsList->
//                       news.add(newsList)
//                   }
//            }
//            news
//        } catch (e: Exception) {
//            emptyList()
//        }
//    }

    fun getNewsById(newsId: String, onSuccess: (news: NewsTypes) -> Unit, onFailure: () -> Unit) {
        val query = firestore.collection(Collections.NEWS).document(newsId)
        query.get().addOnSuccessListener { result ->
            if (result.exists()) {
                val event = result.toObject(NewsTypes::class.java)
                event?.let {
                    onSuccess(it)
                }
            } else {
                onFailure()
            }
        }
    }


}
