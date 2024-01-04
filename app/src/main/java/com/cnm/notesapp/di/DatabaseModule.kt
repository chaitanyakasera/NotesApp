package com.cnm.notesapp.di

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import com.cnm.notesapp.data.local.NoteDao
import com.cnm.notesapp.data.local.NoteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(
    SingletonComponent::class
)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideNoteDao(database: NoteDatabase): NoteDao = database.noteDao

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext ctx: Context
    ): NoteDatabase =
        Room.databaseBuilder(
            ctx,
            NoteDatabase::class.java,
            "notes_db"
        ).build()
}