package com.ahmedtikiwa.mynewsapp.ui.headlines

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ahmedtikiwa.mynewsapp.domain.Article
import com.ahmedtikiwa.mynewsapp.repository.NewsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class HeadlinesViewModel(application: Application) : AndroidViewModel(application) {

    /**
     * This is the job for all coroutines started by this ViewModel.
     *
     * Cancelling this job will cancel all coroutines started by this ViewModel.
     */
    private val viewModelJob = SupervisorJob()

    // the Coroutine runs using the Main (UI) dispatcher
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val newsRepository = NewsRepository()

    private val _navigateToSelectedHeadline = MutableLiveData<Article>()

    val navigateToSelectedHeadline: LiveData<Article>
        get() = _navigateToSelectedHeadline

    fun displayHeadlineDetails(article: Article) {
        _navigateToSelectedHeadline.value = article
    }

    fun displayHeadlineDetailsComplete() {
        _navigateToSelectedHeadline.value = null
    }

    /**
     * init{} is called immediately when this ViewModel is created.
     */
    init {
        viewModelScope.launch {
            newsRepository.refreshHeadlines()
        }
    }

    val headlinesList = newsRepository.headlines

    /**
     * Cancel all coroutines when the ViewModel is cleared
     */
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(HeadlinesViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return HeadlinesViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}