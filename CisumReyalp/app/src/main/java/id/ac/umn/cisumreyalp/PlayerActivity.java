package id.ac.umn.cisumreyalp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class PlayerActivity extends AppCompatActivity {
    MediaPlayer mediaPlayer;
    AudioModel audio;
    Button btnRewind, btnPlay, btnForward;
    TextView tvSongName;
    SeekBar seekBar;

    private final int seekForwardTime = 10 * 1000;
    private final int seekBackwardTime = 10 * 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        this.setTitle("Now Playing");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        audio = (AudioModel) intent.getSerializableExtra("audio");

        btnRewind = findViewById(R.id.btnRewind);
        btnPlay = findViewById(R.id.btnPlay);
        btnForward = findViewById(R.id.btnForward);
        tvSongName = findViewById(R.id.tvSongName);
        seekBar = findViewById(R.id.seekBar);

        if(audio != null) {
            tvSongName.setText(audio.getaTitle());
        }

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getBaseContext(), audio.getaPath() + "\n" + audio.getaId(), Toast.LENGTH_SHORT).show();
//                mediaPlayer = new MediaPlayer();

//                try {
//                    mediaPlayer.setDataSource(audio.getaPath());
//                    mediaPlayer.prepare();
//                    mediaPlayer.start();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }

                startPlaying();
            }
        });

        btnForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer != null){
                    int currentPosition = mediaPlayer.getCurrentPosition();
                    if (currentPosition + seekForwardTime <= mediaPlayer.getDuration()){
                        mediaPlayer.seekTo(currentPosition+seekForwardTime);
                    }
                    else{
                        mediaPlayer.seekTo(mediaPlayer.getDuration());
                    }
                }
            }
        });

        btnRewind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer != null){
                    int currentPosition = mediaPlayer.getCurrentPosition();
                    if (currentPosition - seekBackwardTime >=0){
                        mediaPlayer.seekTo(currentPosition - seekBackwardTime);
                    }
                    else{
                        mediaPlayer.seekTo(0);
                    }
                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                stopPlaying();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        stopPlaying();
        super.onBackPressed();
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public void startPlaying() {
        if(audio != null) {
            if(mediaPlayer != null && mediaPlayer.isPlaying()) {
                btnPlay.setBackground(getDrawable(R.drawable.ic_play));
                mediaPlayer.stop();
            } else {
                mediaPlayer = MediaPlayer.create(PlayerActivity.this, Uri.parse(audio.getaPath()));
                btnPlay.setBackground(getDrawable(R.drawable.ic_pause));
                mediaPlayer.start();
            }
        }

        enableSeekBar();
    }

    public void stopPlaying() {
        if(mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }

    public void enableSeekBar() {
        seekBar.setMax(mediaPlayer.getDuration());

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if(mediaPlayer != null && mediaPlayer.isPlaying()) {
                    seekBar.setProgress(mediaPlayer.getCurrentPosition());
                }
            }
        }, 0, 10);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // Update the progress depending on seek bar
                if(fromUser) {
                    mediaPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }
}