package com.example.harusikdan.feature.tab

import com.example.harusikdan.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_tab.view.*

class TabPresenter(override val view: TabContract.View): TabContract.Presenter {

    private val compositeDisposable = CompositeDisposable()

    override val listener = BottomNavigationView.OnNavigationItemSelectedListener {item ->
        when(item.itemId) {
            R.id.bottom_tab_main -> {
                view.loadFragment(0)
                return@OnNavigationItemSelectedListener true
            }
            R.id.bottom_tab_capture -> {
                view.loadFragment(1)
                return@OnNavigationItemSelectedListener true
            }
            R.id.bottom_tab_my_page -> {
                view.loadFragment(2)
                return@OnNavigationItemSelectedListener true
            }
        }
        return@OnNavigationItemSelectedListener false
    }

    override fun onCleared() {
        compositeDisposable.clear()
    }

}