package com.android.example.tereomaori;


import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class PhrasesFragment extends Fragment {

    private MediaPlayer mMediaPlayer;
    private AudioManager mAudioManager;
    private MediaPlayer.OnCompletionListener onCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            releaseMediaPlayer();
        }
    };
    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if(focusChange == AudioManager.AUDIOFOCUS_GAIN){
                mMediaPlayer.start();
            }

            else if(focusChange == AudioManager.AUDIOFOCUS_LOSS){
                releaseMediaPlayer();
            }

            else if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange== AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK ) {
                mMediaPlayer.pause();
                mMediaPlayer.seekTo(0);
            }
        };
    };


    public PhrasesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_words, container, false);


        mAudioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Word> words = new ArrayList<Word>();
        words.add(new Word("Goodbye (from a person leaving)", "E noho rā", R.raw.goodbyetostaying));
        words.add(new Word("Goodbye (from a person staying)", "Haere rā", R.raw.goodbyetoleaving));
        words.add(new Word("Welcome! Come!", "Haere mai", R.raw.welcome));
        words.add(new Word("Goodbye (less formal)", "Hei konā rā", R.raw.goodbyecasual));
        words.add(new Word("Hi! G’day! (general informal greeting)", "Kia ora", R.raw.hellocasual));
        words.add(new Word("(Good) morning!", "Mōrena", R.raw.morning));
        words.add(new Word("Welcome! Come!", "Nau mai", R.raw.welcomecome));
        words.add(new Word("Formal greeting to one person", "Tēnā koe", R.raw.hellotoone));
        words.add(new Word("Formal greeting to two people", "Tēnā kōrua", R.raw.hellototwo));
        words.add(new Word("Formal greeting to many people", "Tēnā koutou", R.raw.hellotomany));

        WordAdapter adapter = new WordAdapter(getActivity(), words, R.color.category_phrases);

        ListView listView = (ListView) rootView.findViewById(R.id.list);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                int soundFileId = words.get(position).getSoundFileId();
                releaseMediaPlayer();
                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener, AudioManager.STREAM_MUSIC,
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED){
                    mMediaPlayer = MediaPlayer.create(getActivity(), soundFileId);
                    mMediaPlayer.start();
                    mMediaPlayer.setOnCompletionListener(onCompletionListener);
                }

            }
        });


        return rootView;
    }

    private void releaseMediaPlayer() {

        if (mMediaPlayer != null) {
            mMediaPlayer.release();
            mMediaPlayer = null;
            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }
}
