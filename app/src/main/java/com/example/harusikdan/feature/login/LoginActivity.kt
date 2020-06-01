package com.example.harusikdan.feature.login

import android.content.Intent
import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.harusikdan.R
import com.example.harusikdan.databinding.ActivityLoginBinding
import com.example.harusikdan.feature.onboarding.onboardingframe.OnboardingActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var loginBinding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpDataBinding()
        initView()
    }

    private fun setUpDataBinding() {
        loginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        loginBinding.activity = this
    }

    private fun initView() {
        loginBinding.goToApply
    }

    fun loginButtonClicked() {
        val intent = Intent(this@LoginActivity, OnboardingActivity::class.java)
        startActivity(intent)
        finish()
    }
}