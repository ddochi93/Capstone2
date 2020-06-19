package com.example.harusikdan.feature.tab

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.eroom.domain.utils.toastShort
import com.example.harusikdan.R
import com.example.harusikdan.databinding.ActivityTabBinding
import com.example.harusikdan.feature.main.MainFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class TabActivity : AppCompatActivity(), TabContract.View {
    private lateinit var mainBinding: ActivityTabBinding
    private lateinit var presenter: TabPresenter

    private val FINISH_INTERVAL_TIME: Long = 2000
    private var backPressedTime: Long = 0

    lateinit var listener: BottomNavigationView.OnNavigationItemSelectedListener
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initPresenter()
        setUpDataBinding()
        initFragment()
    }

    private fun initPresenter() {
        presenter = TabPresenter(this)
        listener = presenter.listener
    }

    private fun setUpDataBinding() {
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_tab)
        mainBinding.activity = this
    }

    private fun initFragment() = supportFragmentManager.beginTransaction().replace(R.id.main_container, MainFragment.newInstance()).commit()

    override fun loadFragment(index: Int) =
        when (index) {
            0 -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.main_container, MainFragment.newInstance()).commit()
            }
            1 -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.main_container, MainFragment.newInstance()).commit()
            }
            2 -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.main_container, MainFragment.newInstance()).commit()
            }
            3 -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.main_container, MainFragment.newInstance()).commit()
            }
            else -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.main_container, MainFragment.newInstance()).commit()
            }
        }

    override fun onBackPressed() {
        val tempTime = System.currentTimeMillis()
        val intervalTime = tempTime - backPressedTime

        if(intervalTime in 0..FINISH_INTERVAL_TIME) {
            super.onBackPressed()
        } else {
            backPressedTime = tempTime
            this.toastShort("한번 더 뒤로가기를 누르시면 종료됩니다")
        }
    }
}