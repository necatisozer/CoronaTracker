package co.icanteach.app.coronatracker.data

import co.icanteach.app.coronatracker.core.Resource
import co.icanteach.app.coronatracker.core.apiCall
import co.icanteach.app.coronatracker.data.remote.model.CountriesDataResponse
import co.icanteach.app.coronatracker.data.remote.model.NewsResponse
import co.icanteach.app.coronatracker.data.remote.model.TotalDataResponse
import co.icanteach.app.coronatracker.data.remote.source.RemoteDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CoronaTrackerRepository @Inject constructor(private val remoteDataSource: RemoteDataSource) {

    fun fetchCountriesData(): Flow<Resource<CountriesDataResponse>> {
        return apiCall { remoteDataSource.fetchCountriesData() }
    }

    fun fetchTotalData(): Flow<Resource<TotalDataResponse>> {
        return apiCall { remoteDataSource.fetchTotalData() }
    }

    fun fetchCoronaNews(): Flow<Resource<NewsResponse>> {
        return apiCall { remoteDataSource.fetchCoronaNews() }
    }
}