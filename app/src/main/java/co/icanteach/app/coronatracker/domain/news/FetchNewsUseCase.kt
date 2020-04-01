package co.icanteach.app.coronatracker.domain.news

import co.icanteach.app.coronatracker.core.Resource
import co.icanteach.app.coronatracker.core.inject.DefaultDispatcher
import co.icanteach.app.coronatracker.core.mapResource
import co.icanteach.app.coronatracker.data.CoronaTrackerRepository
import co.icanteach.app.coronatracker.domain.news.model.News
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FetchNewsUseCase @Inject constructor(
    private val repository: CoronaTrackerRepository,
    private val mapper: CoronaNewsMapper,
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher
) {

    fun fetchCoronaNews(): Flow<Resource<List<News>>> {
        return repository
            .fetchCoronaNews()
            .mapResource { newsResponse ->
                withContext(dispatcher) {
                    mapper.mapFromResponse(newsResponse.result)
                }
            }
    }
}