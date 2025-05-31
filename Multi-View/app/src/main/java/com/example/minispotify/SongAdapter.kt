package com.example.minispotify

import android.content.Context
import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView

class SongAdapter(
    private val songs: MutableList<Song>,
    private val context: Context,
    private val onFavoritesChanged: () -> Unit
) : RecyclerView.Adapter<SongAdapter.SongViewHolder>() {

    private var mediaPlayer: MediaPlayer? = null

    inner class SongViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val stopButton: Button = itemView.findViewById(R.id.stopButton)
        val playButton: Button = itemView.findViewById(R.id.songTitle)
        val favorite: Button = itemView.findViewById(R.id.favoriteButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_song_items, parent, false)
        return SongViewHolder(view)
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val song = songs[position]
        holder.playButton.text = song.title

        val prefs = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val favorites = prefs.getStringSet("favorites", mutableSetOf())?.toMutableSet() ?: mutableSetOf()

        val isFavorite = favorites.contains(song.title)
        holder.favorite.text = if (isFavorite) "❤️" else "🤍"

        holder.favorite.setOnClickListener {
            val updatedFavorites = prefs.getStringSet("favorites", mutableSetOf())?.toMutableSet() ?: mutableSetOf()

            if (updatedFavorites.contains(song.title)) {
                updatedFavorites.remove(song.title)
            } else {
                updatedFavorites.add(song.title)
            }
            prefs.edit().putStringSet("favorites", updatedFavorites).apply()


            notifyItemChanged(position)


            onFavoritesChanged()
        }

        holder.playButton.setOnClickListener {
            mediaPlayer?.release()
            mediaPlayer = MediaPlayer.create(context, song.fileResId)
            mediaPlayer?.start()
        }

        holder.stopButton.setOnClickListener{
            mediaPlayer?.stop()
        }
    }

    override fun getItemCount(): Int = songs.size
}