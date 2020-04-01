package co.icanteach.app.coronatracker

import android.app.Activity
import android.app.Application
import androidx.fragment.app.Fragment
import co.icanteach.app.coronatracker.core.inject.ApplicationComponent
import co.icanteach.app.coronatracker.core.inject.DaggerApplicationComponent

class CoronaTrackerApplication : Application() {
    val appComponent: ApplicationComponent = DaggerApplicationComponent.create()
}

val Activity.appComponent get() = (applicationContext as CoronaTrackerApplication).appComponent
val Fragment.appComponent get() = (activity?.applicationContext as CoronaTrackerApplication).appComponent