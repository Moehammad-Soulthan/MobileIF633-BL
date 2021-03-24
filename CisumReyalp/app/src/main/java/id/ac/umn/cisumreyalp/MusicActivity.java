package id.ac.umn.cisumreyalp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MusicActivity extends AppCompatActivity {
    private long backPressedTime;
    RecyclerView rvList;
    SongAdapter songAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);

        this.setTitle("Song List");
        startActivity(new Intent(MusicActivity.this, WelcomePopActivity.class));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
                return;
            }
        }

        getSongs();
    }

    @Override
    public void onBackPressed() {
        if(backPressedTime + 2000 > System.currentTimeMillis()) {
            finish();
            super.onBackPressed();
            return;
        } else {
            Toast.makeText(getBaseContext(), "Back Again To Logout", Toast.LENGTH_SHORT).show();
        }

        backPressedTime = System.currentTimeMillis();
    }

    public void getSongs(){
        rvList = findViewById(R.id.rvList);

        List<AudioModel> allAudioFiles = getAllAudioFromDevice(MusicActivity.this);

        songAdapter = new SongAdapter(MusicActivity.this, allAudioFiles);
        rvList.setAdapter(songAdapter);
        rvList.setLayoutManager(new LinearLayoutManager(MusicActivity.this));
    }

//    public ArrayList<File> findSong (File file) {
//        ArrayList<File> arrayList = new ArrayList<>();
//        File[] files = file.listFiles();
//
//        for(File singlefile: files)
//        {
//            if(singlefile.isDirectory() && !singlefile.isHidden()){
//                arrayList.addAll(findSong(singlefile));
//            }else{
//                if(singlefile.getName().endsWith(".mp3")){
//                    arrayList.add(singlefile);
//                }
//            }
//        }
//        return arrayList;
//    }

    //    https://www.programmersought.com/article/39354470599/
    public List<AudioModel> getAllAudioFromDevice(final Context context) {
        final List<AudioModel> tempAudioList = new ArrayList<>();

        Cursor cursor = getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null,
                MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
        if (cursor != null) {
            tempAudioList.clear();
            while (cursor.moveToNext()) {
                AudioModel audioModel = new AudioModel();

                String id = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID));
                String title = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));
                String singer = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
                String album = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM));
                String path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
                int time = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));
                int size = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE));
//                MyMusic myMusic = new MyMusic(id, title, singer, path, size, time, album);

                audioModel.setaId(id);
                audioModel.setaTitle(title);
                audioModel.setaArtist(singer);
                audioModel.setaAlbum(album);
                audioModel.setaPath(path);
                audioModel.setaDuration(time);
                audioModel.setaSize(size);
                tempAudioList.add(audioModel);
            }
        }

        return tempAudioList;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(MusicActivity.this,
                            Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                        getSongs();
                        Toast.makeText(MusicActivity.this,"Permission Granted",Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MusicActivity.this,"Permission Denied",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        https://www.youtube.com/watch?v=oh4YOj9VkVE
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:
//                Toast.makeText(this, "Profile Selected", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MusicActivity.this, ProfileActivity.class));
                return true;
            case R.id.item2:
//                Toast.makeText(this, "Logout Selected", Toast.LENGTH_SHORT).show();
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}