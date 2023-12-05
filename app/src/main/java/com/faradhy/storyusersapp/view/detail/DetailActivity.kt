package com.faradhy.storyusersapp.view.detail

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.faradhy.storyusersapp.data.ResultState
import com.faradhy.storyusersapp.databinding.ActivityDetailBinding
import com.faradhy.storyusersapp.view.ViewModelFactory

class DetailActivity : AppCompatActivity() {

    companion object{
        const val EXTRA_ID = "extra_id_story"
        const val EXTRA_TOKEN = "extra_token"
    }

    private val viewModel by viewModels<DetailViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var detailBinding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailBinding= ActivityDetailBinding.inflate(layoutInflater)
        setContentView(detailBinding.root)

        setupView()
        detail()
    }

    private fun detail(){
        val token = intent.getStringExtra(EXTRA_TOKEN)
        val id = intent.getStringExtra(EXTRA_ID)

        if (token != null && id != null) {
            viewModel.getDetailStory(id).observe(this) { detail ->
                when (detail) {
                    is ResultState.Success -> {
                        detailBinding.apply {
                            descDetail.text = detail.data.story?.description.orEmpty()
                            tvItemName.text = detail.data.story?.name.orEmpty()
                            Glide.with(this@DetailActivity)
                                .load(detail.data.story?.photoUrl)
                                .transition(DrawableTransitionOptions.withCrossFade())
                                .centerCrop()
                                .into(photoDetail)
                        }
                    }
                    else -> {
                    }
                }
            }
        }
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.show()
        supportActionBar?.title = "New Story"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    @Suppress("DEPRECATION")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}