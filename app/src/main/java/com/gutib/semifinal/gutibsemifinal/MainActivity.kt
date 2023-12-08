package com.gutib.semifinal.gutibsemifinal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.gutib.semifinal.gutibsemifinal.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var tweetApiService: TweetApiServices

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Initialize binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://eldroid.online/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        tweetApiService = retrofit.create(TweetApiServices::class.java)

       
        binding.buttonCreate.setOnClickListener {
            val lastName = binding.lastNameInput.text.toString()
            val tweetId = binding.tweetIdInput.text.toString()
            val name = binding.nameInput.text.toString()
            val description = binding.descriptionInput.text.toString()

            val tweet = Tweet(lastName, tweetId, name, description)
            createTweet(tweet, binding.textViewResult)
        }

        binding.buttonUpdate.setOnClickListener {
            val lastName = binding.lastNameInput.text.toString()
            val tweetId = binding.tweetIdInput.text.toString()
            val name = binding.nameInput.text.toString()
            val description = binding.descriptionInput.text.toString()

            val updatedTweet = Tweet(lastName, tweetId, name, description)
            updateTweet(tweetId, updatedTweet, binding.textViewResult)
        }

        binding.buttonDelete.setOnClickListener {
            // Logic to delete a tweet
            binding.textViewResult.text = "Tweet deleted"
        }
    }

    private fun createTweet(tweet: Tweet, textViewResult: TextView) {
        val call = tweetApiService.createTweet(tweet)

        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    textViewResult.text = "New tweet created"
                } else {
                    textViewResult.text = "Failed to create tweet: ${response.code()} - ${response.message()}"
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                textViewResult.text = "Error: ${t.message}"
            }
        })
    }

    private fun updateTweet(tweetId: String, tweet: Tweet, textViewResult: TextView) {
        val call = tweetApiService.updateTweet(tweetId, tweet)

        call.enqueue(object : Callback<Tweet> {
            override fun onResponse(call: Call<Tweet>, response: Response<Tweet>) {
                if (response.isSuccessful) {
                    textViewResult.text = "Tweet updated successfully"
                } else {
                    textViewResult.text = "Failed to update tweet: ${response.code()} - ${response.message()}"
                }
            }

            override fun onFailure(call: Call<Tweet>, t: Throwable) {
                textViewResult.text = "Error: ${t.message}"
            }
        })
    }
}