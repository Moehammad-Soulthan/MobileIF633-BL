package id.ac.umn.cisumreyalp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class PlayerActivity extends AppCompatActivity {
    MediaPlayer mediaPlayer;
    AudioModel audio;
    Button btnRewind, btnPlay, btnForward;
    TextView tvSongName;
    SeekBar seekBar;
    int totalTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        this.setTitle("Now Playing");

        Intent intent = getIntent();
        audio = (AudioModel) intent.getSerializableExtra("audio");

        btnRewind = findViewById(R.id.btnRewind);
        btnPlay = findViewById(R.id.btnPlay);
        btnForward = findViewById(R.id.btnForward);
        tvSongName = findViewById(R.id.tvSongName);
//        seekBar = findViewById(R.id.seekBar);

        if(audio != null) {
            tvSongName.setText(audio.getaName());
        }

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPlaying();
            }
        });

    }

    public void startPlaying(){
        if(mediaPlayer != null && mediaPlayer.isPlaying()){
            mediaPlayer.stop();
        }

        if(audio != null) {
            mediaPlayer = MediaPlayer.create(PlayerActivity.this, Uri.parse(audio.getaPath()));
            mediaPlayer.start();
        }

        enableSeekBar();
    }

    public void stopPlaying(){
        if(mediaPlayer != null){
            mediaPlayer.stop();
        }
    }

    public void enableSeekBar() {
        seekBar = findViewById(R.id.seekBar);
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
                if(fromUser){
                    mediaPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }

    @Override
    public void onBackPressed() {
        stopPlaying();
        super.onBackPressed();
    }

}