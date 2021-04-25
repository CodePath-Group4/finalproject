package com.example.finalproject;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.finalproject.models.Favorite;
import com.example.finalproject.models.Like;
import com.example.finalproject.models.Song;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

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

    public void clear() {
        songs.clear();
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivAlbumArt;
        private TextView tvSongName;
        private TextView tvArtistName;
        private TextView tvAlbumName;
        private TextView tvLikeCounter;
        private Button btnLike;
        private Button btnFavorite;
        boolean liked;
        boolean favorited;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivAlbumArt = itemView.findViewById(R.id.ivAlbumArt);
            tvSongName = itemView.findViewById(R.id.tvSongName);
            tvArtistName = itemView.findViewById(R.id.tvArtistName);
            tvAlbumName = itemView.findViewById(R.id.tvAlbumName);
            tvLikeCounter = itemView.findViewById(R.id.tvLikeCounter);
            btnLike = itemView.findViewById(R.id.btnLike);
            btnFavorite = itemView.findViewById(R.id.btnFavorite);

            liked = false;
            favorited = false;
        }

        public void bind(Song song) {

            Glide.with(context)
                    .load(song.getImageUrl())
//                    .load("https://i.scdn.co/image/ab67616d0000b2731ae2bdc1378da1b440e1f610")
                    .fitCenter()
                    .into(ivAlbumArt);

            //TODO: Display correct information from each song
            tvSongName.setText(song.getSongName());
//            tvSongName.setText("FILLER SONG NAME");

            tvArtistName.setText(song.getArtistName());
//            tvArtistName.setText("FILLER ARTIST NAME");

            tvAlbumName.setText(song.getAlbumName());
//            tvAlbumName.setText("FILLER ALBUM NAME");

            queryLikes(song);
            queryUserLiked(song);
            queryUserFavorited(song);

            btnLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i(TAG, "ivLike, onClick");
                    liked = !liked;
                    if (liked)
                        btnLike.setBackgroundResource(R.drawable.ufi_heart_active);
                    else
                        btnLike.setBackgroundResource(R.drawable.ufi_heart);
                }
            });

            btnFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i(TAG, "ivFavorite, onClick");
                    favorited = !favorited;
                    if (favorited)
                        btnFavorite.setBackgroundResource(R.drawable.ufi_save_active);
                    else
                        btnFavorite.setBackgroundResource(R.drawable.ufi_save);
                }
            });
        }

        public void queryLikes(Song song) {
            Log.i(TAG, "queryLikes");
            ParseQuery<Like> query = ParseQuery.getQuery(Like.class);

            query.include(Like.KEY_SONG);
            query.include(Like.KEY_USER);
            query.whereEqualTo(Like.KEY_SONG, ParseObject.createWithoutData(Song.class, song.getObjectId()));
            query.findInBackground(new FindCallback<Like>() {
                @Override
                public void done(List<Like> likesFound, ParseException e) {
                    Log.i(TAG, "queryLikes, done");
                    if (e != null) {
                        Log.e(TAG, "Problem getting likes", e);
                        return;
                    }

                    Log.i(TAG, "Number of likes found: " + likesFound.size());

                    tvLikeCounter.setText(Integer.toString(likesFound.size()));
                }
            });
        }

        public void queryUserLiked(Song song) {
            Log.i(TAG, "queryUserLiked");
            ParseQuery<Like> query = ParseQuery.getQuery(Like.class);

            query.include(Like.KEY_SONG);
            query.include(Like.KEY_USER);
            query.whereEqualTo(Like.KEY_SONG, ParseObject.createWithoutData(Song.class, song.getObjectId()));
            query.whereEqualTo(Like.KEY_USER, ParseUser.getCurrentUser());
            query.findInBackground(new FindCallback<Like>() {
                @Override
                public void done(List<Like> likesFound, ParseException e) {
                    Log.i(TAG, "queryUserLiked, done");
                    if (e != null) {
                        Log.e(TAG, "Problem getting likes", e);
                        return;
                    }

                    if (likesFound.size() == 1) {
                        Log.i(TAG, "User has liked song with ID " + song.getObjectId());
                        liked = true;
                        btnLike.setBackgroundResource(R.drawable.ufi_heart_active);
                    } else {
                        Log.i(TAG, likesFound.size() + " likes by User for song with ID " + song.getObjectId());
                        // liked and btnLike are default false, so we don't need to do anything else here
                    }
                }
            });
        }

        public void queryUserFavorited(Song song) {
            Log.i(TAG, "queryUserFavorited");
            ParseQuery<Favorite> query = ParseQuery.getQuery(Favorite.class);

            query.include(Favorite.KEY_SONG);
            query.include(Favorite.KEY_USER);
            query.whereEqualTo(Favorite.KEY_SONG, ParseObject.createWithoutData(Song.class, song.getObjectId()));
            query.whereEqualTo(Favorite.KEY_USER, ParseUser.getCurrentUser());
            query.findInBackground(new FindCallback<Favorite>() {
                @Override
                public void done(List<Favorite> favoritesFound, ParseException e) {
                    Log.i(TAG, "queryUserFavorited, done");
                    if (e != null) {
                        Log.e(TAG, "Problem getting favorites", e);
                        return;
                    }

                    if (favoritesFound.size() == 1) {
                        Log.i(TAG, "User has favorited song with ID " + song.getObjectId());
                        favorited = true;
                        btnFavorite.setBackgroundResource(R.drawable.ufi_save_active);
                    } else {
                        Log.i(TAG, favoritesFound.size() + " favorites by User for song with ID " + song.getObjectId());
                        // liked and btnLike are default false, so we don't need to do anything else here
                    }
                }
            });
        }
    }
}
