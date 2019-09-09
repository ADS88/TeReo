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
public class FamilyFragment extends Fragment {
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


    public FamilyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_words, container, false);
        mAudioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
        final ArrayList<Word> words = new ArrayList<Word>();
        words.add(new Word("Dad", "pāpā", R.drawable.father, R.raw.dad));
        words.add(new Word("Mother", "Whaea", R.drawable.mother, R.raw.mother));
        words.add(new Word("Son/Young man", "Tama", R.drawable.son, R.raw.son));
        words.add(new Word("Daughter", "Tamāhine", R.drawable.daughter, R.raw.daughter));
        words.add(new Word("Child", "Tamaiti", R.drawable.child, R.raw.child));
        words.add(new Word("Children", "Tamariki", R.drawable.children, R.raw.children));
        words.add(new Word("Man/Men", "Tāne", R.drawable.man, R.raw.man));
        words.add(new Word("Younger sibling of same gender", "Teina/Taina", R.drawable.youngersamegender, R.raw.youngersibling));
        words.add(new Word("Sister of a man", "Tuahine", R.drawable.sisterofman, R.raw.sisterofmale));
        words.add(new Word("Brother of a woman", "Tungāne", R.drawable.brotherofwoman, R.raw.brotherofsister));
        words.add(new Word("Woman/Wife", "Wahine", R.drawable.woman, R.raw.woman));

        WordAdapter adapter = new WordAdapter(getActivity(), words, R.color.category_family);

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
