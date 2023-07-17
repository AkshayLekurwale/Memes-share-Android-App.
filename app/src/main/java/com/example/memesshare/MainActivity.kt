package com.example.memesshare

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

class MainActivity : AppCompatActivity() {
    var currentImageUrl : String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         setContentView(R.layout.activity_main)

    load()
    }
    private fun load(){
        val progressB = findViewById<ProgressBar>(R.id.progressBar)
        progressB.visibility = View.VISIBLE
        val queue = Volley.newRequestQueue(this)
        val url = "https://meme-api.com/gimme"

// Request a string response from the provided URL.
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url,null,
            { response ->
                currentImageUrl =  response.getString("url")
                val myImageView = findViewById<ImageView>(R.id.imageView)
                Glide.with(this).load(currentImageUrl).listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressB.visibility = View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressB.visibility = View.GONE
                        return false
                    }
                }).into(myImageView)
            },
            {

            })

// Add the request to the RequestQueue.
        queue.add(jsonObjectRequest)
    }
    fun nextfun(view: View) {
        load()
    }

    fun sharefun(view: View) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT,"Hey, Checkout this cool meme I got from reddit $currentImageUrl")
        val chooser = Intent.createChooser(intent,"Share this meme using ...")
        startActivity(chooser)
    }
}