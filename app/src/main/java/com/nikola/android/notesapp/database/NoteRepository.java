package com.nikola.android.notesapp.database;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import com.nikola.android.notesapp.model.Note;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

public class NoteRepository {
    private NoteDao noteDao;
    private LiveData<List<Note>> allNotes;

    public NoteRepository(Application application) {
        NoteDataBase dataBase = NoteDataBase.getInstance(application);
        noteDao = dataBase.noteDao();
        allNotes = noteDao.getAllNotes();
    }

    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }

    public Observable insert(final Note note) {
        return Observable.create(new ObservableOnSubscribe() {
            @Override
            public void subscribe(ObservableEmitter emitter) throws Exception {
                if (!emitter.isDisposed()) {
                    noteDao.insert(note);
                    emitter.onComplete();
                }
            }
        });
    }

    public Observable update(final Note note) {
        return Observable.create(new ObservableOnSubscribe() {
            @Override
            public void subscribe(ObservableEmitter emitter) throws Exception {
                if (!emitter.isDisposed()) {
                    noteDao.update(note);
                    emitter.onComplete();
                }
            }
        });
    }

    public Observable delete(final Note note) {
        return Observable.create(new ObservableOnSubscribe() {
            @Override
            public void subscribe(ObservableEmitter emitter) throws Exception {
                if (!emitter.isDisposed()) {
                    noteDao.delete(note);
                    emitter.onComplete();
                }
            }
        });
    }

    public Observable deleteAll() {
        return Observable.create(new ObservableOnSubscribe() {
            @Override
            public void subscribe(ObservableEmitter emitter) throws Exception {
                if (!emitter.isDisposed()) {
                    noteDao.deleteAllNotes();
                    emitter.onComplete();
                }
            }
        });
    }


}
