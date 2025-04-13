package com.example.itnotes.di

import android.app.Application
import androidx.room.Room
import com.example.itnotes.data.local.NoteDao
import com.example.itnotes.data.local.NoteDatabase
import com.example.itnotes.data.repository.NoteRepositoryImpl
import com.example.itnotes.domain.repository.NoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    // Provide Room Database
    @Provides
    @Singleton
    fun provideNoteDatabase(app: Application): NoteDatabase {
        return Room.databaseBuilder(
            app,
            NoteDatabase::class.java,
            "note_database"
        )
            .fallbackToDestructiveMigration(false)
            .build()
    }

    // Provide DAO
    @Provides
    fun provideNoteDao(noteDatabase: NoteDatabase): NoteDao {
        return noteDatabase.noteDao()
    }

    // Provide Repository
    @Provides
    @Singleton
    fun provideNoteRepository(noteDao: NoteDao): NoteRepository {
        return NoteRepositoryImpl(noteDao)
    }


}