package com.imranmelikov.simpleinstagramclone.Activities

import android.location.GnssAntennaInfo.Listener
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.imranmelikov.simpleinstagramclone.Adapters.AdapterRV_notification
import com.imranmelikov.simpleinstagramclone.Adapters.AdapterRV_search_activity
import com.imranmelikov.simpleinstagramclone.Models.Post
import com.imranmelikov.simpleinstagramclone.Models.User
import com.imranmelikov.simpleinstagramclone.R
import com.imranmelikov.simpleinstagramclone.databinding.ActivitySearchBinding
import com.imranmelikov.simpleinstagramclone.databinding.ActivitySigninBinding
import com.imranmelikov.simpleinstagramclone.databinding.FragmentNotificationsBinding

class SearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding
    private lateinit var mUser:ArrayList<User>
    private lateinit var searchadapter:AdapterRV_search_activity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivitySearchBinding.inflate(layoutInflater)
        val view=binding.root
        setContentView(view)

        mUser= ArrayList()
        searchadapter= AdapterRV_search_activity(mUser)
        binding.recyclerviewSearchActivity.layoutManager=LinearLayoutManager(this)
        binding.recyclerviewSearchActivity.adapter=searchadapter

    }
}