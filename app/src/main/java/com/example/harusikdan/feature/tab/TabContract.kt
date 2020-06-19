package com.example.harusikdan.feature.tab

import com.google.android.material.bottomnavigation.BottomNavigationView

interface TabContract {
    interface View {
        fun loadFragment(index: Int): Int
    }

    interface Presenter {
        val view: View
        val listener: BottomNavigationView.OnNavigationItemSelectedListener
        fun onCleared()
    }
}