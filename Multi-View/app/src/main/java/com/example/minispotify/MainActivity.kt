package com.example.minispotify


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val infoBtn = findViewById<Button>(R.id.info)
        val favoritesButton = findViewById<Button>(R.id.openFavorites)
        favoritesButton.setOnClickListener {
            startActivity(Intent(this, FavoritesActivity::class.java))
        }

        recyclerView = findViewById(R.id.songRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = SongAdapter(allSongs.toMutableList(), this) {

            adapter.notifyDataSetChanged()
        }

        recyclerView.adapter = adapter

        infoBtn.setOnClickListener {
            showInfoDialog()
        }
    }

    override fun onResume() {
        super.onResume()

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
