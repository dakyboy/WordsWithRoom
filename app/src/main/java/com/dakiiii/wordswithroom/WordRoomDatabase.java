package com.dakiiii.wordswithroom;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Word.class}, exportSchema = false, version = 1)
public abstract class WordRoomDatabase extends RoomDatabase {
    public static final String TAG_ROOM_DB = "Room DB: ";

    public abstract WordDao eWordDao();

    private static WordRoomDatabase sWordRoomDatabase;

    public static WordRoomDatabase getInstance(final Context context) {
        if (sWordRoomDatabase == null) {
            Log.d(TAG_ROOM_DB, "Room db Null");
            synchronized (WordRoomDatabase.class) {
                if (sWordRoomDatabase == null) {
                    sWordRoomDatabase = Room.databaseBuilder(context.getApplicationContext()
                            , WordRoomDatabase.class, "word_database")
                            .fallbackToDestructiveMigration()
                            .addCallback(sCallback)
                            .build();
                }
            }
        }
        Log.d(TAG_ROOM_DB, sWordRoomDatabase.toString());
        return sWordRoomDatabase;
    }

    private static RoomDatabase.Callback sCallback = new Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            new PopulateDbAsync(sWordRoomDatabase).execute();
        }
    };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {
        private final WordDao eWordDao;
        String[] words = {"Lemon haze", "Gorilla glue", "Logs"};

        PopulateDbAsync(WordRoomDatabase database) {
            eWordDao = database.eWordDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            if (eWordDao.getAnyWord().length < 1) {
                for (int a = 1; a <= words.length - 1; a++) {
                    Word word = new Word(words[a]);
                    eWordDao.insert(word);
                }
            }

            return null;
        }
    }
}
