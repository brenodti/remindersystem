package com.example.remindersystem

import android.app.Application
import androidx.navigation.NavController
import com.example.remindersystem.db.ReminderDatabase
import com.example.remindersystem.db.repository.ReminderRepository
import com.example.remindersystem.ui.form.NewReminderFormViewModel
import com.example.remindersystem.ui.list.ReminderListViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.context.startKoin
import org.koin.dsl.module
import timber.log.Timber
import timber.log.Timber.DebugTree

class App : Application() {

    private val appModule = module {
        viewModel { (navController: NavController) -> ReminderListViewModel(get(), navController) }
        viewModel { (navController: NavController) -> NewReminderFormViewModel(get(), navController) }

        single { ReminderRepository(get()) }

        single { get<ReminderDatabase>().reminderDao() }
        single { ReminderDatabase.getInstance(androidContext()) }
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