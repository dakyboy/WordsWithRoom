package com.dakiiii.wordswithroom;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.wordViewHolder> {
    private List<Word> eWords;
    @NonNull
    @Override
    public wordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_item_view, parent, false);

        return new wordViewHolder(view);
    }

    void setWords(List<Word> words) {
        eWords = words;
        notifyDataSetChanged();
    }

    public Word getWordAtPosition(int position){
        return eWords.get(position);
    }

    @Override
    public void onBindViewHolder(@NonNull wordViewHolder holder, int position) {
        if (eWords != null) {
            Word word = eWords.get(position);
            holder.wordTextView.setText(word.getWord());
        } else {
            holder.wordTextView.setText("No word");
        }
    }

    @Override
    public int getItemCount() {
        if (eWords != null) {
            return eWords.size();
        }
        return 0;
    }

    public class wordViewHolder  extends RecyclerView.ViewHolder {
        private TextView wordTextView;
        public wordViewHolder(@NonNull View itemView) {
            super(itemView);
            wordTextView = itemView.findViewById(R.id.textView);
        }
    }
}
