package co.icanteach.app.coronatracker.domain.dashboard

import co.icanteach.app.coronatracker.core.Resource
import co.icanteach.app.coronatracker.core.combineResource
import co.icanteach.app.coronatracker.core.inject.DefaultDispatcher
import co.icanteach.app.coronatracker.data.CoronaTrackerRepository
import co.icanteach.app.coronatracker.domain.DashboardItemMapper
import co.icanteach.app.coronatracker.domain.dashboard.model.DashboardItem
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class FetchDashboardUseCase @Inject constructor(
    private val repository: CoronaTrackerRepository,
    private val mapper: DashboardItemMapper,
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    fun fetchDashboard(): Flow<Resource<List<DashboardItem>>> {
        val totalDataFlow = repository.fetchTotalData()
        val countriesDataFlow = repository.fetchCountriesData()
        return totalDataFlow
            .combineResource(countriesDataFlow) { totalData, countriesData ->
                mapper.mapFromResponse(totalData, countriesData)
            }
            .flowOn(dispatcher)
    }
}