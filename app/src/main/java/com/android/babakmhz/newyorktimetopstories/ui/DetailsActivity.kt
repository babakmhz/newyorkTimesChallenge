package com.android.babakmhz.newyorktimetopstories.ui

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.android.babakmhz.newyorktimetopstories.R
import com.android.babakmhz.newyorktimetopstories.utils.*
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.item_stories.text_title

@AndroidEntryPoint
class DetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        setupViews()
    }

    private fun setupViews() {
        // fetching data
        text_title.text = intent.getStringExtra(INTENT_TITLE_KEY)
        text_date.text = intent.getStringExtra(INTENT_DATE_KEY)
        text_abstract.text = intent.getStringExtra(INTENT_ABSTRACT_KEY)
        text_url.text = intent.getStringExtra(INTENT_URL_KEY)
        val poster: ImageView = findViewById(R.id.poster)
        Glide.with(this).load(intent.getStringExtra(INTENT_IMAGE_KEY)).into(poster)
    }


}