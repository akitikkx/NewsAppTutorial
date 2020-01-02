package com.ahmedtikiwa.mynewsapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ahmedtikiwa.mynewsapp.BuildConfig
import com.ahmedtikiwa.mynewsapp.domain.Article
import com.ahmedtikiwa.mynewsapp.network.Network
import com.ahmedtikiwa.mynewsapp.network.asDomainModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import timber.log.Timber

class NewsRepository() {

    val _headlines = MutableLiveData<List<Article>>()

    val headlines: LiveData<List<Article>>
        get() = _headlines

    suspend fun refreshHeadlines() {
        withContext(Dispatchers.IO) {
            try {
                val articleList =
                    Network.newsapi.getTopHeadlinesAsync("us", BuildConfig.NEWS_API_KEY).await()
                _headlines.postValue(articleList.asDomainModel())
            } catch (e: HttpException) {
                Timber.e(e)
            }
        }
    }

}