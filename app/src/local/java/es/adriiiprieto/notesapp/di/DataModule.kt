package es.adriiiprieto.notesapp.di

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import es.adriiiprieto.notesapp.data.config.NotesDatabase
import es.adriiiprieto.notesapp.data.notes.repository.NoteRepositoryImpl
import es.adriiiprieto.notesapp.data.notes.repository.local.NoteLocal
import es.adriiiprieto.notesapp.data.preference.repository.PreferencesRepositoryImpl
import es.adriiiprieto.notesapp.data.preference.repository.local.PreferencesLocal
import es.adriiiprieto.notesapp.domain.repository.NoteRepository
import es.adriiiprieto.notesapp.domain.repository.PreferencesRepository

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    /**
     * Local build variant
     */
    @Provides
    fun provideNoteRepository(notesDatabase: NotesDatabase): NoteRepository = NoteRepositoryImpl(NoteLocal(notesDatabase.loadDatabase().noteDao()))

    private const val PREFERENCES_NAME = "MyPreferences"

    @Provides
    fun provideSharePreferences(@ApplicationContext context: Context): SharedPreferences = context.getSharedPreferences(PREFERENCES_NAME, Activity.MODE_PRIVATE)

    @Provides
    fun providesPreferencesRepository(sharedPreferences: SharedPreferences): PreferencesRepository = PreferencesRepositoryImpl(PreferencesLocal(sharedPreferences))

//    @Provides
//    fun providesPreferencesRepository(@ApplicationContext context: Context): PreferencesRepository = PreferencesRepositoryImpl(PreferencesLocal(context.getSharedPreferences(PREFERENCES_NAME, Activity.MODE_PRIVATE)))

}