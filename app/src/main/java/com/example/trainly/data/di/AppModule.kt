package com.example.trainly.data.di
import androidx.room.Room
import com.example.trainly.data.local.TrainlyDatabase
import com.example.trainly.data.repository.ClientRepositoryImpl
import com.example.trainly.domain.repo.ClientRepository
import com.example.trainly.presentation.client_list.ClientListViewModel
import com.example.trainly.presentation.client_profile.ClientProfileViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single {
        Room.databaseBuilder(
            androidContext(),
            TrainlyDatabase::class.java,
            "trainly_database"
        ) .fallbackToDestructiveMigration()
            .build()

    }

    single { get<TrainlyDatabase>().clientDao() }

    // Repository: linking the Interface to its Implementation
    single<ClientRepository> { ClientRepositoryImpl(get()) }

    // ViewModel
    viewModel { ClientListViewModel(get()) }
    viewModel { (clientId: String) ->
        ClientProfileViewModel(repository = get(), clientId = clientId)
    }
}