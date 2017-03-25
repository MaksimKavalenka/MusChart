package com.muschart.activity;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.common.net.UrlEscapers;
import com.muschart.R;
import com.squareup.picasso.Picasso;

import java.io.IOException;

public class MediaActivity extends Activity implements MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener, View.OnClickListener {

    private MediaPlayer mediaPlayer;
    private TextView run, end;
    private ImageButton resume, pause, stop;
    private SeekBar seekPlaying;
    private Runnable runnable;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.media);

        handler = new Handler();

        ((TextView) findViewById(R.id.artist)).setText(getIntent().getStringExtra("artist"));
        ((TextView) findViewById(R.id.name)).setText(getIntent().getStringExtra("name"));
        Picasso.with(this).load(UrlEscapers.urlFragmentEscaper().escape(getIntent().getStringExtra("cover"))).into((ImageView) findViewById(R.id.cover));

        run = (TextView) findViewById(R.id.run);
        end = (TextView) findViewById(R.id.end);

        resume = (ImageButton) findViewById(R.id.buttonResume);
        pause = (ImageButton) findViewById(R.id.buttonPause);
        stop = (ImageButton) findViewById(R.id.buttonStop);
        resume.setOnClickListener(this);
        pause.setOnClickListener(this);
        stop.setOnClickListener(this);

        seekPlaying = (SeekBar) findViewById(R.id.seekPlaying);
        seekPlaying.setOnTouchListener((view, event) -> {
            seekPlaying.setMax(mediaPlayer.getDuration());
            mediaPlayer.seekTo(seekPlaying.getProgress());
            if (!mediaPlayer.isPlaying())
                mediaPlayer.start();
            return false;
        });

        releaseMP();
        play();
        if (mediaPlayer == null)
            return;

        mediaPlayer.setOnCompletionListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
        releaseMP();
        handler.removeCallbacks(runnable);
    }

    private void releaseMP() {
        if (mediaPlayer != null) {
            try {
                mediaPlayer.release();
                mediaPlayer = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void play() {
        try {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(getIntent().getStringExtra("song"));
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnPreparedListener(this);
            mediaPlayer.prepareAsync();
            setTime();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setTime() {
        seekPlaying.setMax(mediaPlayer.getDuration());

        int endSeconds = (mediaPlayer.getDuration() / 1000) % 60;
        String endString = (endSeconds < 10) ? "0" + endSeconds : String.valueOf(endSeconds);
        end.setText(String.valueOf(mediaPlayer.getDuration() / 60000) + ':' + endString);

        if (mediaPlayer.isPlaying()) {
            int runSeconds = (mediaPlayer.getCurrentPosition() / 1000) % 60;
            String runString = (runSeconds < 10) ? "0" + runSeconds : String.valueOf(runSeconds);
            run.setText(String.valueOf(mediaPlayer.getCurrentPosition() / 60000) + ':' + runString);
        }

        seekPlaying.setProgress(mediaPlayer.getCurrentPosition());
        runnable = () -> setTime();
        handler.postDelayed(runnable, 1);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonResume:
                if (!mediaPlayer.isPlaying())
                    mediaPlayer.start();
                break;
            case R.id.buttonPause:
                if (mediaPlayer.isPlaying())
                    mediaPlayer.pause();
                break;
            case R.id.buttonStop:
                mediaPlayer.stop();
                seekPlaying.setProgress(0);
                run.setText("0:00");
                break;
            default:
                break;
        }
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
    }

    @Override
    public void onBackPressed() {
        mediaPlayer.stop();
        super.onBackPressed();
    }

}