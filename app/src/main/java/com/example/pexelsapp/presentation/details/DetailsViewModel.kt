package com.example.pexelsapp.presentation.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pexelsapp.domain.model.ResponseState
import com.example.pexelsapp.domain.model.photo.Photo
import com.example.pexelsapp.domain.usecase.bookmarks.AddBookmarkUseCase
import com.example.pexelsapp.domain.usecase.bookmarks.DeleteBookmarkUseCase
import com.example.pexelsapp.domain.usecase.bookmarks.GetBookmarkByIdUseCase
import com.example.pexelsapp.domain.usecase.photos.DownloadPhotoUseCase
import com.example.pexelsapp.domain.usecase.photos.GetPhotoByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val getPhotoByIdUseCase: GetPhotoByIdUseCase,
    private val getBookmarkByIdUseCase: GetBookmarkByIdUseCase,
    private val downloadPhotoUseCase: DownloadPhotoUseCase,
    private val addBookmarkUseCase: AddBookmarkUseCase,
    private val deleteBookmarkUseCase: DeleteBookmarkUseCase,
) : ViewModel() {

    private var _photoInfo = MutableStateFlow<Photo?>(null)
    val photoInfo: StateFlow<Photo?> = _photoInfo

    private var _loadingState = MutableStateFlow<ResponseState<*>>(ResponseState.Idle)
    val loadingState: StateFlow<ResponseState<*>> = _loadingState

    private var _toastMessage = MutableStateFlow<String?>(null)
    val toastMessage: StateFlow<String?> = _toastMessage

    private var _isInBookmarks = MutableStateFlow(false)
    val isInBookmarks: StateFlow<Boolean> = _isInBookmarks

    fun getPhotoInfo(id: Int?, isFromBookmarks: Boolean?) {
        _photoInfo.value = null
        _isInBookmarks.value = false
        viewModelScope.launch {
            _loadingState.value = ResponseState.Loading
            val response: ResponseState<Photo>
            if (id != null && isFromBookmarks != null) {
                response = if (isFromBookmarks) {
                    _isInBookmarks.value = true
                    getBookmarkByIdUseCase.invoke(id)
                } else getPhotoByIdUseCase.invoke(id)
                if (response is ResponseState.Success) {
                    _photoInfo.value = response.data
                } else {
                    _photoInfo.value = null
                    endLoading(response)
                }
            } else {
                endLoading(ResponseState.Error("Image not found"))
            }
        }
    }

//    fun checkIfPhotoInBookmarks(id: Int) {
//        viewModelScope.launch {
//            val response = getBookmarkByIdUseCase.invoke(id)
//            _isInBookmarks.value = response is ResponseState.Success
//        }
//    }

    fun downloadPhoto() {
        viewModelScope.launch {
            _photoInfo.value?.let {
                _loadingState.value = ResponseState.Loading
                _toastMessage.value = downloadPhotoUseCase.invoke(
                    it.source,
                    it.id
                )
                _loadingState.value = ResponseState.Idle
            }
        }
    }

    fun onBookmarkClick() {
        viewModelScope.launch {
            _photoInfo.value?.let {
                _loadingState.value = ResponseState.Loading
                if (_isInBookmarks.value) {
                    deleteBookmarkUseCase.invoke(it)
                    _isInBookmarks.value = false
                } else {
                    addBookmarkUseCase.invoke(it)
                    _isInBookmarks.value = true
                }
                endLoading()
            }
        }
    }

    fun endLoading(response: ResponseState<*> = ResponseState.Idle) {
        viewModelScope.launch {
            delay(800)
            _loadingState.value = response
        }
    }

    fun resetToastMessage() {
        _toastMessage.value = null
    }
}