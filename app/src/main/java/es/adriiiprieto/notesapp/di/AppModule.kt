package es.adriiiprieto.notesapp.di

import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import es.adriiiprieto.notesapp.presentation.analytics.AnalyticsHandler

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideAnalyticsHandler(): AnalyticsHandler = AnalyticsHandler(Firebase.analytics)


}