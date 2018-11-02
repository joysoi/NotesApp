package com.nikola.android.notesapp.viewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.nikola.android.notesapp.database.NoteRepository;
import com.nikola.android.notesapp.model.Note;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.schedulers.Schedulers;

public class NoteViewModel extends AndroidViewModel {
    private static final String TAG = "NoteViewModel";
    private NoteRepository repository;
    private LiveData<List<Note>> allNotes;
    private CompositeDisposable disposable = new CompositeDisposable();

    public NoteViewModel(@NonNull Application application) {
        super(application);
        repository = new NoteRepository(application);
        allNotes = repository.getAllNotes();
    }

    public void insert(Note note) {
        disposable.add(repository.insert(note)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .ignoreElements()
                .subscribeWith(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        Log.i(TAG, "Inserted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "NOT inserted");
                        e.printStackTrace();
                    }
                }));

    }

    public void update(Note note) {
        disposable.add(repository.update(note)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .ignoreElements()
                .subscribeWith(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        Log.i(TAG, "Updated");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "NOT updated");
                        e.printStackTrace();
                    }
                }));
    }

    public void delete(Note note) {
        disposable.add(repository.delete(note)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .ignoreElements()
                .subscribeWith(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        Log.i(TAG, "Deleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "NOT Deleted");
                        e.printStackTrace();
                    }
                }));
    }

    public void deleteAllNotes() {
        disposable.add(repository.deleteAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .ignoreElements()
                .subscribeWith(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        Log.i(TAG, "All Deleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "ALL NOT Deleted");
                        e.printStackTrace();
                    }
                }));
    }

    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.dispose();
    }
}
