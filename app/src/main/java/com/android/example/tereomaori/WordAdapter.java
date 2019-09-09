package com.android.example.tereomaori;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.example.tereomaori.R;
import com.android.example.tereomaori.Word;

import java.util.ArrayList;

/**
 * Created by Andrew on 7/07/2019.
 */

public class WordAdapter extends ArrayAdapter<Word> {

    private int mBackgroundColour;

    public WordAdapter(Activity context, ArrayList<Word> words, int backgroundColour){
        super(context, 0, words);
        mBackgroundColour = backgroundColour;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        Word currentWord = getItem(position);

        View wordBackground = listItemView.findViewById(R.id.word_background);
        int color = ContextCompat.getColor(getContext(), mBackgroundColour);
        wordBackground.setBackgroundColor(color);

        TextView englishTextView = (TextView) listItemView.findViewById(R.id.english_text);
        englishTextView.setText(currentWord.getEnglishTranslation());

        TextView maoriTextView = (TextView) listItemView.findViewById(R.id.maori_text);
        maoriTextView.setText(currentWord.getMaoriTranslation());

        ImageView imageView = (ImageView) listItemView.findViewById(R.id.word_image);

        if (currentWord.hasImage()){
            imageView.setImageResource(currentWord.getImageResourceId());
            imageView.setVisibility(View.VISIBLE);
        }
        else{
            imageView.setVisibility(View.GONE);
        }

        return listItemView;
    }
}
