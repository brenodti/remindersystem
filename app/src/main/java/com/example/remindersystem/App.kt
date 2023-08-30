package com.example.remindersystem

import android.app.Application
import androidx.navigation.NavController
import com.example.remindersystem.db.ReminderDatabase
import com.example.remindersystem.repository.ReminderRepository
import com.example.remindersystem.network.holiday.CalendarificApiService
import com.example.remindersystem.network.image.GoogleSearchApiService
import com.example.remindersystem.ui.detail.ReminderDetailViewModel
import com.example.remindersystem.ui.form.NewReminderFormViewModel
import com.example.remindersystem.ui.list.ReminderListViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import timber.log.Timber
import timber.log.Timber.DebugTree

class App : Application() {

    private val appModule = module {
        viewModel { (navController: NavController) -> ReminderListViewModel(get(), navController) }
        viewModel { (navController: NavController) -> ReminderDetailViewModel(navController) }
        viewModel { (navController: NavController) -> NewReminderFormViewModel(get(), navController) }

        single { ReminderRepository(get(), get(), get()) }

        single { get<ReminderDatabase>().reminderDao() }
        single { ReminderDatabase.getInstance(androidContext()) }

        single { CalendarificApiService.CalendarificApi.retrofitService }
        single { GoogleSearchApiService.GoogleSearchApi.retrofitService }
    }

    override fun onCreate() {
        super.onCreate()

        Timber.plant(object : DebugTree(){ })

        startKoin {
            androidContext(this@App)
            modules(appModule)
        }
    }
}