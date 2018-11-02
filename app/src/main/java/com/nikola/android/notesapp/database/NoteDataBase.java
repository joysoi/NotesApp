package com.nikola.android.notesapp.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.nikola.android.notesapp.model.Note;

@Database(entities = Note.class, version = 1)
public abstract class NoteDataBase extends RoomDatabase {

    private static NoteDataBase instance;

    public static synchronized NoteDataBase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    NoteDataBase.class, "note_db")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

    public abstract NoteDao noteDao();


}
