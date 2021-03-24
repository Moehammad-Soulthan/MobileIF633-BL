package id.ac.umn.cisumreyalp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.SongViewHolder> {
    Context ctx;
    List<AudioModel> songs;


    SongAdapter(Context ctx, List<AudioModel> audioModelList) {
        this.ctx = ctx;
        this.songs = audioModelList;
    }

    @NonNull
    @Override
    public SongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.song_list, parent, false);
        SongViewHolder holder = new SongViewHolder(ctx, songs, view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull SongAdapter.SongViewHolder holder, int position) {
        holder.songName.setText(songs.get(position).getaTitle());
    }

    @Override
    public int getItemCount() {
        return songs.size();
    }

    public static class SongViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        Context nContext;
        List<AudioModel> songs;
        public TextView songName;

        public SongViewHolder(Context context, List<AudioModel> audioModelList, View v) {
            super(v);
            nContext = context;
            songs = audioModelList;
            songName = v.findViewById(R.id.songName);

            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View view){
            int itemPosition = getAdapterPosition();

            Intent intent = new Intent(nContext, PlayerActivity.class);
            intent.putExtra("audio", songs.get(itemPosition));

            nContext.startActivity(intent);
        }
    }

}
