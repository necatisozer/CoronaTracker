package co.icanteach.app.coronatracker.presentation.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.icanteach.app.coronatracker.core.Resource
import co.icanteach.app.coronatracker.domain.news.FetchNewsUseCase
import co.icanteach.app.coronatracker.domain.news.model.News
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class NewsViewModel @Inject constructor(
    private val fetchNewsUseCase: FetchNewsUseCase
) : ViewModel() {

    private val news = MutableLiveData<List<News>>()

    fun getNewsResult(): LiveData<List<News>> = news

    init {
        fetchCoronaNews()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun fetchCoronaNews() {
        fetchNewsUseCase.fetchCoronaNews()
            .onEach { handleResource(it) }
            .catch { updatePageForError(it) } // For uncaught exceptions
            .launchIn(viewModelScope)
    }

    private fun handleResource(resource: Resource<List<News>>) {
        when (resource) {
            is Resource.Success -> updatePageForResult(resource.data)
            is Resource.Error -> updatePageForError(resource.exception)
            is Resource.Loading -> updatePageForLoading()
        }
    }

    private fun updatePageForResult(data: List<News>) {
        news.value = data
    }

    private fun updatePageForError(throwable: Throwable) {

    }

    private fun updatePageForLoading() {

    }
}