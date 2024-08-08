package com.example.pexelsapp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pexelsapp.domain.model.ResponseState
import com.example.pexelsapp.domain.model.collection.Collection
import com.example.pexelsapp.domain.model.photo.PhotoResponse
import com.example.pexelsapp.domain.usecase.collections.GetFeaturedCollectionsUseCase
import com.example.pexelsapp.domain.usecase.photos.GetCuratedPhotosUseCase
import com.example.pexelsapp.domain.usecase.photos.SearchPhotosUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getCuratedPhotosUseCase: GetCuratedPhotosUseCase,
    private val getFeaturedCollectionsUseCase: GetFeaturedCollectionsUseCase,
    private val searchPhotosUseCase: SearchPhotosUseCase,
) : ViewModel() {

    private var searchJob: Job? = null
    private var originalCollectionsList: List<Collection> = listOf()

    private var lastRequestMethod: () -> Unit = {}
    private var _searchText = MutableStateFlow("")
    val searchText: StateFlow<String> = _searchText

    private var _photoResponseList = MutableStateFlow<List<PhotoResponse>>(listOf())
    val photoResponseList: StateFlow<List<PhotoResponse>> = _photoResponseList

    private var _featuredCollectionsList = MutableStateFlow<List<Collection>>(listOf())
    val featuredCollectionsList: StateFlow<List<Collection>> = _featuredCollectionsList

    private var _selectedCollection = MutableStateFlow<String?>(null)
    val selectedCollection: StateFlow<String?> = _selectedCollection

    private var _responseState = MutableStateFlow<ResponseState<*>>(ResponseState.Idle)
    val responseState: StateFlow<ResponseState<*>> = _responseState

    fun searchPhotosByTags() {
        viewModelScope.launch {
            _responseState.value = ResponseState.Loading
            val response = if(_searchText.value.isBlank()){
                getCuratedPhotosUseCase.invoke()
            }
            else searchPhotosUseCase.invoke(_searchText.value)
            if (response is ResponseState.Success) {
                _photoResponseList.value = response.data.photoResponses
                delay(1000)
                _responseState.value = ResponseState.Idle
            } else {
                _photoResponseList.value = emptyList()
                lastRequestMethod = { searchPhotosByTags() }
                delay(1000)
                _responseState.value = response
            }
        }
    }

    fun getCuratedPhotosAndFeaturedCollections() {
        viewModelScope.launch {
            _responseState.value = ResponseState.Loading
            val collectionsResponse = getFeaturedCollectionsUseCase.invoke()
            val curatedPhotosResponse = getCuratedPhotosUseCase.invoke()
            if (curatedPhotosResponse is ResponseState.Success && collectionsResponse is ResponseState.Success) {
                _photoResponseList.value = curatedPhotosResponse.data.photoResponses
                val collections = collectionsResponse.data.collections
                _featuredCollectionsList.value = collections
                originalCollectionsList = collections
                delay(1000)
                _responseState.value = ResponseState.Idle
            } else {
                _photoResponseList.value = emptyList()
                lastRequestMethod = { getCuratedPhotosAndFeaturedCollections() }
                delay(1000)
                _responseState.value = ResponseState.Error("Network error")
            }
        }
    }

    fun onSearchTextChange(text: String) {
        _searchText.value = text
        val collection =
            originalCollectionsList.find { it.title.equals(text.trim(), ignoreCase = true) }

        if (collection?.title?.lowercase() == text.trim().lowercase()) {
            selectedCollectionToFirst(collection)
            _selectedCollection.value = collection.id

        } else {
            _featuredCollectionsList.value = originalCollectionsList
            _selectedCollection.value = null
        }

        searchJob?.cancel()

        searchJob = CoroutineScope(Dispatchers.Main).launch {
            delay(600)
            searchPhotosByTags()
        }
    }

    fun onCollectionClick(collectionId: String) {
        viewModelScope.launch {
            val collection = originalCollectionsList.find { it.id == collectionId }
            if (collection != null) {
                _searchText.value = collection.title
                _selectedCollection.value = collectionId
                searchPhotosByTags()
                selectedCollectionToFirst(collection)
            } else {
                _featuredCollectionsList.value = originalCollectionsList
            }
        }
    }

    private fun selectedCollectionToFirst(collection: Collection) {
        val currentList = originalCollectionsList.toMutableList()
        currentList.remove(collection)
        currentList.add(0, collection)
        _featuredCollectionsList.value = currentList
    }

    fun clearSearchBar() {
        _searchText.value = ""
        _featuredCollectionsList.value = originalCollectionsList
        _selectedCollection.value = null
    }

    fun retryRequest() {
        lastRequestMethod()
    }
}