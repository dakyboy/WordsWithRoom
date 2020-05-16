package com.dakiiii.wordswithroom;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class WordViewModel extends AndroidViewModel {
    private WordRepository eWordRepository;
    private LiveData<List<Word>> eAllWords;


    public WordViewModel(Application application) {
        super(application);
        eWordRepository = new WordRepository(application);
        eAllWords = eWordRepository.getAllWords();
    }

    LiveData<List<Word>> getAllWords() {
        return eAllWords;
    }

    public void insert(Word word) {
        eWordRepository.insert(word);
    }
}
