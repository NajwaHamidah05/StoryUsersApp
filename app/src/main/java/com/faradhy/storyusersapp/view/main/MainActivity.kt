package com.faradhy.storyusersapp.view.main

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.faradhy.storyusersapp.R
import com.faradhy.storyusersapp.adapter.LoadingStateAdapter
import com.faradhy.storyusersapp.adapter.MainAdapter
import com.faradhy.storyusersapp.databinding.ActivityMainBinding
import com.faradhy.storyusersapp.view.ViewModelFactory
import com.faradhy.storyusersapp.view.detail.DetailActivity
import com.faradhy.storyusersapp.view.maps.MapsActivity
import com.faradhy.storyusersapp.view.onboarding.OnboardingActivity
import com.faradhy.storyusersapp.view.story.StoryActivity

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var mainBinding: ActivityMainBinding
    private lateinit var mainAdapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        val layoutManager = LinearLayoutManager(this)
        mainBinding.rvStories.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        mainBinding.rvStories.addItemDecoration(itemDecoration)

        mainAdapter = MainAdapter()
        viewModel.getSession().observe(this) { user ->
            if (!user.isLogin) {
                startActivity(Intent(this, OnboardingActivity::class.java))
                finish()
            }else{
                getData()

                mainAdapter.setOnItemClickCallback { story ->
                    val intent = Intent(this@MainActivity, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.EXTRA_TOKEN,user.token)
                    intent.putExtra(DetailActivity.EXTRA_ID,story.id)
                    startActivity(intent)
                }

            }
        }

        setupView()


    }

    private fun navigateToPost() {
        val intent = Intent(this, StoryActivity::class.java)
        startActivity(intent)
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
        supportActionBar?.title = "Story"
        mainBinding.newStory.setOnClickListener { navigateToPost() }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.log_out -> {
                viewModel.logout()
                startActivity(Intent(this, OnboardingActivity::class.java))
                finish()
            }
            R.id.maps_button->{
                startActivity(Intent(this, MapsActivity::class.java))
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun getData() {
        mainBinding.rvStories.adapter = mainAdapter.withLoadStateFooter(
            footer = LoadingStateAdapter {
                mainAdapter.retry()
            }
        )
        viewModel.story.observe(this) {
            mainAdapter.submitData(lifecycle, it)
        }
    }

}