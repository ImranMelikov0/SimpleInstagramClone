package com.imranmelikov.simpleinstagramclone.Activities

import android.os.Bundle
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.imranmelikov.simpleinstagramclone.R
import com.imranmelikov.simpleinstagramclone.databinding.ActivityMainBinding
import com.imranmelikov.simpleinstagramclone.ui.ProfilFragment
import com.imranmelikov.simpleinstagramclone.ui.SearchFragment
import com.imranmelikov.simpleinstagramclone.ui.HomeFragment
import com.imranmelikov.simpleinstagramclone.ui.NotificationsFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

//        val navController = findNavController(R.id.nav_host_fragment_activity_main)
//
//        val appBarConfiguration = AppBarConfiguration(
//            setOf(
//                R.id.navigation_home, R.id.navigation_notifications
//            )
//        )
//        setupActionBarWithNavController(navController, appBarConfiguration)
//        navView.setupWithNavController(navController)

        moveToFragment(HomeFragment())
        navView.setOnItemSelectedListener{
            when(it.itemId){
                R.id.nav_home ->{
                    moveToFragment(HomeFragment())
                  return@setOnItemSelectedListener true
                }
                R.id.nav_search ->{
                    moveToFragment(SearchFragment())
                    return@setOnItemSelectedListener true
                }
                R.id.nav_add ->{
                    return@setOnItemSelectedListener true
                }
                R.id.nav_notifications ->{
                    moveToFragment(NotificationsFragment())
                    return@setOnItemSelectedListener true
                }
                R.id.nav_profile ->{
                    moveToFragment(ProfilFragment())
                    return@setOnItemSelectedListener true
                }

            }

            false
        }
    }


    private fun moveToFragment(fragment: Fragment){
        val fragmentTrans=supportFragmentManager.beginTransaction()
        fragmentTrans.replace(R.id.container,fragment)
        fragmentTrans.commit()
    }
}