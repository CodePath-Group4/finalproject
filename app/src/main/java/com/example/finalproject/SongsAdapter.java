package com.example.finalproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.finalproject.models.Song;

import java.util.List;

public class SongsAdapter extends RecyclerView.Adapter<SongsAdapter.ViewHolder> {

    public static final String TAG = "SongsAdapter";

    private Context context;
    private List<Song> songs;

    public SongsAdapter(Context context, List<Song> songs) {
        this.context = context;
        this.songs = songs;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_song, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SongsAdapter.ViewHolder holder, int position) {
        Song song = songs.get(position);
        holder.bind(song);
    }

    @Override
    public int getItemCount() {
        return songs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivAlbumArt;
        private TextView tvSongName;
        private TextView tvArtistName;
        private TextView tvAlbumName;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivAlbumArt = itemView.findViewById(R.id.ivAlbumArt);
            tvSongName = itemView.findViewById(R.id.tvSongName);
            tvArtistName = itemView.findViewById(R.id.tvArtistName);
            tvAlbumName = itemView.findViewById(R.id.tvAlbumName);
        }

        public void bind(Song song) {

            String url = "wmhbradio.org/wp-content/uploads/2016/07/music-placeholder.png";


            Glide.with(context)
//                    .load(song.getArtUrl());
                    .load("https://i.scdn.co/image/ab67616d0000b2731ae2bdc1378da1b440e1f610")
                    .fitCenter()
                    .into(ivAlbumArt);

            //TODO: Display correct information from each song
            //tvSongName.setText(song.getName());
            tvSongName.setText("FILLER SONG NAME");

            //tvArtistName.setText(song.getArtist());
            tvArtistName.setText("FILLER ARTIST NAME");

            //tvAlbumName.setText(song.getAlbum());
            tvAlbumName.setText("FILLER ALBUM NAME");
        }
    }
}
