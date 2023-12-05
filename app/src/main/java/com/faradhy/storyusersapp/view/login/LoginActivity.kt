package com.faradhy.storyusersapp.view.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.faradhy.storyusersapp.view.main.MainActivity
import com.faradhy.storyusersapp.data.ResultState
import com.faradhy.storyusersapp.data.preferences.UserModel
import com.faradhy.storyusersapp.databinding.ActivityLoginBinding
import com.faradhy.storyusersapp.view.ViewModelFactory

class LoginActivity : AppCompatActivity() {
    private val viewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var bindingLogin: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingLogin = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(bindingLogin.root)

        setupView()
        setupAction()
        playAnimation()
    }

    private fun toMain() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
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
        supportActionBar?.hide()
    }

    private fun setupAction() {
        bindingLogin.loginButton.setOnClickListener {
            val email = bindingLogin.emailEditTextLayout.text.toString()
            val password = bindingLogin.passwordEditTextLayout.text.toString()
            viewModel.login(email, password).observe(this){result ->
                if (result!= null) {
                    when (result) {
                        is ResultState.Loading -> {
                            loading(true)
                        }
                        is ResultState.Success -> {
                            viewModel.saveSession(UserModel(email, result.data.loginResult.token))
                            toast(result.data.message)
                            loading(false)
                            toMain()
                            onDestroy()
                        }
                        is ResultState.Error -> {
                            toast(result.error)
                            loading(false)
                        }

                        else -> {}
                    }
                }
            }
        }
    }


    private fun loading(isLoading: Boolean) {
        bindingLogin.loadingBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun toast(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(bindingLogin.imageView, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val title = ObjectAnimator.ofFloat(bindingLogin.titleTextView, View.ALPHA, 1f).setDuration(100)
        val message =
            ObjectAnimator.ofFloat(bindingLogin.messageTextView, View.ALPHA, 1f).setDuration(100)
        val emailTextView =
            ObjectAnimator.ofFloat(bindingLogin.emailTextView, View.ALPHA, 1f).setDuration(100)
        val emailEditTextLayout =
            ObjectAnimator.ofFloat(bindingLogin.emailEditTextLayout, View.ALPHA, 1f).setDuration(100)
        val passwordTextView =
            ObjectAnimator.ofFloat(bindingLogin.passwordTextView, View.ALPHA, 1f).setDuration(100)
        val passwordEditTextLayout =
            ObjectAnimator.ofFloat(bindingLogin.passwordEditTextLayout, View.ALPHA, 1f).setDuration(100)
        val login = ObjectAnimator.ofFloat(bindingLogin.loginButton, View.ALPHA, 1f).setDuration(100)

        AnimatorSet().apply {
            playSequentially(
                title,
                message,
                emailTextView,
                emailEditTextLayout,
                passwordTextView,
                passwordEditTextLayout,
                login
            )
            startDelay = 100
        }.start()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}