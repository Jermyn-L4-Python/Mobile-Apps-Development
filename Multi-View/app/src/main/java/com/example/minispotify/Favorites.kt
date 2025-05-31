package com.example.minispotify

import android.content.Context
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FavoritesActivity : AppCompatActivity() {

    private val allSongs = listOf(
        Song("Another Life - SZA", R.raw.anotherlife),
        Song("Moon River - Frank Ocean", R.raw.moonriver),
        Song("See You Again - Tyler The Creator", R.raw.see),
        Song("Transform - Daniel Caesar", R.raw.transform),
        Song("Love Me Like You - Little Mix", R.raw.little),
        Song("Super Bass - Niki Minaj", R.raw.bass),
        Song("Love Me Not - Ravyn Lenae", R.raw.love)
    )

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: SongAdapter
    private var favoriteSongs = mutableListOf<Song>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)

        val infoBtn = findViewById<Button>(R.id.info)
        infoBtn.setOnClickListener {
            showInfoDialog()
        }

        val homeButton = findViewById<Button>(R.id.openHome)
        homeButton.setOnClickListener {
            finish()
        }

        recyclerView = findViewById(R.id.songRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        loadFavoriteSongs()

        adapter = SongAdapter(favoriteSongs, this) {
            loadFavoriteSongs()
            adapter.notifyDataSetChanged()
        }

        recyclerView.adapter = adapter
    }

    private fun loadFavoriteSongs() {
        val prefs = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val favorites = prefs.getStringSet("favorites", setOf()) ?: setOf()

        favoriteSongs.clear()
        favoriteSongs.addAll(allSongs.filter { favorites.contains(it.title) })
    }

    override fun onResume() {
        super.onResume()

        loadFavoriteSongs()
        adapter.notifyDataSetChanged()
    }

    private fun showInfoDialog() {
        val dialog = AlertDialog.Builder(this)
            .setTitle("How it works")
            .setMessage(
                "Play a song by pressing the song title\n" +
                        "Use the red stop button to stop the music from playing \n" +
                        "Press the heart button to add song to your favorites\n" +
                        "Open the favorites page by tapping on 'Favorites'"
            )
            .setPositiveButton("Got it!") { dialog, _ -> dialog.dismiss() }
            .create()
        dialog.show()
    }
}