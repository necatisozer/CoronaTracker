package co.icanteach.app.coronatracker.core.inject

import co.icanteach.app.coronatracker.data.remote.NetworkModule
import co.icanteach.app.coronatracker.presentation.dashboard.inject.DashboardComponent
import co.icanteach.app.coronatracker.presentation.dashboard.inject.DashboardModule
import co.icanteach.app.coronatracker.presentation.news.inject.NewsComponent
import co.icanteach.app.coronatracker.presentation.news.inject.NewsModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ApplicationModule::class,
        CoroutinesModule::class,
        NetworkModule::class,
        NewsModule::class,
        DashboardModule::class]
)
interface ApplicationComponent {
    val newsComponent: NewsComponent.Factory
    val dashboardComponent: DashboardComponent.Factory
}