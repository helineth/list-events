//package com.arena.eventos.ui.screens
//
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.arena.eventos.database.dao.states.HomeScreenUiState
//import com.arena.eventos.database.model.EventType
//import com.arena.eventos.repository.EventRepositoryImpl
//
//import dagger.hilt.android.lifecycle.HiltViewModel
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.asStateFlow
//import kotlinx.coroutines.flow.update
//import kotlinx.coroutines.launch
//import kotlinx.coroutines.withContext
//import javax.inject.Inject
//
//@HiltViewModel
//class HomeScreenViewModel @Inject constructor(
//    private val eventRepositoryImpl: EventRepositoryImpl
//): ViewModel() {
//
//    private val _uiState: MutableStateFlow<HomeScreenUiState> =
//        MutableStateFlow(HomeScreenUiState())
//
//    val uiState = _uiState.asStateFlow()
//
//    init {
//        viewModelScope.launch {
//            loadEventData()
//        }
//    }
//
////    private suspend fun loadEventData(){
////        viewModelScope.launch {
//////            val events: List<EventType> = withContext(Dispatchers.Default){
//////                eventRepositoryImpl.getAllEvents()
//////            }
////            _uiState.update {
////                it.copy(
////                    events = events
////                )
////            }
////            //val event: Event = withContext(Dispatchers.Default){eventRepositoryImpl}
//        }
//
//    }
//
//
//}