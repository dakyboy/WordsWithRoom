package com.dakiiii.wordswithroom;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int NEW_WORD_ACTIVITY_RESULT = 1;
    private WordViewModel eWordViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final WordListAdapter adapter = new WordListAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        view model
//        ViewModelProvider.AndroidViewModelFactory androidViewModelFactory  = new ViewModelProvider.AndroidViewModelFactory(getApplication());
//        eWordViewModel = new ViewModelProvider(this, androidViewModelFactory).get(WordViewModel.class);
        eWordViewModel = new ViewModelProvider.AndroidViewModelFactory(getApplication()).create(WordViewModel.class);
        eWordViewModel.getAllWords().observe(this, new Observer<List<Word>>() {
            @Override
            public void onChanged(List<Word> words) {
                adapter.setWords(words);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NEW_WORD_ACTIVITY_RESULT && resultCode == RESULT_OK) {
            Word word = new Word(data.getStringExtra(NewWordActivity.EXTRA_REPLY));
            eWordViewModel.insert(word);
        } else {
            Toast.makeText(this, R.string.empty_not_saved, Toast.LENGTH_SHORT).show();
        }
    }

    public void addWord(View view) {
        Intent intent = new Intent(MainActivity.this, NewWordActivity.class);
        startActivityForResult(intent, NEW_WORD_ACTIVITY_RESULT);
    }
}
