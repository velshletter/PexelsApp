package com.example.pexelsapp.presentation.bookmarks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pexelsapp.domain.model.ResponseState
import com.example.pexelsapp.domain.model.photo.Photo
import com.example.pexelsapp.domain.usecase.bookmarks.GetAllBookmarksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarksViewModel @Inject constructor(
    private val getAllBookmarksUseCase: GetAllBookmarksUseCase
) : ViewModel() {

    private var _photoList = MutableStateFlow<List<Photo>>(listOf())
    val photoList: StateFlow<List<Photo>> = _photoList

    private var _responseState = MutableStateFlow<ResponseState<*>>(ResponseState.Idle)
    val responseState: StateFlow<ResponseState<*>> = _responseState

    fun loadBookmarks() {
        viewModelScope.launch {
            _responseState.value = ResponseState.Loading
            _photoList.value = getAllBookmarksUseCase.invoke()
            delay(800)
            _responseState.value = ResponseState.Idle
        }
    }
}