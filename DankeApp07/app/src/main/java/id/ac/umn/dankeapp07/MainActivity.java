package id.ac.umn.dankeapp07;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import id.ac.umn.dankeapp07.tutorialGaleriVideo.TGaleriVideoActivity;
import id.ac.umn.dankeapp07.tutorialKamera.TKameraActivity;

public class MainActivity extends AppCompatActivity {
    private Button btnTutorialKamera;
    private Button btnTutorialGaleriVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.setTitle("MULTIMEDIA & KAMERA");

        btnTutorialKamera = (Button) findViewById(R.id.btnTutorialKamera);
        btnTutorialGaleriVideo = (Button) findViewById(R.id.btnTutorialGaleriVideo);

        btnTutorialKamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TKameraActivity.class));
            }
        });

        btnTutorialGaleriVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TGaleriVideoActivity.class));
            }
        });
    }
}