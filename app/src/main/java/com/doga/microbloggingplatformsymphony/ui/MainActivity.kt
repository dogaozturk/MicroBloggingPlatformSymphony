package com.doga.microbloggingplatformsymphony.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.doga.microbloggingplatformsymphony.R
import com.doga.microbloggingplatformsymphony.databinding.MainActivityBinding
import com.doga.microbloggingplatformsymphony.ui.author.AuthorListFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, AuthorListFragment.newInstance())
                    .commitNow()
        }
    }

    override fun onBackPressed() {
        checkForNavigation()
    }

    private fun checkForNavigation() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        } else if (supportFragmentManager.backStackEntryCount == 0) {
            finish()
        }
    }


}
