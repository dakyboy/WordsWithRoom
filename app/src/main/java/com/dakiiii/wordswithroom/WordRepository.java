package com.dakiiii.wordswithroom;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class WordRepository {
    private WordDao eWordDao;
    private LiveData<List<Word>> eAllWords;

    public WordRepository(Application application) {
        WordRoomDatabase wordRoomDatabase = WordRoomDatabase.getInstance(application);
        eWordDao = wordRoomDatabase.eWordDao();
        eAllWords = eWordDao.getAllWords();
    }

    public void insert(Word word) {
        new insertAsyncTask(eWordDao).execute(word);

    }

    public LiveData<List<Word>> getAllWords() {
        return eAllWords;
    }

    private static class insertAsyncTask extends AsyncTask<Word, Void, Void> {
        private WordDao eAsyncWordDao;

        insertAsyncTask(WordDao wordDao) {
            eAsyncWordDao = wordDao;
        }
        @Override
        protected Void doInBackground(Word... words) {
            eAsyncWordDao.insert(words[0]);
            return null;
        }
    }
}
